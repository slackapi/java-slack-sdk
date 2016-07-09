package com.github.seratch.jslack.api.methods.response.pins;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class PinsRemoveResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
}