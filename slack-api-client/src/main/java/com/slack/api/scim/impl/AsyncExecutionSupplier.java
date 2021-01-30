package com.slack.api.scim.impl;

import com.slack.api.scim.SCIMApiException;
import com.slack.api.scim.SCIMApiResponse;

import java.io.IOException;

/**
 * A Supplier that holds an API Method execution.
 */
@FunctionalInterface
public interface AsyncExecutionSupplier<T extends SCIMApiResponse> {

    T execute() throws IOException, SCIMApiException;

}
