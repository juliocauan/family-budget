package br.com.juliocauan.familybudget.infrastructure.application.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.juliocauan.familybudget.infrastructure.application.model.ExpenseEntity;

public class ExpenseSpecification {

    public static Specification<ExpenseEntity> description(String description) {
        return (root, query, builder) -> builder.equal(root.get("description"), description);
    }

    public static Specification<ExpenseEntity> month(int month) {
        return (root, query, builder) ->
            builder.equal(builder.function("MONTH", Integer.class, root.get("outcomeDate")), month);
    }

    public static Specification<ExpenseEntity> year(int year) {
        return (root, query, builder) ->
            builder.equal(builder.function("YEAR", Integer.class, root.get("outcomeDate")), year);
    }

}
