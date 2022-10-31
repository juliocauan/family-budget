package br.com.juliocauan.familybudget.repository;

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

public class RevenueRepositoryTest extends TestContext{

    private final RevenueRepository revenueRepository;
    private final LocalDate date = LocalDate.now();

    private RevenueEntity entity = new RevenueEntity();

    @Autowired
    public RevenueRepositoryTest(RevenueRepository revenueRepository) {
        this.revenueRepository = revenueRepository;
    }

    @BeforeEach
    public void setup(){
        entity = RevenueEntity.builder()
            .id(null)
            .description("Test Description 1")
            .incomeDate(date)
            .quantity(new BigDecimal("12345.67"))
        .build();
        revenueRepository.deleteAll();
        revenueRepository.save(entity);
    }

    @Test
    public void givenDuplicatedDescriptionAndDate_WhenFindDuplicate_ThenNotEmptyList(){
        List<RevenueEntity> list = revenueRepository.findDuplicate(
            entity.getDescription(),
            entity.getIncomeDate().getMonthValue(),
            entity.getIncomeDate().getYear());
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void givenOnlyDuplicatedDate_WhenFindDuplicate_ThenEmptyList(){
        List<RevenueEntity> list = revenueRepository.findDuplicate(
            "Test Description 2",
            entity.getIncomeDate().getMonthValue(),
            entity.getIncomeDate().getYear());
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void givenOnlyDuplicatedDescriptionAndYear_WhenFindDuplicate_ThenEmptyList(){
        List<RevenueEntity> list = revenueRepository.findDuplicate(
            entity.getDescription(),
            (entity.getIncomeDate().getMonthValue() + 1) % 12,
            entity.getIncomeDate().getYear());
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void givenOnlyDuplicatedDescriptionAndMonth_WhenFindDuplicate_ThenEmptyList(){
        List<RevenueEntity> list = revenueRepository.findDuplicate(
            entity.getDescription(),
            entity.getIncomeDate().getMonthValue(),
            entity.getIncomeDate().getYear() + 1);
        Assertions.assertTrue(list.isEmpty());
    }
    
    @Test
    public void givenPresentDescription_WhenFindByDescriptionContaining_ThenNotEmptyList(){
        List<RevenueEntity> list = revenueRepository.findByDescriptionContaining("1");
        Assertions.assertFalse(list.isEmpty());
    }
        
    @Test
    public void givenNotPresentDescription_WhenFindByDescriptionContaining_ThenEmptyList(){
        List<RevenueEntity> list = revenueRepository.findByDescriptionContaining("2");
        Assertions.assertTrue(list.isEmpty());
    }

}