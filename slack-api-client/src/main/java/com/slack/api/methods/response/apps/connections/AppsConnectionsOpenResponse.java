package com.slack.api.methods.response.apps.connections;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

/**
 * https://api.slack.com/methods/apps.connections.open
 */
@Data
public class AppsConnectionsOpenResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String url;
}