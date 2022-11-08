package br.com.juliocauan.familybudget.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.juliocauan.familybudget.domain.application.model.Revenue;
import br.com.juliocauan.familybudget.infrastructure.application.model.mapper.RevenueMapper;
import br.com.juliocauan.familybudget.infrastructure.application.service.RevenueService;
import br.com.juliocauan.openapi.api.RevenuesApi;
import br.com.juliocauan.openapi.model.RevenueDTO;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class RevenuesController implements RevenuesApi{

    private final RevenueService revenueService;

    @Override
    public ResponseEntity<Void> _postRevenue(@Valid RevenueDTO revenuePOST) {
        Revenue revenue = RevenueMapper.dtoToDomain(revenuePOST);
        revenueService.save(revenue);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<RevenueDTO>> _getAllRevenues(@Valid String description) {
        List<Revenue> revenues = revenueService.getAll(description);
        List<RevenueDTO> response = RevenueMapper.domainListToDtoList(revenues);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<List<RevenueDTO>> _getRevenuesByMonth(Integer year, Integer month) {
        List<Revenue> revenues = revenueService.getByMonthOfYear(year, month);
        List<RevenueDTO> response = RevenueMapper.domainListToDtoList(revenues);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<RevenueDTO> _getRevenue(Integer revenueId) {
        Revenue revenue = revenueService.findOne(revenueId);
        RevenueDTO response = RevenueMapper.domainToDto(revenue);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<Void> _updateRevenue(Integer revenueId, @Valid RevenueDTO revenuePUT) {
        Revenue revenue = RevenueMapper.dtoToDomain(revenuePUT);
        revenueService.update(revenueId, revenue);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> _deleteRevenue(Integer revenueId) {
        revenueService.delete(revenueId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
}
