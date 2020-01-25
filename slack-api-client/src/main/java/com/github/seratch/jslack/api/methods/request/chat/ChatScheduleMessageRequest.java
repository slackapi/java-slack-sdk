package com.github.seratch.jslack.api.methods.request.chat;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import com.slack.api.model.Attachment;
import com.slack.api.model.block.LayoutBlock;
import lombok.*;

import java.util.List;

@Data
@Builder
public class ChatScheduleMessageRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `chat:write`
     */
    private String token;

    /**
     * Channel, private group, or DM channel to send message to. Can be an encoded ID, or a name. See below for more details.
     */
    private String channel;

    /**
     * Unix EPOCH timestamp of time in future to send the message.
     */
    private Integer postAt;

    /**
     * Text of the message to send. See below for an explanation of formatting.
     * This field is usually required, unless you're providing only attachments instead.
     * Provide no more than 40,000 characters or risk truncation.
     */
    private String text;

    /**
     * Pass true to post the message as the authed user, instead of as a bot. Defaults to false. See authorship below.
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
     * A JSON-based array of structured attachments, presented as a URL-encoded string.
     */
    private List<Attachment> attachments;

    /**
     * A JSON-based array of structured attachments, presented as a URL-encoded string.
     */
    private String attachmentsAsString;

    /**
     * A JSON-based array of structured blocks, presented as a URL-encoded string.
     */
    private List<LayoutBlock> blocks;

    /**
     * A JSON-based array of structured blocks as a String, presented as a URL-encoded string.
     */
    private String blocksAsString;

    /**
     * Find and link channel names and usernames.
     */
    private boolean linkNames;

    /**
     * Change how messages are treated. Defaults to none. See below.
     */
    private String parse;

    /**
     * Used in conjunction with thread_ts and indicates whether reply should be made visible to everyone
     * in the channel or conversation. Defaults to false.
     */
    private boolean replyBroadcast;

    /**
     * Provide another message's ts value to make this message a reply.
     * Avoid using a reply's ts value; use its parent instead.
     */
    private String threadTs;

    /**
     * Pass true to enable unfurling of primarily text-based content.
     */
    private boolean unfurlLinks;

    /**
     * Pass false to disable unfurling of media content.
     */
    private boolean unfurlMedia;

}