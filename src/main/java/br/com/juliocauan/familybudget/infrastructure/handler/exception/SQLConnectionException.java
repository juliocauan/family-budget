package br.com.juliocauan.familybudget.infrastructure.handler.exception;

import java.sql.SQLException;

public class SQLConnectionException extends RuntimeException{

    public SQLConnectionException(SQLException ex) {
        super(ex);
    }

}
