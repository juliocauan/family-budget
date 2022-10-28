package br.com.juliocauan.familybudget.infrastructure.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.juliocauan.familybudget.infrastructure.application.model.RevenueEntity;

@Repository
public interface RevenueRepository extends JpaRepository<RevenueEntity, Integer>, JpaSpecificationExecutor<RevenueEntity>{

    @Query(value 
            = "SELECT * FROM revenues "
            + "WHERE description = :description "
            + "AND EXTRACT(MONTH FROM income_date) = :month "
            + "AND EXTRACT(YEAR FROM income_date) = :year",
        nativeQuery = true)
    List<RevenueEntity> findDuplicate(@Param("description") String description, @Param("month") int month, @Param("year") int year);

    List<RevenueEntity> findByDescriptionLike(String description);

}
