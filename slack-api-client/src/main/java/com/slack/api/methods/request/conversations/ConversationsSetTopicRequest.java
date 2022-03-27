package com.slack.api.methods.request.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/conversations.setTopic
 */
@Data
@Builder
public class ConversationsSetTopicRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `conversations:write`
     */
    private String token;

    /**
     * Conversation to set the topic of
     */
    private String channel;

    /**
     * The new topic string. Does not support formatting or linkification.
     */
    private String topic;

}
