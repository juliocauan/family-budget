package br.com.juliocauan.familybudget.infrastructure.application.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import br.com.juliocauan.familybudget.domain.application.model.Expense;
import br.com.juliocauan.familybudget.domain.application.service.ExpenseServiceDomain;
import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;
import br.com.juliocauan.familybudget.infrastructure.application.model.mapper.ExpenseMapper;
import br.com.juliocauan.familybudget.infrastructure.application.repository.ExpenseRepository;
import br.com.juliocauan.familybudget.infrastructure.handler.exception.DuplicatedEntityException;
import lombok.AllArgsConstructor;

//TODO review this Service
@Service
@AllArgsConstructor
public class ExpenseService extends ExpenseServiceDomain<Integer>{

    private final ExpenseRepository expenseRepository;

    @Override
    protected List<Expense> getAll() {
        return ExpenseMapper.entityListToDomainList(expenseRepository.findAll());
    }

    @Override
    public List<Expense> getAll(String description) {
        return description == null ? getAll() : getByDescription(description);
    }

    @Override
    public List<Expense> getByMonthOfYear(int year, int month) {
        return ExpenseMapper.entityListToDomainList(expenseRepository.findByMonthOfYear(year, month));
    }

    @Override
    public ExpenseEntity save(Expense entity) {
        if(hasDuplicate(entity)) throw new DuplicatedEntityException(getDuplicatedExceptionMessage());
        return expenseRepository.save(
            ExpenseEntity.builder()
            .description(entity.getDescription())
            .outcomeDate(entity.getOutcomeDate())
            .quantity(entity.getQuantity())
            .category(checkCategory(entity.getCategory()))
            .build()
        );
    }

    @Override
    public ExpenseEntity findOne(Integer id) {
        return expenseRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(getNotFoundExceptionMessage(id)));
    }

    @Override
    public ExpenseEntity update(Integer oldEntityId, Expense newEntity) {
        if(hasDuplicate(newEntity)) throw new DuplicatedEntityException(getDuplicatedExceptionMessage());
        ExpenseEntity expense = findOne(oldEntityId);
        expense.setDescription(newEntity.getDescription());
        expense.setOutcomeDate(newEntity.getOutcomeDate());
        expense.setQuantity(newEntity.getQuantity());
        return expenseRepository.save(expense);
    }

    @Override
    public void delete(Integer id) {
        expenseRepository.delete(findOne(id));
    }

    @Override
    protected boolean hasDuplicate(Expense entity) {
        List<ExpenseEntity> list = expenseRepository.findDuplicate(
            entity.getDescription(),
            entity.getOutcomeDate().getMonthValue(),
            entity.getOutcomeDate().getYear()
        );
        return !list.isEmpty();
    }

    @Override
    protected String getClassName() {
        return ExpenseEntity.class.getSimpleName();
    }

    private List<Expense> getByDescription(String description) {
        return ExpenseMapper.entityListToDomainList(expenseRepository.findByDescriptionContaining(description));
    }
    
}
