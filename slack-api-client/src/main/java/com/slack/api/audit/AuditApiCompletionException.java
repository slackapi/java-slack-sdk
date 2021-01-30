package com.slack.api.audit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class AuditApiCompletionException extends RuntimeException {

    private final IOException ioException;
    private final AuditApiException auditApiException;
    private final Exception otherException;

    public AuditApiCompletionException(IOException ioException, AuditApiException auditApiException, Exception otherException) {
        this.ioException = ioException;
        this.auditApiException = auditApiException;
        this.otherException = otherException;
    }

}
