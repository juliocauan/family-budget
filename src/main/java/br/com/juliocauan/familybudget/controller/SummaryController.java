package br.com.juliocauan.familybudget.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.juliocauan.familybudget.domain.application.model.Summary;
import br.com.juliocauan.familybudget.infrastructure.application.model.mapper.SummaryMapper;
import br.com.juliocauan.familybudget.infrastructure.application.service.SummaryService;
import br.com.juliocauan.openapi.api.SummaryApi;
import br.com.juliocauan.openapi.model.SummaryDTO;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class SummaryController implements SummaryApi{

    private final SummaryService summaryService;
    
    @Override
    public ResponseEntity<SummaryDTO> _getSummaryByMonth(Integer year, Integer month) {
        Summary summary = summaryService.getMonthSummary(year, month);
        SummaryDTO response = SummaryMapper.domainToDto(summary);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
