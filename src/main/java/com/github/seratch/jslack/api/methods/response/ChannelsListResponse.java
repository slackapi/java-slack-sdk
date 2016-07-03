package com.github.seratch.jslack.api.methods.response;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class ChannelsListResponse implements SlackApiResponse {

    private boolean ok;
    private String error;

    private List<Channel> channels;
}