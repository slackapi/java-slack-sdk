package com.slack.api;

/**
 * A functional interface that provides a way to configure objects using lambda syntax in method args.
 *
 * @param <Builder>
 */
@FunctionalInterface
public interface RequestConfigurator<Builder> {

    Builder configure(Builder builder);

}
