package br.com.juliocauan.familybudget.domain.application.model;

import java.util.Date;

import lombok.Data;

@Data
public class Expense {
    private Integer id;
    private String description;
    private Float value;
    private Date date;
}
