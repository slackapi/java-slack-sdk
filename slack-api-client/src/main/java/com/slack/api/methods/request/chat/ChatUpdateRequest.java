package com.slack.api.methods.request.chat;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.Attachment;
import com.slack.api.model.Message;
import com.slack.api.model.block.LayoutBlock;
import lombok.*;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/chat.update
 */
@Data
@Builder
public class ChatUpdateRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `chat:write`
     */
    private String token;

    /**
     * Channel, private group, or IM channel to send message to. Can be an encoded ID, or a name.
     */
    private String channel;

    /**
     * Timestamp of the message to be updated.
     */
    private String ts;

    /**
     * Text of the message to send.
     * See below for an explanation of [formatting](#formatting).
     * This field is usually required, unless you're providing only `attachments` instead.
     */
    private String text;

    /**
     * `id` of the user who will receive the ephemeral message. The user should be in the channel specified by the `channel` argument.
     */
    private String user;

    /**
     * Broadcast an existing thread reply to make it visible to everyone in the channel or conversation.
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Boolean replyBroadcast;

    // NOTE: The default value is intentionally null
    public Boolean isReplyBroadcast() {
        return this.replyBroadcast;
    }

    // NOTE: The default value is intentionally null
    public void setReplyBroadcast(Boolean replyBroadcast) {
        this.replyBroadcast = replyBroadcast;
    }

    /**
     * Pass true to post the message as the authed user, instead of as a bot.
     * Defaults to false. See [authorship](#authorship) below.
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

    /**
     * Accepts message text formatted in markdown. This argument should not be used in conjunction with blocks or text. Limit this field to 12,000 characters.
     */
    private String markdownText;

    /**
     * JSON object with event_type and event_payload fields, presented as a URL-encoded string.
     * Metadata you post to Slack is accessible to any app or user who is a member of that workspace.
     */
    private Message.Metadata metadata;

    /**
     * JSON object with event_type and event_payload fields, presented as a URL-encoded string.
     * Metadata you post to Slack is accessible to any app or user who is a member of that workspace.
     */
    private String metadataAsString;

    /**
     * A JSON-based array of structured blocks, presented as a URL-encoded string.
     */
    private List<LayoutBlock> blocks;

    /**
     * A JSON-based array of structured blocks as a String, presented as a URL-encoded string.
     */
    private String blocksAsString;

    /**
     * A JSON-based array of structured attachments, presented as a URL-encoded string.
     */
    private List<Attachment> attachments;

    /**
     * A JSON-based array of structured attachments, presented as a URL-encoded string.
     */
    private String attachmentsAsString;

    /**
     * Array of new file ids that will be sent with this message.
     */
    private List<String> fileIds;

    /**
     * Find and link channel names and usernames.
     */
    private boolean linkNames;

    /**
     * Change how messages are treated. Defaults to `none`. See [below](#formatting).
     */
    private String parse;

}
