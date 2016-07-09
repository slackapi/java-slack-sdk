package com.github.seratch.jslack.api.methods.response.mpim;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.Group;
import lombok.Data;

@Data
public class MpimOpenResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private Group group;
}