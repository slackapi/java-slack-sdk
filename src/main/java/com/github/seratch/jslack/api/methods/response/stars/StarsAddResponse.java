package com.github.seratch.jslack.api.methods.response.stars;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class StarsAddResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
}