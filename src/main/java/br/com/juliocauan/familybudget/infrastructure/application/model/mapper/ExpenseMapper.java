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

    static Expense entityToDomain(ExpenseEntity entity){
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

    static ExpenseGetDTO domainToDto(Expense entity){
        return new ExpenseGetDTO()
            .description(entity.getDescription())
            .quantity(entity.getQuantity())
            .date(entity.getOutcomeDate())
            .category(entity.getCategory());
    }

    static ExpenseEntity dtoToEntity(ExpensePostDTO dto){
        return ExpenseEntity.builder()
            .description(dto.getDescription())
            .quantity(dto.getQuantity())
            .outcomeDate(dto.getDate())
            .category(dto.getCategory())
            .build();
    }

    static ExpenseEntity dtoToEntity(ExpensePutDTO dto){
        return ExpenseEntity.builder()
            .description(dto.getDescription())
            .quantity(dto.getQuantity())
            .outcomeDate(dto.getDate())
            .build();
    }

}
