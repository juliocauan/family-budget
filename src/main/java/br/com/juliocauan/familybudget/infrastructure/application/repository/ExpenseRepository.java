package br.com.juliocauan.familybudget.infrastructure.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Integer>, JpaSpecificationExecutor<ExpenseEntity> {

    @Query(value 
            = "SELECT * FROM expenses "
            + "WHERE description = :description "
            + "AND EXTRACT(MONTH FROM outcome_date) = :month "
            + "AND EXTRACT(YEAR FROM outcome_date) = :year",
        nativeQuery = true)
    List<ExpenseEntity> findDuplicate(@Param("description") String description, @Param("month") int month, @Param("year") int year);
}
