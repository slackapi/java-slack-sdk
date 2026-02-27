package com.slack.api.util.predicate;

import java.util.function.Predicate;
import java.util.regex.Pattern;
import static com.google.common.base.Predicates.instanceOf;

/**
 * Predicate for validating that a given ID conforms to Slack's user ID format.
 */
public final class IsValidUserIdPredicate implements FieldPredicate, Predicate<String> {
    public static final Pattern USER_ID_REGEX = Pattern.compile("^[WU][A-Z0-9]{8,}$");

    @Override
    public boolean test(String t) {
        return USER_ID_REGEX.matcher(t).matches();
    }

    @Override
    public boolean validate(Object obj) {
        return instanceOf(String.class).and(o -> test((String) o)).test(obj);
    }
}
