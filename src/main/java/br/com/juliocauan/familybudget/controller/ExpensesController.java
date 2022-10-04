package br.com.juliocauan.familybudget.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import br.com.juliocauan.openapi.api.ExpensesApi;
import br.com.juliocauan.openapi.model.ExpenseDTO;

public class ExpensesController implements ExpensesApi{

    @Override
    public ResponseEntity<Void> _postExpense(@Valid ExpenseDTO expenseDTO) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
