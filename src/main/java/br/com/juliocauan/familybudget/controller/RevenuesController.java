package br.com.juliocauan.familybudget.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
        // TODO Auto-generated method stub
        return null;
    }
    
}
