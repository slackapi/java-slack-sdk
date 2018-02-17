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

    private String token;
    private String channel;
    private String messageTs;
}
