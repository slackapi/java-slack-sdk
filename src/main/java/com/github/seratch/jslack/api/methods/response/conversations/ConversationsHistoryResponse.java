package com.github.seratch.jslack.api.methods.response.conversations;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.Message;
import com.github.seratch.jslack.api.model.ResponseMetadata;

import lombok.Data;

import java.util.List;

@Data
public class ConversationsHistoryResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private String latest;
    private List<Message> messages;
    private boolean hasMore;
    private ResponseMetadata responseMetadata;
}
