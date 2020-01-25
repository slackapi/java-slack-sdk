package com.github.seratch.jslack.lightning.util;

@FunctionalInterface
public interface BuilderConfigurator<Builder> {

    Builder configure(Builder builder);

}
