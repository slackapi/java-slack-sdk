package com.slack.api.methods.response.pins;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

@Data
public class PinsAddResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private ErrorResponseMetadata responseMetadata;
}