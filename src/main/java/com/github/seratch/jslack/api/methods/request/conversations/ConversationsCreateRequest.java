package com.github.seratch.jslack.api.methods.request.conversations;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationsCreateRequest implements SlackApiRequest {

    private String token;
    private String name;
    private boolean isPrivate;
}