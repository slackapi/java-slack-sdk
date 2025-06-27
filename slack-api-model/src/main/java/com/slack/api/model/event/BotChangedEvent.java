package com.slack.api.model.event;

import com.slack.api.model.BotIcons;
import lombok.Data;

/**
 * The bot_changed event is sent to all connections for a workspace when an integration "bot" is updated.
 * Clients can use this to update their local list of bots.
 * <p>
 * If the bot belongs to a Slack app, the event will also include an app_id pointing to its parent app.
 * <p>
 * https://docs.slack.dev/reference/events/bot_changed
 */
@Data
public class BotChangedEvent implements Event {

    public static final String TYPE_NAME = "bot_changed";

    private final String type = TYPE_NAME;
    private Bot bot;

    @Data
    public static class Bot {
        private String id;
        private String appId;
        private String name;
        private BotIcons icons;
    }

}