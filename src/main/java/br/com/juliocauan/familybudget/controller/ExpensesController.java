package br.com.juliocauan.familybudget.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.juliocauan.familybudget.infrastructure.application.dao.ExpenseEntityDAO;
import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;
import br.com.juliocauan.openapi.api.ExpensesApi;
import br.com.juliocauan.openapi.model.ExpenseDTO;

public class ExpensesController implements ExpensesApi{

    @Override
    public ResponseEntity<Void> _postExpense(@Valid ExpenseDTO expenseDTO) {
        ExpenseEntityDAO expenseDAO = new ExpenseEntityDAO();
        expenseDAO.save(dtoToEntity(expenseDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private ExpenseEntity dtoToEntity(ExpenseDTO dto){
        return ExpenseEntity.builder()
            .description(dto.getDescription())
            .value(dto.getValue())
            .outcomeDate(dto.getDate())
            .build();
    }
    
}
