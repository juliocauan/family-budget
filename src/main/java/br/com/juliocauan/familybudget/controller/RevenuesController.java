package br.com.juliocauan.familybudget.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.juliocauan.familybudget.infrastructure.application.model.RevenueEntity;
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
        revenueService.save(RevenueMapper.dtoToEntity(revenuePOST));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<RevenueDTO>> _getAllRevenues() {
        List<RevenueEntity> list = revenueService.getAll();
        List<RevenueDTO> response = new ArrayList<>();
        list.forEach(revenue -> response.add(RevenueMapper.entityToDto(revenue)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<RevenueDTO> _getRevenue(Integer revenueId) {
        RevenueDTO response = RevenueMapper.entityToDto(revenueService.findOne(revenueId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<Void> _updateRevenue(Integer revenueId, @Valid RevenueDTO revenuePUT) {
        revenueService.update(revenueId, RevenueMapper.dtoToEntity(revenuePUT));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> _deleteRevenue(Integer revenueId) {
        revenueService.delete(revenueId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
}
