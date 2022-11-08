package br.com.juliocauan.familybudget.infrastructure.application.model.mapper;

import java.util.List;

import br.com.juliocauan.familybudget.domain.application.model.CategoryExpense;
import br.com.juliocauan.familybudget.domain.application.model.Summary;
import br.com.juliocauan.openapi.model.CategoryExpenseDTO;
import br.com.juliocauan.openapi.model.SummaryDTO;

public interface SummaryMapper {

    static SummaryDTO domainToDto(Summary entity){
        List<CategoryExpenseDTO> categories = SummaryMapper.domainListToDtoList(entity.getCategoryExpenses());
        return new SummaryDTO()
            .balance(entity.getBalance())
            .categoryExpenses(categories)
            .expensesTotal(entity.getExpensesTotal())
            .revenuesTotal(entity.getRevenuesTotal());
    }

    private static List<CategoryExpenseDTO> domainListToDtoList(List<CategoryExpense> list) {
        return list.stream().map(SummaryMapper::domainToDto).toList();
    }

    private static CategoryExpenseDTO domainToDto(CategoryExpense categoryExpense) {
        return new CategoryExpenseDTO()
            .category(categoryExpense.getCategory())
            .quantity(categoryExpense.getQuantity());
    }

}
