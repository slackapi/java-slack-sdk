package com.slack.api.methods.response.reactions;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.BotProfile;
import com.slack.api.model.Reaction;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ReactionsGetResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String type;
    private String channel;
    private Message message;

    @Data
    public static class Message {
        private String type;
        private String subtype;
        private String text;
        private String ts;
        private String user;
        private String username;
        private String team;
        private String appId;
        private String botId;
        private BotProfile botProfile;
        private String permalink;
        private List<Reaction> reactions;
    }

}