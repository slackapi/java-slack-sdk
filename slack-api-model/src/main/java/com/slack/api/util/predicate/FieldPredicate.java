package com.slack.api.util.predicate;

/**
 * A functional interface for defining validation predicates against {@link java.lang.reflect.Field}.  Used by
 * {@link com.slack.api.util.annotation.Required} during object serialization and deserialization to ensure the
 * field member is "valid" per the defined predicate.
 */
@FunctionalInterface
public interface FieldPredicate {
    boolean validate(Object obj);
}
