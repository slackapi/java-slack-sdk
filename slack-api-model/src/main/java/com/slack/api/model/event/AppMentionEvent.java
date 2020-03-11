package com.slack.api.model.event;

import com.slack.api.model.BotProfile;
import lombok.Data;

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
 * https://api.slack.com/events/app_mention
 */
@Data
public class AppMentionEvent implements Event {

    public static final String TYPE_NAME = "app_mention";

    private final String type = TYPE_NAME;
    private String user;
    private String username;
    private String botId;
    private BotProfile botProfile;
    private String subtype;
    private String text;
    private String ts;
    private String team;
    private String channel;
    private String eventTs;

    private String threadTs;

}