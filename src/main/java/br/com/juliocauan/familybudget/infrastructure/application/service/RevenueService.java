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

//TODO review this Service
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
    public RevenueEntity save(Revenue entity) {
        if(hasDuplicate(entity)) throw new DuplicatedEntityException(getDuplicatedExceptionMessage());
        return revenueRepository.save(
            RevenueEntity.builder()
            .description(entity.getDescription())
            .incomeDate(entity.getIncomeDate())
            .quantity(entity.getQuantity())
            .build()
        );
    }

    @Override
    public RevenueEntity findOne(Integer id) {
        return revenueRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(getNotFoundExceptionMessage(id)));
    }

    @Override
    public RevenueEntity update(Integer oldEntityId, Revenue newEntity) {
        if(hasDuplicate(newEntity)) throw new DuplicatedEntityException(getDuplicatedExceptionMessage());
        RevenueEntity revenue = findOne(oldEntityId);
        revenue.setDescription(newEntity.getDescription());
        revenue.setIncomeDate(newEntity.getIncomeDate());
        revenue.setQuantity(newEntity.getQuantity());
        return revenueRepository.save(revenue);
    }

    @Override
    public void delete(Integer id) {
        revenueRepository.delete(findOne(id));
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
    
}
