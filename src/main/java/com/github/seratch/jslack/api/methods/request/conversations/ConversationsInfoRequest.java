package com.github.seratch.jslack.api.methods.request.conversations;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationsInfoRequest implements SlackApiRequest {

    private String token;
    private String channel;
    private boolean includeLocale;
}