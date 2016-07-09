package com.github.seratch.jslack.api.methods.response.mpim;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class MpimCloseResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
}