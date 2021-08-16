package com.slack.api.methods.response.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

@Data
public class ConversationsInviteSharedResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String url; // https://join.slack.com/share/...
    private String inviteId; // I12345
    private String confCode;
    private boolean isLegacySharedChannel;
}
