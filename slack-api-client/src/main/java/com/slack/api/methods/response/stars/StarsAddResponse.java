package com.slack.api.methods.response.stars;

import com.slack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class StarsAddResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
}