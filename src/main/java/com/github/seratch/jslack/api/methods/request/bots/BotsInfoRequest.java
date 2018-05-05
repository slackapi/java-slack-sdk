package com.github.seratch.jslack.api.methods.request.bots;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BotsInfoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `users:read`
     */
    private String token;

    /**
     * Bot user to get info on
     */
    private String bot;

}