package br.com.juliocauan.familybudget.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.juliocauan.familybudget.config.TestContext;
import br.com.juliocauan.familybudget.infrastructure.application.model.RevenueEntity;
import br.com.juliocauan.familybudget.infrastructure.application.repository.RevenueRepository;
import br.com.juliocauan.familybudget.infrastructure.application.service.RevenueService;
import br.com.juliocauan.familybudget.infrastructure.handler.exception.DuplicatedEntityException;

public class RevenueServiceTest extends TestContext{

    private final RevenueService revenueService;
    private final RevenueRepository revenueRepository;

    private RevenueEntity entity = new RevenueEntity();

    @Autowired
    public RevenueServiceTest(RevenueService revenueService, RevenueRepository revenueRepository) {
        this.revenueService = revenueService;
        this.revenueRepository = revenueRepository;
    }

    @BeforeEach
    public void setup(){
        entity = RevenueEntity.builder()
            .id(null)
            .description("Test Description 1")
            .incomeDate(LocalDate.now())
            .quantity(new BigDecimal("12345.67"))
        .build();
        revenueRepository.deleteAll();
    }

    @Test
    public void givenDuplicateRevenue_WhenSave_ThenDuplicateError(){
        revenueRepository.save(entity);
        Assertions.assertThrows(DuplicatedEntityException.class, () -> revenueService.save(entity));
    }

    @Test
    public void givenDuplicateRevenue_WhenUpdate_ThenDuplicateError(){
        RevenueEntity revenue = revenueRepository.save(entity);
        Assertions.assertThrows(DuplicatedEntityException.class, () -> revenueService.update(revenue.getId(), revenue));
    }

    @Test
    public void givenNoDescription_WhenGet_ThenGetAll(){
        revenueRepository.save(entity);
        entity.setId(null);
        entity.setDescription("Test Description 2");
        revenueRepository.save(entity);
        List<RevenueEntity> list = revenueService.getAll(null);
        Assertions.assertEquals(2, list.size());
    }

    @Test
    public void givenDescription_WhenGet_ThenGetLikeDescription(){
        revenueRepository.save(entity);
        entity.setId(null);
        entity.setDescription("Test Description 2");
        revenueRepository.save(entity);
        List<RevenueEntity> list = revenueService.getAll("1");
        Assertions.assertEquals(1, list.size());
    }

}
