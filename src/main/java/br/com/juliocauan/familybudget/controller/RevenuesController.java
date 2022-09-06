package br.com.juliocauan.familybudget.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.juliocauan.familybudget.infrastructure.application.repository.DBCPDataSource;
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
        Connection connection = DBCPDataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute("SELECT * FROM revenues");
            ResultSet rst = statement.getResultSet();
            List<RevenueGET> response = new ArrayList<>();
            RevenueGET revenue;
            
            while(rst.next()){
                revenue = new RevenueGET();
                revenue
                    .description(rst.getString("DESCRIPTION"))
                    .value(rst.getBigDecimal("VALUE"))
                    .date(rst.getDate("DAY").toLocalDate());
                response.add(revenue);
            }
            connection.close();
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
    }
    
}
