package br.com.juliocauan.familybudget.infrastructure.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.juliocauan.familybudget.infrastructure.handler.exception.DuplicatedEntityException;
import br.com.juliocauan.familybudget.infrastructure.handler.exception.NotFoundException;
import br.com.juliocauan.familybudget.infrastructure.handler.exception.SQLConnectionException;
import br.com.juliocauan.openapi.model.Error;
import br.com.juliocauan.openapi.model.ErrorField;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Error responseError;

    private Error init(int code, Exception ex){
        Error error = new Error();
        error.setCode(code);
        error.setTrace(stackTraceString(ex.getStackTrace()));
        error.setMessage(ex.getMessage());
        return error;
    }

    private String stackTraceString(StackTraceElement[] elements){
        return org.apache.commons.lang3.StringUtils.join(elements, "\n");
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundError(NotFoundException ex){
        responseError = init(301, ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);
    }
    
    @ExceptionHandler(SQLConnectionException.class)
    public ResponseEntity<Object> sqlError(SQLConnectionException ex){
        responseError = init(401, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }
    
    //OPENAPI VALIDATION ERROR
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        responseError = init(402, ex);
        ex.getFieldErrors().forEach(error -> {
            ErrorField e = new ErrorField();
            e.setField(error.getObjectName() + "." + error.getField());
            e.setMessage(error.getDefaultMessage());
            e.setCode(error.getCode());
            responseError.addFieldListItem(e);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }
    
    @ExceptionHandler(DuplicatedEntityException.class)
    public ResponseEntity<Object> duplicateError(DuplicatedEntityException ex){
        responseError = init(403, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }


}
