package br.com.juliocauan.familybudget.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.juliocauan.familybudget.config.TestContext;
import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;
import br.com.juliocauan.familybudget.infrastructure.application.repository.ExpenseRepository;
import br.com.juliocauan.familybudget.infrastructure.application.service.ExpenseService;
import br.com.juliocauan.openapi.model.CategoryEnum;

public class ExpenseServiceTest extends TestContext {

    private final ExpenseService expenseService;
    private final ExpenseRepository expenseRepository;

    private ExpenseEntity entity = new ExpenseEntity();

    @Autowired
    public ExpenseServiceTest(ExpenseService expenseService, ExpenseRepository expenseRepository) {
        this.expenseService = expenseService;
        this.expenseRepository = expenseRepository;
    }

    @BeforeEach
    public void setup(){
        entity.setId(null);
        entity.setCategory(CategoryEnum.LEISURE);
        entity.setDescription("Test Description");
        entity.setOutcomeDate(LocalDate.now());
        entity.setQuantity(new BigDecimal("12345.67"));
        expenseRepository.deleteAll();
    }

    @Test
    public void givenCategory_WhenSave_ThenUseGivenCategory(){
        ExpenseEntity expense = expenseService.save(entity);
        Assertions.assertEquals(entity.getCategory(), expense.getCategory());
    }
    
    @Test
    public void givenNoCategory_WhenSave_ThenUseDefaultCategory(){
        entity.setCategory(null);
        ExpenseEntity expense = expenseService.save(entity);
        Assertions.assertEquals(CategoryEnum.OTHERS, expense.getCategory());
    }
    
    
}
