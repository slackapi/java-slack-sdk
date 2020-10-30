package com.slack.api.methods.response.chat;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Attachment;
import com.slack.api.model.BotProfile;
import com.slack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;

@Data
public class ChatScheduleMessageResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String scheduledMessageId;
    private String channel;
    private Integer postAt;
    private ScheduledMessage message;

    @Data
    public static class ScheduledMessage {
        private String botId;
        private BotProfile botProfile;

        private String type;
        private String team;
        private String user;

        private String text;
        private List<Attachment> attachments;
        private List<LayoutBlock> blocks;

    }
}