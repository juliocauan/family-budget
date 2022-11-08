package br.com.juliocauan.familybudget.infrastructure.application.model.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.juliocauan.familybudget.domain.application.model.Expense;
import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;
import br.com.juliocauan.openapi.model.CategoryEnum;
import br.com.juliocauan.openapi.model.ExpenseGetDTO;
import br.com.juliocauan.openapi.model.ExpensePostDTO;
import br.com.juliocauan.openapi.model.ExpensePutDTO;

public interface ExpenseMapper {

    private static Expense entityToDomain(ExpenseEntity entity){
        return new Expense() {
            @Override
            public String getDescription() {return entity.getDescription();}
            @Override
            public BigDecimal getQuantity() {return entity.getQuantity();}
            @Override
            public LocalDate getOutcomeDate() {return entity.getOutcomeDate();}
            @Override
            public CategoryEnum getCategory() {return entity.getCategory();}
        };
    }
    
    static List<Expense> entityListToDomainList(List<ExpenseEntity> entityList){
        return entityList.stream().map(ExpenseMapper::entityToDomain).toList();
    }
    
    static ExpenseGetDTO domainToGetDto(Expense entity){
        return new ExpenseGetDTO()
            .description(entity.getDescription())
            .quantity(entity.getQuantity())
            .date(entity.getOutcomeDate())
            .category(entity.getCategory());
    }

    static List<ExpenseGetDTO> domainListToGetDtoList(List<Expense> domainList) {
        return domainList.stream().map(ExpenseMapper::domainToGetDto).toList();
    }

    static ExpenseEntity domainToEntity(Expense expense){
        return ExpenseEntity
            .builder()
                .category(expense.getCategory())
                .description(expense.getDescription())
                .outcomeDate(expense.getOutcomeDate())
                .quantity(expense.getQuantity())
            .build();
    }

    static Expense postDtoToDomain(ExpensePostDTO dto){
        return new Expense() {
            @Override
            public String getDescription() {return dto.getDescription();}
            @Override
            public BigDecimal getQuantity() {return dto.getQuantity();}
            @Override
            public LocalDate getOutcomeDate() {return dto.getDate();}
            @Override
            public CategoryEnum getCategory() {return dto.getCategory();}
        };
    }

    static Expense putDtoToDomain(ExpensePutDTO dto){
        return new Expense() {
            @Override
            public String getDescription() {return dto.getDescription();}
            @Override
            public BigDecimal getQuantity() {return dto.getQuantity();}
            @Override
            public LocalDate getOutcomeDate() {return dto.getDate();}
            @Override
            public CategoryEnum getCategory() {return null;}
        };
    }

}
