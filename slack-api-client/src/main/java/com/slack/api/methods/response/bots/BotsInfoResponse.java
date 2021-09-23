package com.slack.api.methods.response.bots;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.BotIcons;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BotsInfoResponse implements SlackApiTextResponse {

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
    private transient Map<String, List<String>> httpResponseHeaders;

    private Bot bot;
}