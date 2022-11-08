package br.com.juliocauan.familybudget.domain.application.model;

import java.math.BigDecimal;

import br.com.juliocauan.openapi.model.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public final class CategoryExpense {
    CategoryEnum category;
    BigDecimal quantity;
}
