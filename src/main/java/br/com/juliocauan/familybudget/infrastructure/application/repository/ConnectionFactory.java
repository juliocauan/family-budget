package br.com.juliocauan.familybudget.infrastructure.application.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionFactory {

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/family-budget-data",
                "root",
                "secret");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
