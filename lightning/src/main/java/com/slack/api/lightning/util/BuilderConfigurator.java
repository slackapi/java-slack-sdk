package com.slack.api.lightning.util;

/**
 * A functional interface that allows using a lambda syntax.
 * @param <Builder>
 */
@FunctionalInterface
public interface BuilderConfigurator<Builder> {

    Builder configure(Builder builder);

}
