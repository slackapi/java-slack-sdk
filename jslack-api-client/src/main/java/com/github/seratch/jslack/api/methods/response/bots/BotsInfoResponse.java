package com.github.seratch.jslack.api.methods.response.bots;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.BotIcons;
import lombok.Data;

@Data
public class BotsInfoResponse implements SlackApiResponse {

    @Data
    public static class Bot {
        private String id;
        private String appId;
        private String userId;
        private String name;
        private boolean deleted;
        private Integer updated;
        private BotIcons icons;
    }

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private Bot bot;
}