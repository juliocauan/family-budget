package br.com.juliocauan.familybudget.infrastructure.application.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import br.com.juliocauan.familybudget.domain.application.model.Revenue;
import br.com.juliocauan.familybudget.domain.application.service.RevenueServiceDomain;
import br.com.juliocauan.familybudget.infrastructure.application.model.RevenueEntity;
import br.com.juliocauan.familybudget.infrastructure.application.model.mapper.RevenueMapper;
import br.com.juliocauan.familybudget.infrastructure.application.repository.RevenueRepository;
import br.com.juliocauan.familybudget.infrastructure.handler.exception.DuplicatedEntityException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RevenueService extends RevenueServiceDomain<Integer> {

    private final RevenueRepository revenueRepository;
    
    @Override
    protected List<Revenue> getAll() {
        return RevenueMapper.entityListToDomainList(revenueRepository.findAll());
    }

    @Override
    public List<Revenue> getAll(String description) {
        return description == null ? getAll() : getByDescription(description);
    }

    private List<Revenue> getByDescription(String description) {
        return RevenueMapper.entityListToDomainList(revenueRepository.findByDescriptionContaining(description));
    }

    @Override
    public List<Revenue> getByMonthOfYear(int year, int month) {
        return RevenueMapper.entityListToDomainList(revenueRepository.findByMonthOfYear(year, month));
    }

    @Override
    public Revenue save(Revenue revenue) {
        if(hasDuplicate(revenue)) throw new DuplicatedEntityException(getDuplicatedExceptionMessage());
        RevenueEntity entity = RevenueMapper.domainToEntity(revenue);
        return revenueRepository.save(entity);
    }

    @Override
    public Revenue findOne(Integer id) {
        return findEntity(id);
    }

    @Override
    public Revenue update(Integer oldEntityId, Revenue newRevenue) {
        if(hasDuplicate(newRevenue)) throw new DuplicatedEntityException(getDuplicatedExceptionMessage());
        RevenueEntity revenue = findEntity(oldEntityId);
        revenue.setDescription(newRevenue.getDescription());
        revenue.setIncomeDate(newRevenue.getIncomeDate());
        revenue.setQuantity(newRevenue.getQuantity());
        return revenueRepository.save(revenue);
    }

    @Override
    public void delete(Integer id) {
        revenueRepository.delete(findEntity(id));
    }

    @Override
    protected boolean hasDuplicate(Revenue entity) {
        List<RevenueEntity> list = revenueRepository.findDuplicate(
            entity.getDescription(),
            entity.getIncomeDate().getMonthValue(),
            entity.getIncomeDate().getYear()
        );
        return !list.isEmpty();
    }

    @Override
    protected String getClassName() {
        return RevenueEntity.class.getSimpleName();
    }

    private final RevenueEntity findEntity(Integer id) {
        return revenueRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(getNotFoundExceptionMessage(id)));
    }
    
}
