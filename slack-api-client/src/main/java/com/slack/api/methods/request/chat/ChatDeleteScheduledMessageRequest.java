package com.slack.api.methods.request.chat;

import com.slack.api.methods.SlackApiRequest;
import lombok.*;

/**
 * https://api.slack.com/methods/chat.deleteScheduledMessage
 */
@Data
@Builder
public class ChatDeleteScheduledMessageRequest implements SlackApiRequest {

    private String token;

    /**
     * The channel the scheduled_message is posting to
     */
    private String channel;

    /**
     * scheduled_message_id returned from call to chat.scheduleMessage
     */
    private String scheduledMessageId;

    /**
     * Pass true to delete the message as the authed user with chat:write:user scope.
     * Bot users in this context are considered authed users.
     * If unused or false, the message will be deleted with chat:write:bot scope.
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Boolean asUser;

    // NOTE: The default value is intentionally null to support workplace apps.
    public Boolean isAsUser() {
        return this.asUser;
    }

    // NOTE: The default value is intentionally null to support workplace apps.
    public void setAsUser(Boolean asUser) {
        this.asUser = asUser;
    }

}