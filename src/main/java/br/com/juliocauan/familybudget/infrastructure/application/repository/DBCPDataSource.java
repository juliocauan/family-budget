package br.com.juliocauan.familybudget.infrastructure.application.repository;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

import br.com.juliocauan.familybudget.infrastructure.application.handler.exception.SQLConnectionException;

public class DBCPDataSource {
    
    private static BasicDataSource ds = new BasicDataSource();
    
    //TODO review this
    static {
        ds.setUrl("jdbc:postgresql://localhost:5432/family-budget-data");
        ds.setUsername("root");
        ds.setPassword("secret");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }
    
    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new SQLConnectionException(e);
        }
    }
    
    private DBCPDataSource(){ }
}
