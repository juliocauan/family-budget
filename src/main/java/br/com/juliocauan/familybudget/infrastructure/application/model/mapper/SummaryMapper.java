package br.com.juliocauan.familybudget.infrastructure.application.model.mapper;

import java.util.List;

import br.com.juliocauan.familybudget.domain.application.model.Summary;
import br.com.juliocauan.openapi.model.CategoryExpenseDTO;
import br.com.juliocauan.openapi.model.SummaryDTO;

public interface SummaryMapper {

    static SummaryDTO domainToDto(Summary summary){
        List<CategoryExpenseDTO> categories = CategoryExpenseMapper.domainListToDtoList(summary.getCategoryExpenses());
        return new SummaryDTO()
            .balance(summary.getBalance())
            .categoryExpenses(categories)
            .expensesTotal(summary.getExpensesTotal())
            .revenuesTotal(summary.getRevenuesTotal());
    }

}
