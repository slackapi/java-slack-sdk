package com.github.seratch.jslack.api.webhook;

import com.github.seratch.jslack.api.model.Attachment;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * https://api.slack.com/incoming-webhooks
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
     * You can use Slack's standard message markup to add simple formatting to your messages.
     * You can also use message attachments to display richly-formatted message blocks.
     */
    private List<Attachment> attachments = new ArrayList<>();

}
