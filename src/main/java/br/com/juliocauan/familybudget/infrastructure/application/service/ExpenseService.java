package br.com.juliocauan.familybudget.infrastructure.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.juliocauan.familybudget.domain.application.model.Expense;
import br.com.juliocauan.familybudget.domain.application.service.ExpenseServiceDomain;
import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;
import br.com.juliocauan.familybudget.infrastructure.application.repository.ExpenseRepository;
import br.com.juliocauan.familybudget.infrastructure.application.repository.specification.ExpenseSpecification;
import br.com.juliocauan.familybudget.infrastructure.handler.exception.DuplicatedEntityException;
import br.com.juliocauan.familybudget.infrastructure.handler.exception.NotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExpenseService extends ExpenseServiceDomain<Integer>{

    private final ExpenseRepository expenseRepository;

    @Override
    public List<ExpenseEntity> getAll() {
        return expenseRepository.findAll();
    }

    @Override
    public void save(Expense entity) {
        if(hasDuplicate(entity)) throw new DuplicatedEntityException(getDuplicatedExceptionMessage());
        expenseRepository.save(
            ExpenseEntity.builder()
            .description(entity.getDescription())
            .outcomeDate(entity.getOutcomeDate())
            .value(entity.getValue())
            .build()
        );
    }

    @Override
    public ExpenseEntity findOne(Integer id) {
        return expenseRepository.findById(id).orElseThrow(() ->
            new NotFoundException(getNotFoundExceptionMessage(id)));
    }

    @Override
    public void update(Integer oldEntityId, Expense newEntity) {
        if(hasDuplicate(newEntity)) throw new DuplicatedEntityException(getDuplicatedExceptionMessage());
        ExpenseEntity expense = findOne(oldEntityId);
        expense.setDescription(newEntity.getDescription());
        expense.setOutcomeDate(newEntity.getOutcomeDate());
        expense.setValue(newEntity.getValue());
        expenseRepository.save(expense);
    }

    @Override
    public void delete(Integer id) {
        expenseRepository.delete(findOne(id));
    }

    @Override
    protected Boolean hasDuplicate(Expense entity) {
        List<ExpenseEntity> list = expenseRepository.findAll(
            ExpenseSpecification.description(entity.getDescription())
            .and(ExpenseSpecification.month(entity.getOutcomeDate().getMonthValue()))
            .and(ExpenseSpecification.year(entity.getOutcomeDate().getYear()))
        );
        return !list.isEmpty();
    }

    @Override
    protected String getClassName() {
        return ExpenseEntity.class.getName();
    }
    
}