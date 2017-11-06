package com.github.seratch.jslack.api.methods.request.conversations;

import java.util.List;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationsInviteRequest implements SlackApiRequest {

    private String token;
    private String channel;
    private List<String> users;
}