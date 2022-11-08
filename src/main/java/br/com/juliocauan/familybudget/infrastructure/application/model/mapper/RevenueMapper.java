package br.com.juliocauan.familybudget.infrastructure.application.model.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.juliocauan.familybudget.domain.application.model.Revenue;
import br.com.juliocauan.familybudget.infrastructure.application.model.RevenueEntity;
import br.com.juliocauan.openapi.model.RevenueDTO;

public interface RevenueMapper {

    private static Revenue entityToDomain(RevenueEntity entity){
        return new Revenue() {
            @Override
            public String getDescription() {return entity.getDescription();}
            @Override
            public BigDecimal getQuantity() {return entity.getQuantity();}
            @Override
            public LocalDate getIncomeDate() {return entity.getIncomeDate();}
        };
    }
    
    static List<Revenue> entityListToDomainList(List<RevenueEntity> entityList){
        return entityList.stream().map(RevenueMapper::entityToDomain).toList();
    }

    static RevenueDTO domainToDto(Revenue entity){
        return new RevenueDTO()
            .description(entity.getDescription())
            .quantity(entity.getQuantity())
            .date(entity.getIncomeDate());
    }

    static RevenueEntity dtoToEntity(RevenueDTO dto){
        return RevenueEntity.builder()
            .description(dto.getDescription())
            .quantity(dto.getQuantity())
            .incomeDate(dto.getDate())
            .build();
    }
    
}
