package com.github.seratch.jslack.api.methods.response.im;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.Channel;
import lombok.Data;

@Data
public class ImOpenResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private boolean noOp;
    private boolean alreadyOpen;

    private Channel channel;
}