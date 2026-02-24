package com.slack.api.model.annotation;

import java.util.Objects;

public class IsNotNullFieldPredicate implements FieldPredicate {
    @Override
    public boolean test(Object obj) {
        return !Objects.isNull(obj);
    }
}
