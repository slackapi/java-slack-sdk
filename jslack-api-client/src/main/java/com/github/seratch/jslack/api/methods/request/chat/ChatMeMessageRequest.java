package com.github.seratch.jslack.api.methods.request.chat;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMeMessageRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `chat:write:user`
     */
    private String token;

    /**
     * Channel to send message to. Can be a public channel, private group or IM channel. Can be an encoded ID, or a name.
     */
    private String channel;

    /**
     * Text of the message to send.
     */
    private String text;

}