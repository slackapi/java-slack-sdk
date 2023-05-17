package com.slack.api.scim2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class SCIM2ApiCompletionException extends RuntimeException {

    private final IOException ioException;
    private final SCIM2ApiException scimApiException;
    private final Exception otherException;

    public SCIM2ApiCompletionException(IOException ioException, SCIM2ApiException scimApiException, Exception otherException) {
        this.ioException = ioException;
        this.scimApiException = scimApiException;
        this.otherException = otherException;
    }

}
