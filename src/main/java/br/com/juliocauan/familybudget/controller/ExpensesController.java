package br.com.juliocauan.familybudget.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.juliocauan.familybudget.infrastructure.application.dao.ExpenseEntityDAO;
import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;
import br.com.juliocauan.openapi.api.ExpensesApi;
import br.com.juliocauan.openapi.model.ExpenseDTO;

public class ExpensesController implements ExpensesApi{

    @Override
    public ResponseEntity<List<ExpenseDTO>> _getAllExpenses() {
        ExpenseEntityDAO expenseDAO = new ExpenseEntityDAO();
        List<ExpenseEntity> list = expenseDAO.getAll();
        List<ExpenseDTO> response = new ArrayList<>();
        list.forEach(expense -> response.add(entityToDto(expense)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<Void> _postExpense(@Valid ExpenseDTO expenseDTO) {
        ExpenseEntityDAO expenseDAO = new ExpenseEntityDAO();
        expenseDAO.save(dtoToEntity(expenseDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<ExpenseDTO> _getExpense(Integer expenseId) {
        ExpenseEntityDAO expenseDAO = new ExpenseEntityDAO();
        ExpenseEntity entity = expenseDAO.findOne(expenseId);
        ExpenseDTO response = entityToDto(entity);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<Void> _updateExpense(Integer expenseId, @Valid ExpenseDTO expenseDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> _deleteExpense(Integer expenseId) {
        // TODO Auto-generated method stub
        return null;
    }

    private ExpenseDTO entityToDto(ExpenseEntity entity){
        return new ExpenseDTO()
            .description(entity.getDescription())
            .value(entity.getValue())
            .date(entity.getOutcomeDate());
    }

    private ExpenseEntity dtoToEntity(ExpenseDTO dto){
        return ExpenseEntity.builder()
            .description(dto.getDescription())
            .value(dto.getValue())
            .outcomeDate(dto.getDate())
            .build();
    }
    
}
