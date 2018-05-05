package com.github.seratch.jslack.api.methods.request.chat;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * A request to retrieve a permalink URL for a specific extant message
 *
 * @see <a href="https://api.slack.com/methods/chat.getPermalink">Slack chat.getPermalink API</a>
 */
@Data
@Builder
public class ChatGetPermalinkRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `none`
     */
    private String token;

    /**
     * The ID of the conversation or channel containing the message
     */
    private String channel;

    /**
     * A message's `ts` value, uniquely identifying it within a channel
     */
    private String messageTs;

}
