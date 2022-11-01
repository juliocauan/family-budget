package br.com.juliocauan.familybudget.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.juliocauan.openapi.api.SummaryApi;
import br.com.juliocauan.openapi.model.SummaryDTO;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class SummaryController implements SummaryApi{
    
    @Override
    public ResponseEntity<SummaryDTO> _getSummaryByMonthOfYear(Integer year, Integer month) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
