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
import br.com.juliocauan.familybudget.infrastructure.application.model.RevenueEntity;
import br.com.juliocauan.familybudget.infrastructure.application.model.mapper.RevenueMapper;
import br.com.juliocauan.familybudget.infrastructure.application.repository.RevenueRepository;
import br.com.juliocauan.openapi.model.RevenueDTO;

public class RevenuesControllerTest extends TestContext{

    private final RevenueRepository revenueRepository;
    private final String url = "/revenues";
    private final String urlId = "/revenues/{revenueId}";
    private final String urlInvalidId = "/revenues/0";
    private final String urlByYearAndMonth = "/revenues/{year}/{month}";
    private final String duplicateErrorMessage = "RevenueEntity duplicate: Same description in the same month!";
    private final String notFoundErrorMessage = "Couldn't find RevenueEntity with id 0!";
    private final LocalDate date = LocalDate.now();
    
    private RevenueDTO revenueDTO = new RevenueDTO();

    @Autowired
    public RevenuesControllerTest(RevenueRepository revenueRepository) {
        this.revenueRepository = revenueRepository;
    }

    @BeforeEach
    public void setup() {
        revenueDTO.date(date).description("Test Description").quantity(new BigDecimal("12345.67"));
        revenueRepository.deleteAll();
    }

    private RevenueEntity saveRevenue(RevenueDTO revenueDTO){
        return revenueRepository.save(RevenueMapper.dtoToEntity(revenueDTO));
    }

    @Test
    public void givenRevenue_WhenPost_Then201() throws Exception {
        getMockMvc().perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(revenueDTO)))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    public void givenInvalidRevenue_WhenPost_Then400() throws Exception {
        revenueDTO.date(null).description(null).quantity(null);
        getMockMvc().perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(revenueDTO)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("402"))
            .andExpect(jsonPath("$.fieldList", hasSize(3)));
    }

    @Test
    public void givenDuplicatedRevenue_WhenPost_Then400() throws Exception {
        saveRevenue(revenueDTO);
        getMockMvc().perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(revenueDTO)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("403"))
            .andExpect(jsonPath("$.message").value(duplicateErrorMessage))
            .andExpect(jsonPath("$.fieldList").doesNotExist());
    }

    @Test
    public void givenNoDescription_WhenGet_Then200() throws Exception {
        saveRevenue(revenueDTO);
        getMockMvc().perform(
            get(url))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$.[0].description").value(revenueDTO.getDescription()))
            .andExpect(jsonPath("$.[0].quantity").value(revenueDTO.getQuantity()))
            .andExpect(jsonPath("$.[0].date").value(revenueDTO.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE)));
    }

    @Test
    public void givenDescription_WhenGet_Then200() throws Exception {
        saveRevenue(revenueDTO);
        getMockMvc().perform(
            get(url)
                .queryParam("description", revenueDTO.getDescription()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$.[0].description").value(revenueDTO.getDescription()))
            .andExpect(jsonPath("$.[0].quantity").value(revenueDTO.getQuantity()))
            .andExpect(jsonPath("$.[0].date").value(revenueDTO.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE)));
    }

    @Test
    public void givenNotPresentDescription_WhenGet_Then200() throws Exception {
        saveRevenue(revenueDTO);
        getMockMvc().perform(
            get(url)
                .queryParam("description", "not present"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void givenYearAndMonth_WhenGetByMonth_Then200() throws Exception {
        saveRevenue(revenueDTO);
        getMockMvc().perform(
            get(urlByYearAndMonth, date.getYear(), date.getMonthValue()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$.[0].description").value(revenueDTO.getDescription()))
            .andExpect(jsonPath("$.[0].quantity").value(revenueDTO.getQuantity()))
            .andExpect(jsonPath("$.[0].date").value(revenueDTO.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE)));
    }

    @Test
    public void givenYearAndNotPresentMonth_WhenGetByMonth_Then200() throws Exception {
        saveRevenue(revenueDTO);
        getMockMvc().perform(
            get(urlByYearAndMonth, date.getYear(), 0))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void givenMonthAndNotPresentYear_WhenGetByMonth_Then200() throws Exception {
        saveRevenue(revenueDTO);
        getMockMvc().perform(
            get(urlByYearAndMonth, 0, date.getMonthValue()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void givenRevenueId_WhenGetById_Then200() throws Exception {
        RevenueEntity entity = saveRevenue(revenueDTO);
        getMockMvc().perform(
            get(urlId, entity.getId()))
            .andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value(revenueDTO.getDescription()))
            .andExpect(jsonPath("$.quantity").value(revenueDTO.getQuantity()))
            .andExpect(jsonPath("$.date").value(revenueDTO.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE)));
    }

    @Test
    public void givenInvalidRevenueId_WhenGetById_Then404() throws Exception {
        getMockMvc().perform(
            get(urlInvalidId))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("301"))
            .andExpect(jsonPath("$.message").value(notFoundErrorMessage))
            .andExpect(jsonPath("$.fieldList").doesNotExist());
    }

    @Test
    public void givenRevenue_WhenPut_Then204() throws Exception {
        RevenueEntity entity = saveRevenue(revenueDTO);
        revenueDTO.description("Put Test Description");
        getMockMvc().perform(
            put(urlId, entity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(revenueDTO)))
            .andDo(print())
            .andExpect(status().isNoContent());
    }

    @Test
    public void givenInvalidRevenue_WhenPut_Then400() throws Exception {
        RevenueEntity entity = saveRevenue(revenueDTO);
        revenueDTO.date(null).description(null).quantity(null);
        getMockMvc().perform(
            put(urlId, entity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(revenueDTO)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("402"))
            .andExpect(jsonPath("$.fieldList", hasSize(3)));
    }

    @Test
    public void givenDuplicatedRevenue_WhenPut_Then400() throws Exception {
        RevenueEntity entity = saveRevenue(revenueDTO);
        getMockMvc().perform(
            put(urlId, entity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(revenueDTO)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("403"))
            .andExpect(jsonPath("$.message").value(duplicateErrorMessage))
            .andExpect(jsonPath("$.fieldList").doesNotExist());
    }

    @Test
    public void givenInvalidRevenueId_WhenPut_Then404() throws Exception {
        getMockMvc().perform(
            put(urlInvalidId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(revenueDTO)))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("301"))
            .andExpect(jsonPath("$.message").value(notFoundErrorMessage))
            .andExpect(jsonPath("$.fieldList").doesNotExist());
    }

    @Test
    public void givenRevenueId_WhenDelete_Then200() throws Exception {
        RevenueEntity entity = saveRevenue(revenueDTO);
        getMockMvc().perform(
            delete(urlId, entity.getId()))
            .andDo(print())
            .andExpect(status().isOk());
    }
    
    @Test
    public void givenInvalidRevenueId_WhenDelete_Then404() throws Exception {
        getMockMvc().perform(
            delete(urlInvalidId))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("301"))
            .andExpect(jsonPath("$.message").value(notFoundErrorMessage))
            .andExpect(jsonPath("$.fieldList").doesNotExist());
    }

}
