package br.com.juliocauan.familybudget.domain.application.service;

import java.util.List;

import br.com.juliocauan.familybudget.domain.application.model.Expense;
import br.com.juliocauan.familybudget.domain.application.service.base.BaseService;
import br.com.juliocauan.openapi.model.CategoryEnum;

public abstract class ExpenseServiceDomain<ID> extends BaseService<Expense, ID>{
    protected abstract Boolean hasDuplicate(Expense entity);
    protected abstract List<? extends Expense> getAll(String description);
    protected abstract List<? extends Expense> getByMonthOfYear(int year, int month);
    protected final CategoryEnum checkCategory(CategoryEnum category){
        return category == null ? CategoryEnum.OTHERS : category;
    }
}
