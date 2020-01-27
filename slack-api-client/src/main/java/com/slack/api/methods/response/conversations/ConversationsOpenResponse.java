package com.slack.api.methods.response.conversations;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.Conversation;
import lombok.Data;

@Data
public class ConversationsOpenResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private boolean noOp;
    private boolean alreadyOpen;
    private Conversation channel;
}
