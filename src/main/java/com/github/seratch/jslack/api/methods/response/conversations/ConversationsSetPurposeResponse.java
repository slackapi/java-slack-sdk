package com.github.seratch.jslack.api.methods.response.conversations;

import com.github.seratch.jslack.api.model.Conversation;
import lombok.Data;

@Data
public class ConversationsSetPurposeResponse {

    private boolean ok;
    private String warning;
    private String error;

    private Conversation channel;
}
