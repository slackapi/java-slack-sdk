package com.slack.api.model;

@FunctionalInterface
public interface ModelConfigurator<Builder> {

    Builder configure(Builder builder);

}
