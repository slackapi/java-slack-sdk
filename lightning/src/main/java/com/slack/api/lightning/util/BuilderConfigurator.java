package com.slack.api.lightning.util;

@FunctionalInterface
public interface BuilderConfigurator<Builder> {

    Builder configure(Builder builder);

}
