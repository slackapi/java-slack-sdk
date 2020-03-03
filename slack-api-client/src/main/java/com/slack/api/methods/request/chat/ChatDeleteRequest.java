package com.slack.api.methods.request.chat;

import com.slack.api.methods.SlackApiRequest;
import lombok.*;

@Data
@Builder
public class ChatDeleteRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `chat:write`
     */
    private String token;

    /**
     * Timestamp of the message to be deleted.
     */
    private String ts;

    /**
     * Channel containing the message to be deleted.
     */
    private String channel;

    /**
     * Pass true to delete the message as the authed user with `chat:write:user` scope.
     * [Bot users](/bot-users) in this context are considered authed users.
     * If unused or false, the message will be deleted with `chat:write:bot` scope.
     */
    /**
     * Pass true to post the message as the authed user, instead of as a bot.
     * Defaults to false. See [authorship](#authorship) below.
     * <p>
     * NOTE: The default value is intentionally null to support workplace apps.
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