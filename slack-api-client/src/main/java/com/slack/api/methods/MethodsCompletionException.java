package com.slack.api.methods;

import lombok.EqualsAndHashCode;

/**
 * Wrapped exception holding the cause exception occurred in AsyncMethodsClient
 */
@EqualsAndHashCode(callSuper = true)
public class MethodsCompletionException extends RuntimeException {
    public MethodsCompletionException(Exception cause) {
        super(cause);
    }
}
