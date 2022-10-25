package br.com.juliocauan.familybudget.domain.application.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Expense {
    public abstract String getDescription();
    public abstract BigDecimal getQuantity();
    public abstract LocalDate getOutcomeDate();
}
