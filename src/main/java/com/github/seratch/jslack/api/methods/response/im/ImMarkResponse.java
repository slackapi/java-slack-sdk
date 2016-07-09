package com.github.seratch.jslack.api.methods.response.im;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class ImMarkResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
}