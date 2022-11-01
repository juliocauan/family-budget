package br.com.juliocauan.familybudget.domain.application.service;

import java.util.List;

import br.com.juliocauan.familybudget.domain.application.model.Revenue;
import br.com.juliocauan.familybudget.domain.application.service.base.BaseService;

public abstract class RevenueServiceDomain<ID> extends BaseService<Revenue, ID> {
    protected abstract Boolean hasDuplicate(Revenue entity);
    protected abstract List<? extends Revenue> getAll(String description);
    protected abstract List<? extends Revenue> getByMonthOfYear(int year, int month);
}
