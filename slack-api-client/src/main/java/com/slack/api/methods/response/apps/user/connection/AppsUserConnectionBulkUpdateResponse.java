package com.slack.api.methods.response.apps.user.connection;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * https://docs.slack.dev/reference/methods/apps.user.connection.bulkUpdate
 */
@Data
public class AppsUserConnectionBulkUpdateResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<String> changedUserIds;
    private List<Failure> failures;

    @Data
    public static class Failure {
        private String userId;
        private String error;
    }
}
