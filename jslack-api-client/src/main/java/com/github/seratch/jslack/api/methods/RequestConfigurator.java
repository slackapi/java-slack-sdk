package com.github.seratch.jslack.api.methods;

@FunctionalInterface
public interface RequestConfigurator<Builder> {

    Builder configure(Builder builder);

}
