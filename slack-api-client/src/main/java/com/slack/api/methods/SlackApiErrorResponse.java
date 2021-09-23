package com.slack.api.methods;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SlackApiErrorResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;
}
