package br.com.juliocauan.familybudget.infrastructure.application.model.mapper;

import br.com.juliocauan.familybudget.infrastructure.application.model.RevenueEntity;
import br.com.juliocauan.openapi.model.RevenueDTO;

public abstract class RevenueMapper {

    public static RevenueDTO entityToDto(RevenueEntity entity){
        return new RevenueDTO()
            .description(entity.getDescription())
            .quantity(entity.getQuantity())
            .date(entity.getIncomeDate());
    }

    public static RevenueEntity dtoToEntity(RevenueDTO dto){
        return RevenueEntity.builder()
            .description(dto.getDescription())
            .quantity(dto.getQuantity())
            .incomeDate(dto.getDate())
            .build();
    }
    
}
