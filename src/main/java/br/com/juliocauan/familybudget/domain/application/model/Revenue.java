package br.com.juliocauan.familybudget.domain.application.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Revenue {
    public abstract String getDescription();
    public abstract BigDecimal getQuantity();
    public abstract LocalDate getIncomeDate();
}