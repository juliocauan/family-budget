package br.com.juliocauan.familybudget.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.juliocauan.familybudget.config.TestContext;
import br.com.juliocauan.familybudget.domain.application.model.Expense;
import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;
import br.com.juliocauan.familybudget.infrastructure.application.repository.ExpenseRepository;
import br.com.juliocauan.familybudget.infrastructure.application.service.ExpenseService;
import br.com.juliocauan.familybudget.infrastructure.handler.exception.DuplicatedEntityException;
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
        entity = ExpenseEntity.builder()
            .id(null)
            .category(CategoryEnum.LEISURE)
            .description("Test Description 1")
            .outcomeDate(LocalDate.now())
            .quantity(new BigDecimal("12345.67"))
        .build();
        expenseRepository.deleteAll();
    }

    @Test
    public void givenDuplicateExpense_WhenSave_ThenDuplicateError(){
        expenseRepository.save(entity);
        Assertions.assertThrows(DuplicatedEntityException.class, () -> expenseService.save(entity));
    }

    @Test
    public void givenDuplicateExpense_WhenUpdate_ThenDuplicateError(){
        ExpenseEntity expense = expenseRepository.save(entity);
        Integer id = entity.getId();
        Assertions.assertThrows(DuplicatedEntityException.class, () -> expenseService.update(id, expense));
    }

    @Test
    public void givenCategory_WhenSave_ThenUseGivenCategory(){
        ExpenseEntity expense = expenseService.save(entity);
        Assertions.assertEquals(CategoryEnum.LEISURE, expense.getCategory());
    }
    
    @Test
    public void givenNoCategory_WhenSave_ThenUseDefaultCategory(){
        entity.setCategory(null);
        ExpenseEntity expense = expenseService.save(entity);
        Assertions.assertEquals(CategoryEnum.OTHERS, expense.getCategory());
    }

    @Test
    public void givenNoDescription_WhenGet_ThenGetAll(){
        expenseRepository.save(entity);
        entity.setId(null);
        entity.setDescription("Test Description 2");
        expenseRepository.save(entity);
        List<Expense> list = expenseService.getAll(null);
        Assertions.assertEquals(2, list.size());
    }

    @Test
    public void givenDescription_WhenGet_ThenGetContainsDescription(){
        expenseRepository.save(entity);
        entity.setId(null);
        entity.setDescription("Test Description 2");
        expenseRepository.save(entity);
        List<Expense> list = expenseService.getAll("1");
        Assertions.assertEquals(1, list.size());
    }
  
}
