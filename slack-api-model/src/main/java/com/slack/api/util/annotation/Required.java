package com.slack.api.util.annotation;

import com.slack.api.util.predicate.FieldPredicate;
import com.slack.api.util.predicate.IsNotNullFieldPredicate;
import com.slack.api.util.json.RequiredPropertyDetectionAdapterFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Field-level annotation indicating whether the field is a "required" field or not on the model object.
 * <p>
 * The enforcement of the field's presence in instantiated instances of the model object is accomplished using the
 * {@link RequiredPropertyDetectionAdapterFactory} which ensures all fields marked with {@link Required} are
 * present during the object deserialization (or serialization) process.  Note that the enforcement of this annotation
 * is opt-in and defaults to "off".
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Required {
    /**
     * Optional predicate to evaluate against the field annotated with {@link Required}.  By default, all fields
     * marked with {@link Required} are checked for null.  Primitive field types are initialized by the JVM, and thus
     * are never null by default.
     */
    Class<? extends FieldPredicate> validator() default IsNotNullFieldPredicate.class;
}
