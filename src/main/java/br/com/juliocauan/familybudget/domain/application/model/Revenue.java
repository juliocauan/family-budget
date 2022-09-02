package br.com.juliocauan.familybudget.domain.application.model;

import java.math.BigDecimal;
import java.util.Date;

public interface Revenue {
    String getDescription();
    BigDecimal getValue();
    Date getDate();
}