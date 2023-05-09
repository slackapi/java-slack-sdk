package com.slack.api.scim2.impl;

import com.slack.api.scim2.SCIM2ApiException;
import com.slack.api.scim2.SCIM2ApiResponse;

import java.io.IOException;

/**
 * A Supplier that holds an API Method execution.
 */
@FunctionalInterface
public interface AsyncExecutionSupplier<T extends SCIM2ApiResponse> {

    T execute() throws IOException, SCIM2ApiException;

}
