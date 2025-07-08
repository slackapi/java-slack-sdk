package com.slack.api.model.event;

import com.slack.api.model.Attachment;
import com.slack.api.model.BotProfile;
import com.slack.api.model.File;
import com.slack.api.model.Message;
import com.slack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;

/**
 * A message is delivered from several sources:
 * <p>
 * - They are sent via the Real Time Messaging API when a message is sent to a channel to which you subscribe.
 * This message should immediately be displayed in the client.
 * - They are returned via calls to channels.history, im.history or groups.history
 * - They are returned as latest property on channel, group and im objects.
 * <p>
 * https://docs.slack.dev/reference/events/message
 */
@Data
public class MessageEvent implements Event {

    public static final String TYPE_NAME = "message";

    private String clientMsgId;

    private final String type = TYPE_NAME;
    private String team;
    private String channel;
    private String user;

    private String botId;
    private BotProfile botProfile;

    private String text;
    private List<LayoutBlock> blocks;
    private List<Attachment> attachments;
    private List<File> files;
    private Message.Metadata metadata;

    private String ts;

    private String parentUserId; // in the case of replies in thread
    private String threadTs; // in the case of replies in thread

    private String eventTs;
    private String channelType; // app_home, channel, group, im, mpim

    private Edited edited;

    @Data
    public static class Edited {
        private String user;
        private String ts;
    }
}
