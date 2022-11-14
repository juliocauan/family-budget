package br.com.juliocauan.familybudget.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import br.com.juliocauan.familybudget.domain.application.model.Expense;
import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;
import br.com.juliocauan.familybudget.infrastructure.application.model.mapper.ExpenseMapper;
import br.com.juliocauan.familybudget.infrastructure.application.repository.ExpenseRepository;
import br.com.juliocauan.openapi.model.CategoryEnum;
import br.com.juliocauan.openapi.model.ExpensePostDTO;
import br.com.juliocauan.openapi.model.ExpensePutDTO;

public class ExpensesControllerTest extends TestContext {

    private final ExpenseRepository expenseRepository;
    private final String url = "/expenses";
    private final String urlId = "/expenses/{expenseId}";
    private final String urlInvalidId = "/expenses/0";
    private final String urlByYearAndMonth = "/expenses/{year}/{month}";
    private final String duplicateErrorMessage = "ExpenseEntity duplicate: Same description in the same month!";
    private final String notFoundErrorMessage = "Couldn't find ExpenseEntity with id 0!";
    private final LocalDate date = LocalDate.now();

    private ExpensePostDTO postDTO = new ExpensePostDTO();
    private ExpensePutDTO putDTO = new ExpensePutDTO();

    @Autowired
    public ExpensesControllerTest(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @BeforeEach
    public void setup() {
        postDTO.date(date).description("Post Test Description").quantity(new BigDecimal("12345.67"))
                .category(CategoryEnum.EDUCATION);
        putDTO.date(date).description("Put Test Description").quantity(new BigDecimal("12345.67"));
        expenseRepository.deleteAll();
    }

    private ExpenseEntity saveExpense(ExpensePostDTO postDTO) {
        Expense expense = ExpenseMapper.postDtoToDomain(postDTO);
        ExpenseEntity entity = ExpenseMapper.domainToEntity(expense);
        return expenseRepository.save(entity);
    }

    @Test
    public void givenExpense_WhenPost_Then201() throws Exception {
        getMockMvc().perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(postDTO)))

                .andExpect(status().isCreated());
    }

    @Test
    public void givenInvalidExpense_WhenPost_Then400() throws Exception {
        postDTO.date(null).description(null).quantity(null).category(null);
        getMockMvc().perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(postDTO)))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("402"))
                .andExpect(jsonPath("$.fieldList", hasSize(3)));
    }

    @Test
    public void givenDuplicatedExpense_WhenPost_Then400() throws Exception {
        saveExpense(postDTO);
        getMockMvc().perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(postDTO)))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("403"))
                .andExpect(jsonPath("$.message").value(duplicateErrorMessage))
                .andExpect(jsonPath("$.fieldList").doesNotExist());
    }

    @Test
    public void givenNoDescription_WhenGet_Then200() throws Exception {
        saveExpense(postDTO);
        getMockMvc().perform(
                get(url))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].description").value(postDTO.getDescription()))
                .andExpect(jsonPath("$.[0].quantity").value(postDTO.getQuantity()))
                .andExpect(jsonPath("$.[0].date").value(postDTO.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .andExpect(jsonPath("$.[0].category").value(postDTO.getCategory().getValue()));
    }

    @Test
    public void givenDescription_WhenGet_Then200() throws Exception {
        saveExpense(postDTO);
        getMockMvc().perform(
                get(url)
                        .queryParam("description", postDTO.getDescription()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].description").value(postDTO.getDescription()))
                .andExpect(jsonPath("$.[0].quantity").value(postDTO.getQuantity()))
                .andExpect(jsonPath("$.[0].date").value(postDTO.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .andExpect(jsonPath("$.[0].category").value(postDTO.getCategory().getValue()));
    }

    @Test
    public void givenNotPresentDescription_WhenGet_Then200() throws Exception {
        saveExpense(postDTO);
        getMockMvc().perform(
                get(url)
                        .queryParam("description", "not presente"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void givenYearAndMonth_WhenGetByMonth_Then200() throws Exception {
        saveExpense(postDTO);
        getMockMvc().perform(
                get(urlByYearAndMonth, date.getYear(), date.getMonthValue()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].description").value(postDTO.getDescription()))
                .andExpect(jsonPath("$.[0].quantity").value(postDTO.getQuantity()))
                .andExpect(jsonPath("$.[0].date").value(postDTO.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .andExpect(jsonPath("$.[0].category").value(postDTO.getCategory().getValue()));
    }

    @Test
    public void givenYearAndNotPresentMonth_WhenGetByMonth_Then200() throws Exception {
        saveExpense(postDTO);
        getMockMvc().perform(
                get(urlByYearAndMonth, date.getYear(), 0))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void givenMonthAndNotPresentYear_WhenGetByMonth_Then200() throws Exception {
        saveExpense(postDTO);
        getMockMvc().perform(
                get(urlByYearAndMonth, 0, date.getMonthValue()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void givenExpenseId_WhenGetById_Then200() throws Exception {
        ExpenseEntity entity = saveExpense(postDTO);
        getMockMvc().perform(
                get(urlId, entity.getId()))

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(postDTO.getDescription()))
                .andExpect(jsonPath("$.quantity").value(postDTO.getQuantity()))
                .andExpect(jsonPath("$.date").value(postDTO.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .andExpect(jsonPath("$.category").value(postDTO.getCategory().getValue()));
    }

    @Test
    public void givenInvalidExpenseId_WhenGetById_Then404() throws Exception {
        getMockMvc().perform(
                get(urlInvalidId))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("301"))
                .andExpect(jsonPath("$.message").value(notFoundErrorMessage))
                .andExpect(jsonPath("$.fieldList").doesNotExist());
    }

    @Test
    public void givenExpense_WhenPut_Then204() throws Exception {
        ExpenseEntity entity = saveExpense(postDTO);
        getMockMvc().perform(
                put(urlId, entity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(putDTO)))

                .andExpect(status().isNoContent());
    }

    @Test
    public void givenInvalidExpense_WhenPut_Then400() throws Exception {
        ExpenseEntity entity = saveExpense(postDTO);
        putDTO.date(null).description(null).quantity(null);
        getMockMvc().perform(
                put(urlId, entity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(putDTO)))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("402"))
                .andExpect(jsonPath("$.fieldList", hasSize(3)));
    }

    @Test
    public void givenDuplicatedExpense_WhenPut_Then400() throws Exception {
        ExpenseEntity entity = saveExpense(postDTO);
        putDTO.description(postDTO.getDescription());
        getMockMvc().perform(
                put(urlId, entity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(putDTO)))

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
                        .content(getObjectMapper().writeValueAsString(putDTO)))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("301"))
                .andExpect(jsonPath("$.message").value(notFoundErrorMessage))
                .andExpect(jsonPath("$.fieldList").doesNotExist());
    }

    @Test
    public void givenExpenseId_WhenDelete_Then200() throws Exception {
        ExpenseEntity entity = saveExpense(postDTO);
        getMockMvc().perform(
                delete(urlId, entity.getId()))

                .andExpect(status().isOk());
    }

    @Test
    public void givenInvalidExpenseId_WhenDelete_Then404() throws Exception {
        getMockMvc().perform(
                delete(urlInvalidId))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("301"))
                .andExpect(jsonPath("$.message").value(notFoundErrorMessage))
                .andExpect(jsonPath("$.fieldList").doesNotExist());
    }

}
