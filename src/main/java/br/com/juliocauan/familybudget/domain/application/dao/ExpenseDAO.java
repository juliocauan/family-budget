package br.com.juliocauan.familybudget.domain.application.dao;

import br.com.juliocauan.familybudget.domain.application.dao.base.BaseDAO;
import br.com.juliocauan.familybudget.domain.application.model.Expense;

public abstract class ExpenseDAO<ID> implements BaseDAO<Expense, ID>{
    protected abstract Boolean isDuplicated(Expense entity);
}
