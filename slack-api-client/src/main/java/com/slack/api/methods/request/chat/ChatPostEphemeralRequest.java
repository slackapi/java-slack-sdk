package com.slack.api.methods.request.chat;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.Attachment;
import com.slack.api.model.block.LayoutBlock;
import lombok.*;

import java.util.List;

@Data
@Builder
public class ChatPostEphemeralRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `chat:write`
     */
    private String token;

    /**
     * Channel, private group, or IM channel to send message to. Can be an encoded ID, or a name.
     */
    private String channel;

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
     * Provide another message's ts value to post this message in a thread.
     * Avoid using a reply's ts value; use its parent's value instead.
     * Ephemeral messages in threads are only shown if there is already an active thread.
     */
    private String threadTs;

    /**
     * Emoji to use as the icon for this message.
     * Overrides icon_url. Must be used in conjunction with as_user set to false, otherwise ignored.
     * See authorship below.
     * https://api.slack.com/methods/chat.postEphemeral#authorship
     */
    private String iconEmoji;

    /**
     * URL to an image to use as the icon for this message.
     * Must be used in conjunction with as_user set to false, otherwise ignored.
     * See authorship below.
     * https://api.slack.com/methods/chat.postEphemeral#authorship
     */
    private String iconUrl;

    /**
     * Set your bot's user name.
     * Must be used in conjunction with as_user set to false, otherwise ignored.
     * See authorship below.
     * https://api.slack.com/methods/chat.postEphemeral#authorship
     */
    private String username;

    /**
     * Find and link channel names and usernames.
     */
    private boolean linkNames;

    /**
     * Change how messages are treated. Defaults to `none`. See [below](#formatting).
     */
    private String parse;

}