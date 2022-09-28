package br.com.juliocauan.familybudget.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.juliocauan.familybudget.infrastructure.application.dao.RevenueEntityDAO;
import br.com.juliocauan.familybudget.infrastructure.application.model.RevenueEntity;
import br.com.juliocauan.openapi.api.RevenuesApi;
import br.com.juliocauan.openapi.model.RevenueDTO;

@RestController
public class RevenuesController implements RevenuesApi{

    @Override
    public ResponseEntity<Void> _postRevenue(@Valid RevenueDTO revenuePOST) {
        RevenueEntityDAO revenueDao = new RevenueEntityDAO();
        revenueDao.save(dtoToEntity(revenuePOST));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<RevenueDTO>> _getAllRevenues() {
        RevenueEntityDAO revenueDAO = new RevenueEntityDAO();
        List<RevenueEntity> list = revenueDAO.getAll();
        List<RevenueDTO> response = new ArrayList<>();
        list.forEach(revenue -> response.add(entityToDto(revenue)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<RevenueDTO> _getRevenue(Integer revenueId) {
        RevenueEntityDAO revenueDAO = new RevenueEntityDAO();
        RevenueEntity entity = revenueDAO.findOne(revenueId);
        RevenueDTO response = entityToDto(entity);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<Void> _updateRevenue(Integer revenueId, @Valid RevenueDTO revenuePUT) {
        RevenueEntityDAO revenueDAO = new RevenueEntityDAO();
        RevenueEntity oldEntity = revenueDAO.findOne(revenueId);
        RevenueEntity newEntity = dtoToEntity(revenuePUT);
        revenueDAO.update(oldEntity.getId(), newEntity);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private RevenueDTO entityToDto(RevenueEntity entity){
        return new RevenueDTO()
            .description(entity.getDescription())
            .value(entity.getValue())
            .date(entity.getIncomeDate());
    }

    private RevenueEntity dtoToEntity(RevenueDTO dto){
        return RevenueEntity.builder()
            .description(dto.getDescription())
            .value(dto.getValue())
            .incomeDate(dto.getDate())
            .build();
    }
    
}
