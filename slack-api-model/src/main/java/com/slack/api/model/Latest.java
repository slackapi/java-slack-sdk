package com.slack.api.model;

import com.slack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;

@Data
public class Latest {

    private String clientMsgId;

    private String type;
    private String subtype;
    private String team;
    private String user;
    private String username;
    private String parentUserId;
    private String text;
    private String topic; // groups
    private List<Attachment> attachments;
    private List<LayoutBlock> blocks;
    private List<File> files;
    private List<Reaction> reactions;
    private Message.MessageRoot root;
    private boolean upload;
    private boolean displayAsBot;
    private String botId;
    private String botLink;
    private BotProfile botProfile;
    private String threadTs;
    private String ts;
    private Message.Icons icons;
    private List<String> xFiles;
    private Edited edited;

    @Data
    public static class Edited {
        private String user;
        private String ts;
    }
}
