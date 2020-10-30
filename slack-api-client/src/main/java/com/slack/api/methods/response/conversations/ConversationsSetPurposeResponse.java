package com.slack.api.methods.response.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Conversation;
import lombok.Data;

@Data
public class ConversationsSetPurposeResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private Conversation channel;
}
