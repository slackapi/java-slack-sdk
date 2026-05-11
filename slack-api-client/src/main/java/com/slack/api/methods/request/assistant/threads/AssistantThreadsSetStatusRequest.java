package com.slack.api.methods.request.assistant.threads;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/assistant.threads.setStatus
 */
@Data
@Builder
public class AssistantThreadsSetStatusRequest implements SlackApiRequest {

    private String token;

    /**
     * Channel ID containing the assistant thread.
     */
    private String channelId;

    /**
     * Message timestamp of the thread of where to set the status.
     */
    private String threadTs;

    /**
     * Status of the specified bot user, e.g. 'is thinking...'
     */
    private String status;

    /**
     * The list of messages to rotate through as a loading indicator.
     */
    private List<String> loadingMessages;

    /**
     * Emoji to use as the icon for this message. Overrides icon_url.
     */
    private String iconEmoji;

    /**
     * Image URL to use as the icon for this message.
     */
    private String iconUrl;

    /**
     * The bot's username to display.
     */
    private String username;
}
