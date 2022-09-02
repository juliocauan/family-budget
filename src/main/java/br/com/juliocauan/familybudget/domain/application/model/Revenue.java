package br.com.juliocauan.familybudget.domain.application.model;

import java.util.Date;

public interface Revenue {
    String getDescription();
    Float getValue();
    Date getDate();
}