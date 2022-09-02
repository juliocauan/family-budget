package br.com.juliocauan.familybudget.infrastructure.application.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;

import br.com.juliocauan.familybudget.domain.application.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ExpenseEntity implements Expense{

    @Id
    private Integer id;

    @NonNull
    private String description;

    @NonNull
    private BigDecimal value;

    @NonNull
    private Date date;
}
