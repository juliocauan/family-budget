package br.com.juliocauan.familybudget.infrastructure.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.juliocauan.familybudget.domain.application.model.Revenue;
import br.com.juliocauan.familybudget.domain.application.service.RevenueServiceDomain;
import br.com.juliocauan.familybudget.infrastructure.application.model.RevenueEntity;
import br.com.juliocauan.familybudget.infrastructure.application.repository.RevenueRepository;
import br.com.juliocauan.familybudget.infrastructure.handler.exception.DuplicatedEntityException;
import br.com.juliocauan.familybudget.infrastructure.handler.exception.NotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RevenueService extends RevenueServiceDomain<Integer> {

    private final RevenueRepository revenueRepository;
    
    @Override
    public List<RevenueEntity> getAll() {
        return revenueRepository.findAll();
    }

    @Override
    public void save(Revenue entity) {
        if(hasDuplicate(entity)) throw new DuplicatedEntityException(getDuplicatedExceptionMessage());
        revenueRepository.save(
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
            new NotFoundException(getNotFoundExceptionMessage(id)));
    }

    @Override
    public void update(Integer oldEntityId, Revenue newEntity) {
        if(hasDuplicate(newEntity)) throw new DuplicatedEntityException(getDuplicatedExceptionMessage());
        RevenueEntity revenue = findOne(oldEntityId);
        revenue.setDescription(newEntity.getDescription());
        revenue.setIncomeDate(newEntity.getIncomeDate());
        revenue.setQuantity(newEntity.getQuantity());
        revenueRepository.save(revenue);
    }

    @Override
    public void delete(Integer id) {
        revenueRepository.delete(findOne(id));
    }

    @Override
    protected Boolean hasDuplicate(Revenue entity) {
        List<RevenueEntity> list = revenueRepository.findDuplicate(
            entity.getDescription(),
            entity.getIncomeDate().getMonthValue(),
            entity.getIncomeDate().getYear()
        );
        return !list.isEmpty();
    }

    @Override
    protected String getClassName() {
        return RevenueEntity.class.getName();
    }
    
}
