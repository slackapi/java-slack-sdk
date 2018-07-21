package com.github.seratch.jslack.api.methods.request.conversations;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

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
