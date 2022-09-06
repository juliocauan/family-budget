package br.com.juliocauan.familybudget.domain.application.model;

import java.math.BigDecimal;
import java.util.Date;

public abstract class Expense {
    public abstract String getDescription();
    public abstract BigDecimal getValue();
    public abstract Date getOutcomeDate();
}
