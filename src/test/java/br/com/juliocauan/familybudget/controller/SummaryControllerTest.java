package br.com.juliocauan.familybudget.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.juliocauan.familybudget.config.TestContext;
import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;
import br.com.juliocauan.familybudget.infrastructure.application.model.RevenueEntity;
import br.com.juliocauan.familybudget.infrastructure.application.repository.ExpenseRepository;
import br.com.juliocauan.familybudget.infrastructure.application.repository.RevenueRepository;
import br.com.juliocauan.openapi.model.CategoryEnum;

public class SummaryControllerTest extends TestContext{

    private final RevenueRepository revenueRepository;
    private final ExpenseRepository expenseRepository;

    private final String urlByYearAndMonth = "/summary/{year}/{month}";
    private final LocalDate date = LocalDate.now();

    private RevenueEntity revenue = new RevenueEntity();
    private ExpenseEntity expense = new ExpenseEntity();

    @Autowired
    public SummaryControllerTest(RevenueRepository revenueRepository, ExpenseRepository expenseRepository) {
        this.revenueRepository = revenueRepository;
        this.expenseRepository = expenseRepository;
    }

    @BeforeEach
    public void setup(){
        revenueRepository.deleteAll();
        expenseRepository.deleteAll();
    }
    
    private void saveRevenue(String description, String quantity){
        revenue = RevenueEntity
        .builder()
            .id(null)
            .description(description)
            .incomeDate(date)
            .quantity(new BigDecimal(quantity))
        .build();
        revenueRepository.save(revenue);
    }

    private void saveExpense(String description, String quantity, CategoryEnum category){
        expense = ExpenseEntity
        .builder()
            .id(null)
            .description(description)
            .category(category)
            .outcomeDate(date)
            .quantity(new BigDecimal(quantity))
        .build();
        expenseRepository.save(expense);
    }

    @Test
    public void givenPresentYearAndMonth_WhenGetSummaryByMonth_Then200() throws Exception{
        saveRevenue("Revenue Description 1", "1000.72");
        saveRevenue("Revenue Description 2", "500.28");
        saveExpense("Expense Description 1", "200.13", CategoryEnum.HOME);
        saveExpense("Expense Description 2", "400.56", CategoryEnum.TRANSPORT);
        getMockMvc().perform(
            get(urlByYearAndMonth, date.getYear(), date.getMonthValue()))
            
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.revenuesTotal").value("1501.0"))
            .andExpect(jsonPath("$.expensesTotal").value("600.69"))
            .andExpect(jsonPath("$.balance").value("900.31"))
            .andExpect(jsonPath("$.categoryExpenses", hasSize(2)));
    }

    @Test
    public void givenPresentYearAndNotPresentMonth_WhenGetSummaryByMonth_Then200() throws Exception{
        getMockMvc().perform(
            get(urlByYearAndMonth, date.getYear(), 0))
            
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.revenuesTotal").value("0.0"))
            .andExpect(jsonPath("$.expensesTotal").value("0.0"))
            .andExpect(jsonPath("$.balance").value("0.0"))
            .andExpect(jsonPath("$.categoryExpenses", hasSize(0)));
    }

    @Test
    public void givenNotPresentYearAndPresentMonth_WhenGetSummaryByMonth_Then200() throws Exception{
        getMockMvc().perform(
            get(urlByYearAndMonth, 0, date.getMonthValue()))
            
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.revenuesTotal").value("0.0"))
            .andExpect(jsonPath("$.expensesTotal").value("0.0"))
            .andExpect(jsonPath("$.balance").value("0.0"))
            .andExpect(jsonPath("$.categoryExpenses", hasSize(0)));
    }

}
