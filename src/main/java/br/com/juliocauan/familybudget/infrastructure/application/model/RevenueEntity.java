package br.com.juliocauan.familybudget.infrastructure.application.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;

import br.com.juliocauan.familybudget.domain.application.model.Revenue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data @EqualsAndHashCode(callSuper = false)
@AllArgsConstructor @NoArgsConstructor
@Builder
public class RevenueEntity extends Revenue{

    @Id
    private Integer id;

    @NonNull
    private String description;

    @NonNull
    private BigDecimal value;

    @NonNull
    private Date incomeDate;
    
}
