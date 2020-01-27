package com.slack.api.methods;

import lombok.Data;

@Data
public class SlackApiErrorResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
}
