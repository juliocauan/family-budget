package br.com.juliocauan.familybudget.domain.application.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public final class Summary {
    BigDecimal revenuesTotal;
    BigDecimal expensesTotal;
    BigDecimal balance;
    List<CategoryExpense> categoryExpenses;

    public Summary(){
        this.revenuesTotal = new BigDecimal("0.0");
        this.expensesTotal = new BigDecimal("0.0");
        this.balance = new BigDecimal("0.0");
        this.categoryExpenses = new ArrayList<>();
    }

}
