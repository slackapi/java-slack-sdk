package com.github.seratch.jslack.api.methods.response.pins;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.MessageItem;
import lombok.Data;

import java.util.List;

@Data
public class PinsListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private List<MessageItem> items;
}