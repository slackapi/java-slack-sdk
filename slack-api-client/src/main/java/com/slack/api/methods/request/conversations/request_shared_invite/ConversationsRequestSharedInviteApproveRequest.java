package com.slack.api.methods.request.conversations.request_shared_invite;

import com.slack.api.methods.SlackApiRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/reference/methods/conversations.requestSharedInvite.approve
 */
@Data
@Builder
public class ConversationsRequestSharedInviteApproveRequest implements SlackApiRequest {

    private String token;

    /**
     * ID of the requested shared channel invite to approve.
     */
    private String inviteId;

    /**
     * Optional channel_id to which external user will be invited to. Will override the value on the requested invite.
     */
    private String channelId;

    /**
     * Optional boolean on whether the invited team will have post-only permissions in the channel.
     * Will override the value on the requested invite.
     */
    private Boolean isExternalLimited;

    /**
     * Object describing the text to send along with the invite. If this object is specified,
     * both text and is_override are required properties.
     * If is_override is set to true, text will override the original invitation message.
     * Otherwise, text will be appended to the original invitation message.
     * The total length of the message cannot exceed 560 characters.
     * If is_override is set to false, the length of text and the user specified message on the invite request in total
     * must be less than 560 characters.
     */
    private Message message;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String text;
        private boolean isOverride;
    }
}
