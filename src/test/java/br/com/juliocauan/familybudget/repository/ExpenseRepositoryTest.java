package br.com.juliocauan.familybudget.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.juliocauan.familybudget.config.TestContext;
import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;
import br.com.juliocauan.familybudget.infrastructure.application.repository.ExpenseRepository;
import br.com.juliocauan.openapi.model.CategoryEnum;

public class ExpenseRepositoryTest extends TestContext {
    
    private final ExpenseRepository expenseRepository;
    private final LocalDate date = LocalDate.now();

    private ExpenseEntity entity = new ExpenseEntity();

    @Autowired
    public ExpenseRepositoryTest(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @BeforeEach
    public void setup(){
        entity = ExpenseEntity.builder()
            .id(null)
            .description("Test Description 1")
            .outcomeDate(date)
            .quantity(new BigDecimal("12345.67"))
            .category(CategoryEnum.LEISURE)
        .build();
        expenseRepository.deleteAll();
        expenseRepository.save(entity);
    }

    @Test
    public void givenDuplicatedDescriptionAndDate_WhenFindDuplicate_ThenNotEmptyList(){
        List<ExpenseEntity> list = expenseRepository.findDuplicate(
            entity.getDescription(),
            entity.getOutcomeDate().getMonthValue(),
            entity.getOutcomeDate().getYear());
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void givenOnlyDuplicatedDate_WhenFindDuplicate_ThenEmptyList(){
        List<ExpenseEntity> list = expenseRepository.findDuplicate(
            "Test Description 2",
            entity.getOutcomeDate().getMonthValue(),
            entity.getOutcomeDate().getYear());
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void givenOnlyDuplicatedDescriptionAndYear_WhenFindDuplicate_ThenEmptyList(){
        List<ExpenseEntity> list = expenseRepository.findDuplicate(
            entity.getDescription(),
            (entity.getOutcomeDate().getMonthValue() + 1) % 12,
            entity.getOutcomeDate().getYear());
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void givenOnlyDuplicatedDescriptionAndMonth_WhenFindDuplicate_ThenEmptyList(){
        List<ExpenseEntity> list = expenseRepository.findDuplicate(
            entity.getDescription(),
            entity.getOutcomeDate().getMonthValue(),
            entity.getOutcomeDate().getYear() + 1);
        Assertions.assertTrue(list.isEmpty());
    }

}
