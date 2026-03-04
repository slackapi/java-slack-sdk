package com.slack.api.methods.response.apps.user.connection;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * https://docs.slack.dev/reference/methods/apps.user.connection.update
 */
@Data
public class AppsUserConnectionUpdateResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;
}
