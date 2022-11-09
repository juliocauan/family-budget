package br.com.juliocauan.familybudget.infrastructure.application.service;

import org.springframework.stereotype.Service;

import br.com.juliocauan.familybudget.domain.application.service.ExpenseServiceDomain;
import br.com.juliocauan.familybudget.domain.application.service.RevenueServiceDomain;
import br.com.juliocauan.familybudget.domain.application.service.SummaryServiceDomain;
import lombok.AllArgsConstructor;

//TODO review this Service
@Service
@AllArgsConstructor
public class SummaryService extends SummaryServiceDomain<Integer, Integer>{

    private final RevenueService revenueService;
    private final ExpenseService expenseService;
    
    @Override
    protected RevenueServiceDomain<Integer> getRevenueService() {
        return revenueService;
    }

    @Override
    protected ExpenseServiceDomain<Integer> getExpenseService() {
        return expenseService;
    }
    
}
