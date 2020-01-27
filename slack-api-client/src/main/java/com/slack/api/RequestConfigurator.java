package com.slack.api;

@FunctionalInterface
public interface RequestConfigurator<Builder> {

    Builder configure(Builder builder);

}
