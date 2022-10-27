package br.com.juliocauan.familybudget.infrastructure.application.model.mapper;

import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;
import br.com.juliocauan.openapi.model.ExpenseGetDTO;
import br.com.juliocauan.openapi.model.ExpensePostDTO;
import br.com.juliocauan.openapi.model.ExpensePutDTO;

public abstract class ExpenseMapper {

    public static ExpenseGetDTO entityToDto(ExpenseEntity entity){
        return new ExpenseGetDTO()
            .description(entity.getDescription())
            .quantity(entity.getQuantity())
            .date(entity.getOutcomeDate())
            .category(entity.getCategory());
    }

    public static ExpenseEntity dtoToEntity(ExpensePostDTO dto){
        return ExpenseEntity.builder()
            .description(dto.getDescription())
            .quantity(dto.getQuantity())
            .outcomeDate(dto.getDate())
            .category(dto.getCategory())
            .build();
    }

    public static ExpenseEntity dtoToEntity(ExpensePutDTO dto){
        return ExpenseEntity.builder()
            .description(dto.getDescription())
            .quantity(dto.getQuantity())
            .outcomeDate(dto.getDate())
            .build();
    }

}
