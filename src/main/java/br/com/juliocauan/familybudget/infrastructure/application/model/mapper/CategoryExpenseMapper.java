package br.com.juliocauan.familybudget.infrastructure.application.model.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.juliocauan.familybudget.domain.application.model.CategoryExpense;
import br.com.juliocauan.openapi.model.CategoryEnum;
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

    static List<CategoryExpense> mapToDomainList(Map<CategoryEnum, BigDecimal> map) {
        List<CategoryExpense> categoryExpenses = new ArrayList<>();
        Set<CategoryEnum> usedCategories = map.keySet();
        usedCategories.forEach(category -> categoryExpenses.add(new CategoryExpense(category, map.get(category))));
        return categoryExpenses;
    }
    
}
