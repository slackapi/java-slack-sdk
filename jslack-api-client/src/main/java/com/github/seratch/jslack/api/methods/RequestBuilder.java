package com.github.seratch.jslack.api.methods;

@FunctionalInterface
public interface RequestBuilder<R extends SlackApiRequest, Builder> {

    R build(Builder builder);

}
