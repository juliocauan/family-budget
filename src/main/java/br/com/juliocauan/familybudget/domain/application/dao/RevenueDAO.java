package br.com.juliocauan.familybudget.domain.application.dao;

import br.com.juliocauan.familybudget.domain.application.dao.base.BaseDAO;
import br.com.juliocauan.familybudget.domain.application.model.Revenue;

public abstract class RevenueDAO<ID> implements BaseDAO<Revenue, ID>{
    protected abstract Boolean isDuplicated(Revenue entity);
}
