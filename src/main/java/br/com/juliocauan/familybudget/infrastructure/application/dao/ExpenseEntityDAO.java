package br.com.juliocauan.familybudget.infrastructure.application.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import br.com.juliocauan.familybudget.domain.application.dao.ExpenseDAO;
import br.com.juliocauan.familybudget.domain.application.model.Expense;
import br.com.juliocauan.familybudget.infrastructure.datasource.DBCPDataSource;
import br.com.juliocauan.familybudget.infrastructure.handler.exception.DuplicatedEntityException;
import br.com.juliocauan.familybudget.infrastructure.handler.exception.SQLConnectionException;

public final class ExpenseEntityDAO extends ExpenseDAO<Integer>{

    private Connection connection;

    private final String duplicateErrorMessage = "Expense duplicate: Same description in the same month!";
    private final String table = "expenses";
    private final String expense_id = "id";
    private final String description = "description";
    private final String value = "value";
    private final String date = "outcome_date";

    public ExpenseEntityDAO() {
        this.connection = DBCPDataSource.getConnection();
    }

    @Override
    public List<Expense> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(Expense entity) {
        if(isDuplicated(entity)) throw new DuplicatedEntityException(duplicateErrorMessage);
        String sql = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", table, description, value, date);
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getDescription());
            statement.setBigDecimal(2, entity.getValue());
            statement.setDate(3, Date.valueOf(entity.getOutcomeDate()));
            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new SQLConnectionException(ex);
        }
    }

    @Override
    public Expense findOne(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Integer oldEntityId, Expense newEntity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected Boolean isDuplicated(Expense entity) {
        // TODO Auto-generated method stub
        return null;
    }

}
