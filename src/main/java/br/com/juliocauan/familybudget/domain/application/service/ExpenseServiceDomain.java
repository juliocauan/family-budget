package br.com.juliocauan.familybudget.domain.application.service;

import java.util.List;

import br.com.juliocauan.familybudget.domain.application.model.Expense;
import br.com.juliocauan.familybudget.domain.application.service.base.BaseService;
import br.com.juliocauan.openapi.model.CategoryEnum;

public abstract class ExpenseServiceDomain<I> extends BaseService<Expense, I>{
    protected abstract boolean hasDuplicate(Expense entity);
    protected abstract List<Expense> getAll(String description);
    protected abstract List<Expense> getByMonthOfYear(int year, int month);
    protected final CategoryEnum checkCategory(CategoryEnum category){
        return category == null ? CategoryEnum.OTHERS : category;
    }
}
