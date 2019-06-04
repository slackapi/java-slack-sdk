package com.github.seratch.jslack.api.methods.response.reactions;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.Reaction;
import lombok.Data;

import java.util.List;

@Data
public class ReactionsGetResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String type;
    private String channel;
    private Message message;

    @Data
    public static class Message {
        private String type;
        private String subtype;
        private String text;
        private String ts;
        private String username;
        private String botId;
        private String permalink;
        private List<Reaction> reactions;
    }

}