package br.com.juliocauan.familybudget.infrastructure.application.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.juliocauan.familybudget.infrastructure.application.model.RevenueEntity;

public class RevenueSpecification {

    public static Specification<RevenueEntity> description(String description) {
        return (root, query, builder) -> builder.equal(root.get("description"), description);
    }

    public static Specification<RevenueEntity> month(int month) {
        return (root, query, builder) ->
            builder.equal(builder.function("MONTH", Integer.class, root.get("incomeDate")), month);
    }

    public static Specification<RevenueEntity> year(int year) {
        return (root, query, builder) ->
            builder.equal(builder.function("YEAR", Integer.class, root.get("incomeDate")), year);
    }

}
