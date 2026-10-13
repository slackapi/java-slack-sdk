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

    /**
     * Users whose stored status changed. Users already at the requested status are omitted.
     */
    private List<String> changedUserIds;

    /**
     * Users whose update did not fully succeed. A user can also appear in changedUserIds
     * when their status was written but a later step was not.
     */
    private List<Failure> failures;

    @Data
    public static class Failure {
        private String userId;

        /**
         * Why this user's update did not fully succeed, e.g., user_not_found, duplicate_user_id, or clients_not_notified.
         * Treat unrecognized values as a generic failure.
         */
        private String error;
    }
}
