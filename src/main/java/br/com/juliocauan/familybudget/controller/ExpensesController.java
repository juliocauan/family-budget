package br.com.juliocauan.familybudget.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;
import br.com.juliocauan.familybudget.infrastructure.application.service.ExpenseService;
import br.com.juliocauan.openapi.api.ExpensesApi;
import br.com.juliocauan.openapi.model.ExpenseDTO;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ExpensesController implements ExpensesApi{

    private final ExpenseService expenseService;

    @Override
    public ResponseEntity<Void> _postExpense(@Valid ExpenseDTO expenseDTO) {
        expenseService.save(dtoToEntity(expenseDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<ExpenseDTO>> _getAllExpenses() {
        List<ExpenseEntity> list = expenseService.getAll();
        List<ExpenseDTO> response = new ArrayList<>();
        list.forEach(expense -> response.add(entityToDto(expense)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ExpenseDTO> _getExpense(Integer expenseId) {
        ExpenseDTO response = entityToDto(expenseService.findOne(expenseId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<Void> _updateExpense(Integer expenseId, @Valid ExpenseDTO expenseDTO) {
        expenseService.update(expenseId, dtoToEntity(expenseDTO));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> _deleteExpense(Integer expenseId) {
        expenseService.delete(expenseId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private ExpenseDTO entityToDto(ExpenseEntity entity){
        return new ExpenseDTO()
            .description(entity.getDescription())
            .quantity(entity.getQuantity())
            .date(entity.getOutcomeDate());
    }

    private ExpenseEntity dtoToEntity(ExpenseDTO dto){
        return ExpenseEntity.builder()
            .description(dto.getDescription())
            .quantity(dto.getQuantity())
            .outcomeDate(dto.getDate())
            .build();
    }
    
}
