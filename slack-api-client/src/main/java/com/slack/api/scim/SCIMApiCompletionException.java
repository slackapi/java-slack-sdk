package com.slack.api.scim;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class SCIMApiCompletionException extends RuntimeException {

    private final IOException ioException;
    private final SCIMApiException scimApiException;
    private final Exception otherException;

    public SCIMApiCompletionException(IOException ioException, SCIMApiException scimApiException, Exception otherException) {
        this.ioException = ioException;
        this.scimApiException = scimApiException;
        this.otherException = otherException;
    }

}
