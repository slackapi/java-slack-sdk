package com.slack.api.methods.impl;

import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.SlackApiResponse;

import java.io.IOException;

/**
 * A Supplier that holds an API Method execution.
 */
@FunctionalInterface
public interface AsyncExecutionSupplier<T extends SlackApiResponse> {

    T execute() throws IOException, SlackApiException;

}
