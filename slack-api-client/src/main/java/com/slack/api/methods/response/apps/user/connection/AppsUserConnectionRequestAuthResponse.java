package com.slack.api.methods.response.apps.user.connection;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * https://docs.slack.dev/reference/methods/apps.user.connection.requestAuth
 */
@Data
public class AppsUserConnectionRequestAuthResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    /**
     * `warnings` reports the outcome: `connection_status_out_of_date` when a modal opened by `trigger_id` shows a
     * different `is_connected` than Slack has recorded for the user; `auth_modal_not_found` plus one of
     * `connection_status_recorded` or `connection_status_unchanged` when a `user_id` call finds no open modal.
     */
    private ResponseMetadata responseMetadata;
}
