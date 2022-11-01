package br.com.juliocauan.familybudget.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.juliocauan.familybudget.infrastructure.application.service.SummaryService;
import br.com.juliocauan.openapi.api.SummaryApi;
import br.com.juliocauan.openapi.model.Summary;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class SummaryController implements SummaryApi{

    private final SummaryService summaryService;
    
    @Override
    public ResponseEntity<Summary> _getSummaryByMonthOfYear(Integer year, Integer month) {
        Summary response = summaryService.getMonthSummary(year, month);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
