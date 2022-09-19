package br.com.juliocauan.familybudget.infrastructure.handler.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message){
        super(message);
    }

}
