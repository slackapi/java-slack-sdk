package com.github.seratch.jslack.api.webhook;

import java.util.List;

import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.model.block.LayoutBlock;

import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/incoming-webhooks
 * 
 * Implementation of <a href="https://api.slack.com/reference/messaging/payload">Message Payloads</a>
 */
@Data
@Builder
public class Payload {

    /**
     * The first step is to prepare this message as a key/value pair in JSON.
     * For a simple message, your JSON payload only needs to define a text property, containing the text that will be posted to the channel.
     */
    private String text;

    /**
     * Incoming webhooks output to a default channel and can only send messages to a single channel at a time.
     * You can override a custom integration's configured channel by specifying the channel field in your JSON payload.
     * <p>
     * Specify a Slack channel by name with "channel": "#other-channel", or send a Slackbot message to a specific user with "channel": "@username".
     */
    private String channel;

    /**
     * Incoming webhooks originate from a default identity you configured when originally creating your webhook.
     * You can override a custom integration's configured name with the username field in your JSON payload.
     */
    private String username;

    /**
     * You can also override the bot icon either with icon_url or icon_emoji.
     */
    private String iconUrl;
    /**
     * You can also override the bot icon either with icon_url or icon_emoji.
     */
    private String iconEmoji;

    /**
     * An array of {@link LayoutBlock layout blocks} in the same format as described in the 
     * <a href="https://api.slack.com/messaging/composing/layouts#getting-started">layout block guide</a>.
     */
    private List<LayoutBlock> blocks;
    
    /**
     * An array of legacy secondary attachments. We recommend you use {@link #blocks} instead.
     */
    private List<Attachment> attachments;
}
