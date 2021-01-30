package com.slack.api.audit.impl;

import com.slack.api.audit.AuditApiException;
import com.slack.api.audit.AuditApiResponse;

import java.io.IOException;

/**
 * A Supplier that holds an API Method execution.
 */
@FunctionalInterface
public interface AsyncExecutionSupplier<T extends AuditApiResponse> {

    T execute() throws IOException, AuditApiException;

}
