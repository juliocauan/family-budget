package br.com.juliocauan.familybudget.domain.application.model;

import java.math.BigDecimal;
import java.util.Date;

public abstract class Revenue {
    public abstract String getDescription();
    public abstract BigDecimal getValue();
    public abstract Date getIncomeDate();
}