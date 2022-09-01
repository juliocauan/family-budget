package br.com.juliocauan.familybudget.domain.application.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import br.com.juliocauan.familybudget.domain.application.model.Revenue;

public class RevenueMapper implements RowMapper<Revenue> {

    @Override
    public Revenue map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Revenue(rs.getInt("id"), rs.getString("description"), rs.getFloat("value"), rs.getDate("date"));
    }
    
}
