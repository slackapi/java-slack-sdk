package com.github.seratch.jslack.api.methods.response.channels;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.Channel;
import lombok.Data;

@Data
public class ChannelsJoinResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private Channel channel;
}
