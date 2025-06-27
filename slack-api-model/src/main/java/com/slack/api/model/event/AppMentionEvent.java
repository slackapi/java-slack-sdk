package com.slack.api.model.event;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.Attachment;
import com.slack.api.model.BotProfile;
import com.slack.api.model.File;
import com.slack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;

/**
 * This app event allows your app to subscribe to message events that directly mention your bot user.
 * <p>
 * Your Slack app must have a bot user configured and installed to utilize this event.
 * <p>
 * Instead of receiving all messages in a channel and having to filter through them for those mentioning your app,
 * as you would when subscribing to message.channels,
 * you'll receive only the messages pertinent to your app.
 * <p>
 * Messages sent via this subscription arrive as an app_mention event, not as a message as with other message.* event types.
 * However, your app can treat its contents similarly.
 * <p>
 * Messages sent to your app in direct message conversations are not dispatched via app_mention,
 * whether the app is explicitly mentioned or otherwise.
 * Subscribe to message.im events to receive messages directed to your bot user in direct message conversations.
 * <p>
 * https://docs.slack.dev/reference/events/app_mention
 */
@Data
public class AppMentionEvent implements Event {

    public static final String TYPE_NAME = "app_mention";

    private final String type = TYPE_NAME;
    private String clientMsgId;
    private String user;
    private String username;
    private String appId;
    private String botId;
    private BotProfile botProfile;
    private String subtype;
    private String text;
    private List<LayoutBlock> blocks;
    private List<Attachment> attachments;
    private List<File> files;
    private Boolean upload;
    private Boolean displayAsBot;
    private String ts;
    private String team;
    private String channel;

    // user_team, source_team, and user_profile
    // can exist when the user who mentioned this bot is in a different workspace/org
    private String userTeam;
    private String sourceTeam;
    private UserProfile userProfile;

    private Edited edited;
    private String eventTs;

    private String threadTs;

    @Data
    public static class UserProfile {
        private String name;
        private String firstName;
        private String realName;
        private String displayName;
        private String team;
        @SerializedName("is_restricted")
        private boolean restricted;
        @SerializedName("is_ultra_restricted")
        private boolean ultraRestricted;
        private String avatarHash;
        @SerializedName("image_72")
        private String image72;
    }

    @Data
    public static class Edited {
        private String user;
        private String ts;
    }
}