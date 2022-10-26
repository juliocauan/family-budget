package br.com.juliocauan.familybudget.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import br.com.juliocauan.familybudget.config.TestContext;
import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;
import br.com.juliocauan.familybudget.infrastructure.application.repository.ExpenseRepository;
import br.com.juliocauan.openapi.model.ExpenseDTO;

public class ExpensesControllerTest extends TestContext{
    
    private final ExpenseRepository expenseRepository;
    private final String url = "/expenses";
    private final String urlId = "/expenses/{expenseId}";
    private final String urlInvalidId = "/expenses/0";
    private final String duplicateErrorMessage = "ExpenseEntity duplicate: Same description in the same month!";
    private final String notFoundErrorMessage = "Couldn't find ExpenseEntity with id 0!";
    
    private ExpenseDTO expenseDTO = new ExpenseDTO();

    @Autowired
    public ExpensesControllerTest(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @BeforeEach
    public void setup() {
        expenseDTO.date(LocalDate.now()).description("Test Description").quantity(new BigDecimal("12345.67"));
        expenseRepository.deleteAll();
    }

    private ExpenseEntity saveExpense(ExpenseDTO expenseDTO){
        ExpenseEntity entity = ExpenseEntity.builder()
            .description(expenseDTO.getDescription())
            .outcomeDate(expenseDTO.getDate())
            .quantity(expenseDTO.getQuantity())
            .build();
        return expenseRepository.save(entity);
    }

    @Test
    public void givenExpense_WhenPost_Then201() throws Exception {
        getMockMvc().perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(expenseDTO)))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    public void givenInvalidExpense_WhenPost_Then400() throws Exception {
        expenseDTO.date(null).description(null).quantity(null);
        getMockMvc().perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(expenseDTO)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("402"))
            .andExpect(jsonPath("$.fieldList", hasSize(3)));
    }

    @Test
    public void givenDuplicatedExpense_WhenPost_Then400() throws Exception {
        saveExpense(expenseDTO);
        getMockMvc().perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(expenseDTO)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("403"))
            .andExpect(jsonPath("$.message").value(duplicateErrorMessage))
            .andExpect(jsonPath("$.fieldList").doesNotExist());
    }

    @Test
    public void given_WhenGet_Then200() throws Exception {
        saveExpense(expenseDTO);
        getMockMvc().perform(
            get(url))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void givenExpenseId_WhenGetById_Then200() throws Exception {
        ExpenseEntity entity = saveExpense(expenseDTO);
        getMockMvc().perform(
            get(urlId, entity.getId()))
            .andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value(expenseDTO.getDescription()))
            .andExpect(jsonPath("$.quantity").value(expenseDTO.getQuantity()))
            .andExpect(jsonPath("$.date").value(expenseDTO.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE)));
    }

    @Test
    public void givenInvalidExpenseId_WhenGetById_Then404() throws Exception {
        getMockMvc().perform(
            get(urlInvalidId))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("301"))
            .andExpect(jsonPath("$.message").value(notFoundErrorMessage))
            .andExpect(jsonPath("$.fieldList").doesNotExist());
    }

    @Test
    public void givenExpense_WhenPut_Then204() throws Exception {
        ExpenseEntity entity = saveExpense(expenseDTO);
        expenseDTO.description("Put Test Description");
        getMockMvc().perform(
            put(urlId, entity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(expenseDTO)))
            .andDo(print())
            .andExpect(status().isNoContent());
    }

    @Test
    public void givenInvalidExpense_WhenPut_Then400() throws Exception {
        ExpenseEntity entity = saveExpense(expenseDTO);
        expenseDTO.date(null).description(null).quantity(null);
        getMockMvc().perform(
            put(urlId, entity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(expenseDTO)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("402"))
            .andExpect(jsonPath("$.fieldList", hasSize(3)));
    }

    @Test
    public void givenDuplicatedExpense_WhenPut_Then400() throws Exception {
        ExpenseEntity entity = saveExpense(expenseDTO);
        getMockMvc().perform(
            put(urlId, entity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(expenseDTO)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("403"))
            .andExpect(jsonPath("$.message").value(duplicateErrorMessage))
            .andExpect(jsonPath("$.fieldList").doesNotExist());
    }

    @Test
    public void givenInvalidExpenseId_WhenPut_Then404() throws Exception {
        getMockMvc().perform(
            put(urlInvalidId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(expenseDTO)))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("301"))
            .andExpect(jsonPath("$.message").value(notFoundErrorMessage))
            .andExpect(jsonPath("$.fieldList").doesNotExist());
    }

    @Test
    public void givenExpenseId_WhenDelete_Then200() throws Exception {
        ExpenseEntity entity = saveExpense(expenseDTO);
        getMockMvc().perform(
            delete(urlId, entity.getId()))
            .andDo(print())
            .andExpect(status().isOk());
    }
    
    @Test
    public void givenInvalidExpenseId_WhenDelete_Then404() throws Exception {
        getMockMvc().perform(
            delete(urlInvalidId))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("301"))
            .andExpect(jsonPath("$.message").value(notFoundErrorMessage))
            .andExpect(jsonPath("$.fieldList").doesNotExist());
    }

}
