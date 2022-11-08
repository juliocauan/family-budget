package br.com.juliocauan.familybudget.domain.application.service;

import java.util.List;

import br.com.juliocauan.familybudget.domain.application.model.Revenue;
import br.com.juliocauan.familybudget.domain.application.service.base.BaseService;

public abstract class RevenueServiceDomain<I> extends BaseService<Revenue, I> {
    protected abstract boolean hasDuplicate(Revenue entity);
    protected abstract List<Revenue> getAll(String description);
    protected abstract List<Revenue> getByMonthOfYear(int year, int month);
}
