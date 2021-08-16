package com.slack.api.methods.response.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

@Data
public class ConversationsAcceptSharedInviteResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private boolean implicitApproval;
    private String channelId;
    private String inviteId;
}
