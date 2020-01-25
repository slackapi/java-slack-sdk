package com.github.seratch.jslack.api;

@FunctionalInterface
public interface RequestConfigurator<Builder> {

    Builder configure(Builder builder);

}
