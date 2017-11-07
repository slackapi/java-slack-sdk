package com.github.seratch.jslack.api.methods.response.conversations;

import lombok.Data;

@Data
public class ConversationsLeaveResponse {
    
    private boolean ok;
    private String warning;
    private String error;

    private boolean notInChannel;
}
