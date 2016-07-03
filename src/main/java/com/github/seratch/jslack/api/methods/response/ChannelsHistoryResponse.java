package com.github.seratch.jslack.api.methods.response;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class ChannelsHistoryResponse implements SlackApiResponse {

    private boolean ok;
    private String error;

    private String latest;
    private List<Message> messages;
    private boolean hasMore;
}