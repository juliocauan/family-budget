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
import br.com.juliocauan.openapi.model.RevenueGET;
import br.com.juliocauan.openapi.model.RevenuePOST;

@RestController
public class RevenuesController implements RevenuesApi{

    @Override
    public ResponseEntity<RevenueGET> _postRevenue(@Valid RevenuePOST revenuePOST) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<List<RevenueGET>> _getAllRevenues() {
        RevenueEntityDAO revenueDAO = new RevenueEntityDAO();
        List<RevenueEntity> list = revenueDAO.getAll();
        List<RevenueGET> response = new ArrayList<>();
        list.forEach(revenue -> response.add(entityToDto(revenue)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private RevenueGET entityToDto(RevenueEntity entity){
        return new RevenueGET()
            .description(entity.getDescription())
            .value(entity.getValue())
            .date(entity.getIncomeDate());
    }
    
}
