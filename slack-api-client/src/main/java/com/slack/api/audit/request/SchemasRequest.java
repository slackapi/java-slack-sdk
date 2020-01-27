package com.slack.api.audit.request;

import com.slack.api.audit.AuditApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchemasRequest implements AuditApiRequest {
    private String token;
}
