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
    public Expense save(Expense expense) {
        if(hasDuplicate(expense)) throw new DuplicatedEntityException(getDuplicatedExceptionMessage());
        ExpenseEntity entity = ExpenseMapper.domainToEntity(expense);
        entity.setCategory(checkCategory(expense.getCategory()));
        return expenseRepository.save(entity);
    }

    @Override
    public Expense findOne(Integer id) {
        return findEntity(id);
    }

    @Override
    public Expense update(Integer oldEntityId, Expense newExpense) {
        if(hasDuplicate(newExpense)) throw new DuplicatedEntityException(getDuplicatedExceptionMessage());
        ExpenseEntity expense = findEntity(oldEntityId);
        expense.setDescription(newExpense.getDescription());
        expense.setOutcomeDate(newExpense.getOutcomeDate());
        expense.setQuantity(newExpense.getQuantity());
        return expenseRepository.save(expense);
    }

    @Override
    public void delete(Integer id) {
        expenseRepository.delete(findEntity(id));
    }

    @Override
    protected boolean hasDuplicate(Expense expense) {
        List<ExpenseEntity> list = expenseRepository.findDuplicate(
            expense.getDescription(),
            expense.getOutcomeDate().getMonthValue(),
            expense.getOutcomeDate().getYear()
        );
        return !list.isEmpty();
    }

    @Override
    protected String getClassName() {
        return ExpenseEntity.class.getSimpleName();
    }

    private final List<Expense> getByDescription(String description) {
        return ExpenseMapper.entityListToDomainList(expenseRepository.findByDescriptionContaining(description));
    }

    private final ExpenseEntity findEntity(Integer id){
        return expenseRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(getNotFoundExceptionMessage(id)));
    }
    
}
