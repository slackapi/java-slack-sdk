package com.slack.api.util.predicate;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.google.common.base.Predicates.instanceOf;

public class IsValidAppIdPredicate implements FieldPredicate, Predicate<String> {
    public static final Pattern APP_ID_REGEX = Pattern.compile("^A[A-Z0-9]+$");

    @Override
    public boolean test(String t) {
        return APP_ID_REGEX.matcher(t).matches();
    }

    @Override
    public boolean validate(Object obj) {
        return instanceOf(String.class).and(o -> test((String) o)).test(obj);
    }
}
