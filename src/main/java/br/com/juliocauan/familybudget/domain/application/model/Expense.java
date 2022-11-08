package br.com.juliocauan.familybudget.domain.application.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.juliocauan.openapi.model.CategoryEnum;

public interface Expense {
    String getDescription();
    BigDecimal getQuantity();
    LocalDate getOutcomeDate();
    CategoryEnum getCategory();
}
