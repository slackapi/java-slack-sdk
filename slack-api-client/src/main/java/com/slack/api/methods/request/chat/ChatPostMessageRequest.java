package com.slack.api.methods.request.chat;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.Attachment;
import com.slack.api.model.Message;
import com.slack.api.model.block.LayoutBlock;
import lombok.*;

import java.util.List;

/**
 * https://api.slack.com/methods/chat.postMessage
 */
@Data
@Builder
public class ChatPostMessageRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `chat:write`
     */
    private String token;

    /**
     * aSet your bot's user name.
     * Must be used in conjunction with `as_user` set to false, otherwise ignored. See [authorship](#authorship) below.
     */
    private String username;

    /**
     * Provide another message's `ts` value to make this message a reply. Avoid using a reply's `ts` value; use its parent instead.
     */
    private String threadTs;

    /**
     * Channel, private group, or IM channel to send message to.
     * Can be an encoded ID, or a name. See [below](#channels) for more details.
     */
    private String channel;

    /**
     * Text of the message to send. See below for an explanation of [formatting](#formatting).
     * This field is usually required, unless you're providing only `attachments` instead.
     */
    private String text;

    /**
     * Change how messages are treated. Defaults to `none`. See [below](#formatting).
     */
    private String parse;

    /**
     * Find and link channel names and usernames.
     */
    private boolean linkNames;

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
     * Pass true to enable unfurling of primarily text-based content.
     */
    private boolean unfurlLinks;

    /**
     * Pass false to disable unfurling of media content.
     */
    private boolean unfurlMedia;

    /**
     * Pass true to post the message as the authed user, instead of as a bot.
     * Defaults to false. See [authorship](#authorship) below.
     * <p>
     * NOTE: The default value is intentionally null to support workplace apps.
     */
    @Deprecated // use a user token to post a message as a user
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
     * Disable Slack markup parsing by setting to `false`. Enabled by default.
     */
    @Builder.Default
    private boolean mrkdwn = true;

    /**
     * URL to an image to use as the icon for this message.
     * Must be used in conjunction with `as_user` set to false, otherwise ignored. See [authorship](#authorship) below.
     */
    private String iconUrl;

    /**
     * Emoji to use as the icon for this message. Overrides `icon_url`.
     * Must be used in conjunction with `as_user` set to `false`, otherwise ignored. See [authorship](#authorship) below.
     */
    private String iconEmoji;

    /**
     * Used in conjunction with `thread_ts` and indicates whether reply should be made visible to
     * everyone in the channel or conversation. Defaults to `false`.
     */
    private boolean replyBroadcast;

}
