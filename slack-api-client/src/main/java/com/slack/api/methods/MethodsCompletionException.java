package com.slack.api.methods;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Wrapped exception holding the cause exception occurred in AsyncMethodsClient
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class MethodsCompletionException extends RuntimeException {

    private final IOException ioException;
    private final SlackApiException slackApiException;
    private final Exception otherException;

    public MethodsCompletionException(IOException ioException, SlackApiException slackApiException, Exception otherException) {
        this.ioException = ioException;
        this.slackApiException = slackApiException;
        this.otherException = otherException;
    }

}
