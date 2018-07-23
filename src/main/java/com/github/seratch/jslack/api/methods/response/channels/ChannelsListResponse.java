package com.github.seratch.jslack.api.methods.response.channels;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.Channel;
import com.github.seratch.jslack.api.model.ResponseMetadata;

import lombok.Data;

import java.util.List;

@Data
public class ChannelsListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private List<Channel> channels;
    private ResponseMetadata responseMetadata;
}
