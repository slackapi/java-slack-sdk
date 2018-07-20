package com.github.seratch.jslack.api.methods.response.conversations;

import com.github.seratch.jslack.api.methods.SlackApiResponse;

import lombok.Data;

@Data
public class ConversationsLeaveResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private boolean notInChannel;
}
