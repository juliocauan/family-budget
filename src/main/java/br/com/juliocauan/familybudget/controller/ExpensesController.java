package br.com.juliocauan.familybudget.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.juliocauan.familybudget.domain.application.model.Expense;
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
        Expense expense = ExpenseMapper.postDtoToDomain(expenseDTO);
        expenseService.save(expense);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<ExpenseGetDTO>> _getAllExpenses(@Valid String description) {
        List<Expense> expenses = expenseService.getAll(description);
        List<ExpenseGetDTO> response = ExpenseMapper.domainListToGetDtoList(expenses);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<List<ExpenseGetDTO>> _getExpensesByMonth(Integer year, Integer month) {
        List<Expense> expenses = expenseService.getByMonthOfYear(year, month);
        List<ExpenseGetDTO> response = ExpenseMapper.domainListToGetDtoList(expenses);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ExpenseGetDTO> _getExpense(Integer expenseId) {
        Expense expense = expenseService.findOne(expenseId);
        ExpenseGetDTO response = ExpenseMapper.domainToGetDto(expense);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<Void> _updateExpense(Integer expenseId, @Valid ExpensePutDTO expenseDTO) {
        Expense expense = ExpenseMapper.putDtoToDomain(expenseDTO);
        expenseService.update(expenseId, expense);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> _deleteExpense(Integer expenseId) {
        expenseService.delete(expenseId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
}
