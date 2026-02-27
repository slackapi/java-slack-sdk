package com.slack.api.util.predicate;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.google.common.base.Predicates.instanceOf;

/**
 * Predicate for validating that a given ID conforms to Slack's channel ID format.
 */
public class IsValidChannelIdPredicate implements FieldPredicate, Predicate<String> {
    public static final Pattern CHANNEL_ID_REGEX = Pattern.compile("^C[A-Z0-9]{2,}$");

    @Override
    public boolean test(String t) {
        return CHANNEL_ID_REGEX.matcher(t).matches();
    }

    @Override
    public boolean validate(Object obj) {
        return instanceOf(String.class).and(o -> test((String) o)).test(obj);
    }
}
