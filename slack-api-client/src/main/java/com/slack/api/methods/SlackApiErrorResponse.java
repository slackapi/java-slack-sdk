package com.slack.api.methods;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Represents the error response from Slack Web APIs. Developers can check the "error" code.
 * Refer to the API documents (https://api.slack.com/methods) to check the full list of each API's error codes.
 */
@Data
public class SlackApiErrorResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;
}
