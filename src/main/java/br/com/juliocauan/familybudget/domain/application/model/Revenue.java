package br.com.juliocauan.familybudget.domain.application.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Revenue {
    private Integer id;
    private String description;
    private Float value;
    private Date date;
}
