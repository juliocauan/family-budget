package br.com.juliocauan.familybudget.domain.application.model;

import java.util.Date;

public interface Expense {
    String getDescription();
    Float getValue();
    Date getDate();
}
