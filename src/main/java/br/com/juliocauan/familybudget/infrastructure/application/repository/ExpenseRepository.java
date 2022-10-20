package br.com.juliocauan.familybudget.infrastructure.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;

@Repository
public abstract class ExpenseRepository implements JpaRepository<ExpenseEntity, Integer>, JpaSpecificationExecutor<ExpenseEntity> {
}