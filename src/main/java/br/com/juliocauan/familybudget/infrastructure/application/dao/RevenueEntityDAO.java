package br.com.juliocauan.familybudget.infrastructure.application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.juliocauan.familybudget.domain.application.dao.RevenueDAO;
import br.com.juliocauan.familybudget.domain.application.model.Revenue;
import br.com.juliocauan.familybudget.infrastructure.application.model.RevenueEntity;
import br.com.juliocauan.familybudget.infrastructure.datasource.DBCPDataSource;
import br.com.juliocauan.openapi.model.RevenueGET;

public class RevenueEntityDAO implements RevenueDAO{

    @Override
    public List<Revenue> getAll() {
        Connection connection = DBCPDataSource.getConnection();
        List<RevenueEntity> response = new ArrayList<>();
        String sql = "SELECT description, value, income_date FROM revenues";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
            ResultSet rst = statement.getResultSet();
            RevenueGET revenue;
            
            while(rst.next()){
                revenue = new RevenueGET();
                revenue
                    .description(rst.getString("DESCRIPTION"))
                    .value(rst.getBigDecimal("VALUE"))
                    .date(rst.getDate("DAY").toLocalDate());
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
