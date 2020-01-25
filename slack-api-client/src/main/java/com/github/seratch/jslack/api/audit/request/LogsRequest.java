package com.github.seratch.jslack.api.audit.request;

import com.github.seratch.jslack.api.audit.AuditApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogsRequest implements AuditApiRequest {
    private String token;

    /**
     * Unix timestamp of the most recent audit event to include (inclusive).
     */
    private Integer latest;

    /**
     * Unix timestamp of the least recent audit event to include (inclusive).
     * The earliest possible timestamp is when the Audit Logs feature was enabled for your Grid organization,
     * around mid-March 2018.
     */
    private Integer oldest;

    /**
     * Number of results to optimistically return, maximum 9999.
     */
    private Integer limit;

    /**
     * Name of the action.
     */
    private String action;

    /**
     * User ID who initiated the action.
     */
    private String actor;

    /**
     * ID of the target entity of the action (such as a channel, workspace, organization, file).
     */
    private String entity;

}
