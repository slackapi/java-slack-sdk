package com.slack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * The email_domain_changed event is sent to all connections for a workspace
 * when the email domain settings for a workspace change.
 * Most clients can ignore this event.
 * <p>
 * https://api.slack.com/events/emoji_changed
 */
@Data
public class EmojiChangedEvent implements Event {

    public static final String TYPE_NAME = "emoji_changed";

    private final String type = TYPE_NAME;

    private String subtype; // possible values: add, remove

    private List<String> names; // only for subtype:remove

    private String name; // only for subtype:add

    /**
     * The URL of the image
     */
    private String value; // only for subtype:add

    private String eventTs;
}