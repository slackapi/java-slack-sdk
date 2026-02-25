package com.slack.api.model.predicate;

import java.util.Objects;

public class IsNotNullFieldPredicate implements FieldPredicate {
    @Override
    public boolean validate(Object obj) {
        return Objects.nonNull(obj);
    }
}
