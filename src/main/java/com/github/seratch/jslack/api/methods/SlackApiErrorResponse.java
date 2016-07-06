package com.github.seratch.jslack.api.methods;

import lombok.Data;

@Data
public class SlackApiErrorResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
}
