package br.com.juliocauan.familybudget.infrastructure.application.model.mapper;

import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;
import br.com.juliocauan.openapi.model.ExpenseDTO;

public abstract class ExpenseMapper {

    public static ExpenseDTO entityToDto(ExpenseEntity entity){
        return new ExpenseDTO()
            .description(entity.getDescription())
            .quantity(entity.getQuantity())
            .date(entity.getOutcomeDate());
    }

    public static ExpenseEntity dtoToEntity(ExpenseDTO dto){
        return ExpenseEntity.builder()
            .description(dto.getDescription())
            .quantity(dto.getQuantity())
            .outcomeDate(dto.getDate())
            .build();
    }

}
