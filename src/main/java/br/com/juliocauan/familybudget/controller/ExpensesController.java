package br.com.juliocauan.familybudget.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;
import br.com.juliocauan.familybudget.infrastructure.application.model.mapper.ExpenseMapper;
import br.com.juliocauan.familybudget.infrastructure.application.service.ExpenseService;
import br.com.juliocauan.openapi.api.ExpensesApi;
import br.com.juliocauan.openapi.model.ExpenseGetDTO;
import br.com.juliocauan.openapi.model.ExpensePostDTO;
import br.com.juliocauan.openapi.model.ExpensePutDTO;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ExpensesController implements ExpensesApi{

    private final ExpenseService expenseService;

    @Override
    public ResponseEntity<Void> _postExpense(@Valid ExpensePostDTO expenseDTO) {
        expenseService.save(ExpenseMapper.dtoToEntity(expenseDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<ExpenseGetDTO>> _getAllExpenses() {
        List<ExpenseEntity> list = expenseService.getAll();
        List<ExpenseGetDTO> response = new ArrayList<>();
        list.forEach(expense -> response.add(ExpenseMapper.entityToDto(expense)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ExpenseGetDTO> _getExpense(Integer expenseId) {
        ExpenseGetDTO response = ExpenseMapper.entityToDto(expenseService.findOne(expenseId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<Void> _updateExpense(Integer expenseId, @Valid ExpensePutDTO expenseDTO) {
        expenseService.update(expenseId, ExpenseMapper.dtoToEntity(expenseDTO));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> _deleteExpense(Integer expenseId) {
        expenseService.delete(expenseId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
}
