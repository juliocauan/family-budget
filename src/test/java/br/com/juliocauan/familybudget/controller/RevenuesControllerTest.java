package br.com.juliocauan.familybudget.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import br.com.juliocauan.familybudget.config.TestContext;
import br.com.juliocauan.openapi.model.RevenueDTO;

public class RevenuesControllerTest extends TestContext{

    private final String url = "/revenues";
    
    private RevenueDTO revenueDTO = new RevenueDTO();

    @BeforeEach
    public void setup() {
        revenueDTO.date(LocalDate.now()).description("Test Description").quantity(new BigDecimal("12345.67"));
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

}
