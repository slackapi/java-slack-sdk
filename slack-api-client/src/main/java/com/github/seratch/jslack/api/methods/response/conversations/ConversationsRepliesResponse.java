package com.github.seratch.jslack.api.methods.response.conversations;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.slack.api.model.Message;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class ConversationsRepliesResponse implements SlackApiResponse {
    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Message> messages;
    private boolean hasMore;
    private ResponseMetadata responseMetadata;
}
