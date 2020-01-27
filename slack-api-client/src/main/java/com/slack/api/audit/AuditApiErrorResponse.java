package com.slack.api.audit;

import lombok.Data;

@Data
public class AuditApiErrorResponse implements AuditApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
}
