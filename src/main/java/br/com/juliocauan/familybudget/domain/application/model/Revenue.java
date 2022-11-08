package br.com.juliocauan.familybudget.domain.application.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Revenue {
    String getDescription();
    BigDecimal getQuantity();
    LocalDate getIncomeDate();
}