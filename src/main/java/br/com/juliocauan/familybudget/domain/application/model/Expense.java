package br.com.juliocauan.familybudget.domain.application.model;

import java.math.BigDecimal;
import java.util.Date;

public interface Expense {
    String getDescription();
    BigDecimal getValue();
    Date getOutcomeDate();
}
