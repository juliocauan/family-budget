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
    
    static List<Revenue> entityListToDomainList(List<RevenueEntity> list){
        return list.stream().map(RevenueMapper::entityToDomain).toList();
    }

    static List<RevenueDTO> domainListToDtoList(List<Revenue> list){
        return list.stream().map(RevenueMapper::domainToDto).toList();
    }

    static RevenueDTO domainToDto(Revenue revenue){
        return new RevenueDTO()
            .description(revenue.getDescription())
            .quantity(revenue.getQuantity())
            .date(revenue.getIncomeDate());
    }

    static Revenue dtoToDomain(RevenueDTO dto){
        return new Revenue() {
            @Override
            public String getDescription() {return dto.getDescription();}
            @Override
            public BigDecimal getQuantity() {return dto.getQuantity();}
            @Override
            public LocalDate getIncomeDate() {return dto.getDate();}
        };
    }

    static RevenueEntity domainToEntity(Revenue revenue) {
        return RevenueEntity
            .builder()
                .description(revenue.getDescription())
                .incomeDate(revenue.getIncomeDate())
                .quantity(revenue.getQuantity())
            .build();
    }
    
}
