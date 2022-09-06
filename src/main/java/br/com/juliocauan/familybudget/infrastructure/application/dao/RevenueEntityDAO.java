package br.com.juliocauan.familybudget.infrastructure.application.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.juliocauan.familybudget.domain.application.dao.RevenueDAO;
import br.com.juliocauan.familybudget.domain.application.model.Revenue;
import br.com.juliocauan.familybudget.infrastructure.application.model.RevenueEntity;
import br.com.juliocauan.familybudget.infrastructure.datasource.DBCPDataSource;
import br.com.juliocauan.familybudget.infrastructure.handler.exception.SQLConnectionException;

public class RevenueEntityDAO implements RevenueDAO{

    private Connection connection;

    public RevenueEntityDAO() {
        this.connection = DBCPDataSource.getConnection();
    }

    @Override
    public List<RevenueEntity> getAll() {
        String sql = "SELECT description, value, income_date FROM revenues";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            List<RevenueEntity> response = new ArrayList<>();
            statement.execute();
            ResultSet rst = statement.getResultSet();
            while(rst.next()){
                RevenueEntity revenue = RevenueEntity.builder()
                    .description(rst.getString(1))
                    .value(rst.getBigDecimal(2))
                    .incomeDate(rst.getDate(3).toLocalDate())
                    .build();
                response.add(revenue);
            }
            return response;
        } catch (SQLException ex) {
            throw new SQLConnectionException(ex);
        }
    }

    @Override
    public void save(Revenue entity) {
        String sql = "INSERT INTO revenues (description, value, income_date) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getDescription());
            statement.setBigDecimal(2, entity.getValue());
            statement.setDate(3, Date.valueOf(entity.getIncomeDate()));
            statement.execute();

        } catch (SQLException ex) {
            throw new SQLConnectionException(ex);
        }
    }

}
