package br.com.juliocauan.familybudget.infrastructure.handler.exception;

public class DuplicatedEntityException extends RuntimeException{

    public DuplicatedEntityException(String message){
        super(message);
    }

}
