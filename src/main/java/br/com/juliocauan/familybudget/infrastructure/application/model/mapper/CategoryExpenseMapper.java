package br.com.juliocauan.familybudget.infrastructure.application.model.mapper;

import java.util.List;

import br.com.juliocauan.familybudget.domain.application.model.CategoryExpense;
import br.com.juliocauan.openapi.model.CategoryExpenseDTO;

public interface CategoryExpenseMapper {

    static List<CategoryExpenseDTO> domainListToDtoList(List<CategoryExpense> list) {
        return list.stream().map(CategoryExpenseMapper::domainToDto).toList();
    }

    private static CategoryExpenseDTO domainToDto(CategoryExpense categoryExpense) {
        return new CategoryExpenseDTO()
            .category(categoryExpense.getCategory())
            .quantity(categoryExpense.getQuantity());
    }
    
}
