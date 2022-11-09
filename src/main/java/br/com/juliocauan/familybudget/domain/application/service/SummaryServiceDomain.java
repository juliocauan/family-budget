package br.com.juliocauan.familybudget.domain.application.service;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import br.com.juliocauan.familybudget.domain.application.model.Expense;
import br.com.juliocauan.familybudget.domain.application.model.Revenue;
import br.com.juliocauan.familybudget.domain.application.model.Summary;
import br.com.juliocauan.familybudget.infrastructure.application.model.mapper.CategoryExpenseMapper;
import br.com.juliocauan.openapi.model.CategoryEnum;

public abstract class SummaryServiceDomain<R, E> {
    
    protected abstract RevenueServiceDomain<R> getRevenueService();
    protected abstract ExpenseServiceDomain<E> getExpenseService();

    public final Summary getMonthSummary(int year, int month){
        Summary summary = new Summary();
        Map<CategoryEnum, BigDecimal> map = new EnumMap<>(CategoryEnum.class);
        List<Revenue> revenues = getRevenueService().getByMonthOfYear(year, month);
        List<Expense> expenses = getExpenseService().getByMonthOfYear(year, month);

        revenues.forEach(revenue -> summary.setRevenuesTotal( summary.getRevenuesTotal().add(revenue.getQuantity()) ));

        expenses.forEach(expense -> {
            summary.setExpensesTotal( summary.getExpensesTotal().add(expense.getQuantity()) );
            addExpenseInCategoryMap(map, expense.getCategory(), expense.getQuantity());
        });

        summary.setCategoryExpenses(CategoryExpenseMapper.mapToDomainList(map));
        summary.setBalance(summary.getRevenuesTotal().subtract(summary.getExpensesTotal()));
        return summary;
    }

    private final void addExpenseInCategoryMap(Map<CategoryEnum, BigDecimal> map, CategoryEnum category, BigDecimal quantity) {
        if(map.containsKey(category))
            map.put(category, map.get(category).add(quantity));
        else
            map.put(category, quantity);
    }

}
