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
import br.com.juliocauan.familybudget.infrastructure.handler.exception.DuplicatedEntityException;
import br.com.juliocauan.familybudget.infrastructure.handler.exception.SQLConnectionException;

public final class RevenueEntityDAO extends RevenueDAO{

    private Connection connection;
    private final String duplicateErrorMessage = "Revenue duplicate: Same description in the same month!";
    private final String table = "revenues";
    private final String description = "description";
    private final String value = "value";
    private final String date = "income_date";

    public RevenueEntityDAO() {
        this.connection = DBCPDataSource.getConnection();
    }

    @Override
    public List<RevenueEntity> getAll() {
        String sql = String.format("SELECT %s, %s, %s FROM %s", description, value, date, table);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            List<RevenueEntity> response = new ArrayList<>();
            statement.executeQuery();
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
        if(isDuplicated(entity)) throw new DuplicatedEntityException(duplicateErrorMessage);
        String sql = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", table, description, value, date);
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getDescription());
            statement.setBigDecimal(2, entity.getValue());
            statement.setDate(3, Date.valueOf(entity.getIncomeDate()));
            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new SQLConnectionException(ex);
        }
    }

    @Override
    protected Boolean isDuplicated(Revenue entity) {
        String sql = String.format("SELECT * FROM %s" + 
                                    " WHERE %s = ?" +
                                    " AND EXTRACT(MONTH FROM %s) = ?" +
                                    " AND EXTRACT(YEAR FROM %s) = ?", table, description, date, date);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getDescription());
            statement.setInt(2, entity.getIncomeDate().getMonthValue());
            statement.setInt(3, entity.getIncomeDate().getYear());
            return statement.executeQuery().next();

        } catch (SQLException ex) {
            throw new SQLConnectionException(ex);
        }
    }

}
