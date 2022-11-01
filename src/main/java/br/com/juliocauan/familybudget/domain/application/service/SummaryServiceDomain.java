package br.com.juliocauan.familybudget.domain.application.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.juliocauan.familybudget.domain.application.model.Expense;
import br.com.juliocauan.familybudget.domain.application.model.Revenue;
import br.com.juliocauan.openapi.model.CategoryEnum;
import br.com.juliocauan.openapi.model.CategoryExpense;
import br.com.juliocauan.openapi.model.Summary;

public abstract class SummaryServiceDomain<RID, EID> {
    
    protected abstract RevenueServiceDomain<RID> getRevenueService();
    protected abstract ExpenseServiceDomain<EID> getExpenseService();

    public final Summary getMonthSummary(int year, int month){
        Summary summary = new Summary().revenuesTotal(new BigDecimal("0.0")).expensesTotal(new BigDecimal("0.0"));
        Map<CategoryEnum, BigDecimal> map = new HashMap<>();
        List<? extends Revenue> revenues = getRevenueService().getByMonthOfYear(year, month);
        List<? extends Expense> expenses = getExpenseService().getByMonthOfYear(year, month);

        revenues.forEach(revenue -> {
            BigDecimal quantity = sum(summary.getRevenuesTotal(), revenue.getQuantity());
            summary.setRevenuesTotal(quantity);
        });

        expenses.forEach(expense -> {
            BigDecimal quantity = sum(summary.getExpensesTotal(), expense.getQuantity());
            summary.setExpensesTotal(quantity);
            if(map.containsKey(expense.getCategory())) {
                quantity = map.get(expense.getCategory());
                map.put(expense.getCategory(), quantity.add(expense.getQuantity()));
            } else {
                map.put(expense.getCategory(), expense.getQuantity());
            }
        });

        summary.setCategoryExpenses(mapToCategoryExpensesList(map));
        summary.balance(summary.getRevenuesTotal().subtract(summary.getExpensesTotal()));
        return summary;
    }

    private final List<CategoryExpense> mapToCategoryExpensesList(Map<CategoryEnum, BigDecimal> map) {
        List<CategoryExpense> categoryExpenses = new ArrayList<>();
        Set<CategoryEnum> usedCategories = map.keySet();
        usedCategories.forEach(category -> {
            BigDecimal quantity = map.get(category);
            CategoryExpense categoryExpense = new CategoryExpense().category(category).quantity(quantity);
            categoryExpenses.add(categoryExpense);
        });
        return categoryExpenses;
    }

    private final BigDecimal sum(BigDecimal a, BigDecimal b){
        return a.add(b);
    }

}
