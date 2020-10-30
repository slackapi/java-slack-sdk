package com.slack.api.methods.response.stars;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

@Data
public class StarsRemoveResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
}