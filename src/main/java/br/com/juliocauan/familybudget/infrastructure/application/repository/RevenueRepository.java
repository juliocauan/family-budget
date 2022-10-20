package br.com.juliocauan.familybudget.infrastructure.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.juliocauan.familybudget.infrastructure.application.model.RevenueEntity;

@Repository
public abstract class RevenueRepository implements JpaRepository<RevenueEntity, Integer>, JpaSpecificationExecutor<RevenueEntity>{
}