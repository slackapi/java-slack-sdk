package com.github.seratch.jslack.api.model;

@FunctionalInterface
public interface ModelConfigurator<Builder> {

    Builder configure(Builder builder);

}
