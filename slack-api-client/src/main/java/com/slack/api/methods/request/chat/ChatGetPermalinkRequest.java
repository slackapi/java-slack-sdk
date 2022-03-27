package com.slack.api.methods.request.chat;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/chat.getPermalink
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
