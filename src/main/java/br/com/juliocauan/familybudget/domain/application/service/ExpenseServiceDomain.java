package br.com.juliocauan.familybudget.domain.application.service;

import br.com.juliocauan.familybudget.domain.application.model.Expense;
import br.com.juliocauan.familybudget.domain.application.service.base.BaseService;
import br.com.juliocauan.openapi.model.CategoryEnum;

public abstract class ExpenseServiceDomain<ID> extends BaseService<Expense, ID>{
    protected abstract Boolean hasDuplicate(Expense entity);
    protected final CategoryEnum checkCategory(CategoryEnum category){
        return category == null ? CategoryEnum.OTHERS : category;
    }
}
