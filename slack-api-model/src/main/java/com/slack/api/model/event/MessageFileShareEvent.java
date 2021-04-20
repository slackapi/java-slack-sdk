package com.slack.api.model.event;

import com.slack.api.model.Attachment;
import com.slack.api.model.File;
import com.slack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;

/**
 * A file was shared into a channel
 * https://api.slack.com/events/message/file_share
 */
@Data
public class MessageFileShareEvent implements Event {

    public static final String TYPE_NAME = "message";
    public static final String SUBTYPE_NAME = "file_share";

    private final String type = TYPE_NAME;
    private final String subtype = SUBTYPE_NAME;

    private String text;
    private List<LayoutBlock> blocks;
    private List<Attachment> attachments;
    private List<File> files;
    private Boolean upload;
    private Boolean displayAsBot;
    private List<String> xFiles;

    private String user;
    private String parentUserId;
    private String ts;
    private String threadTs;
    private String channel;
    private String eventTs;
    private String channelType; // app_home, channel, group, im, mpim
}
