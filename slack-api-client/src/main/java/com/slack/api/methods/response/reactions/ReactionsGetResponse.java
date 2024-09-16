package com.slack.api.methods.response.reactions;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Attachment;
import com.slack.api.model.BotProfile;
import com.slack.api.model.Message;
import com.slack.api.model.Reaction;
import com.slack.api.model.block.LayoutBlock;
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
        private List<Attachment> attachments;
        private List<LayoutBlock> blocks;
        private com.slack.api.model.Message.Metadata metadata;
        private com.slack.api.model.Message.AssistantAppThread assistantAppThread;
        private String permalink;
        private List<Reaction> reactions;
    }

}