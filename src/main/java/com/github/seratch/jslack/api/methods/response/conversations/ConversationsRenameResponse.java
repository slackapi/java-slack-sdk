package com.github.seratch.jslack.api.methods.response.conversations;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.Conversation;
import lombok.Data;

@Data
public class ConversationsRenameResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private Conversation channel;

}
