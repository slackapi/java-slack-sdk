package com.github.seratch.jslack.api.methods.response.conversations;

import lombok.Data;

@Data
public class ConversationsKickResponse {

    private boolean ok;
    private String warning;
    private String error;
}
