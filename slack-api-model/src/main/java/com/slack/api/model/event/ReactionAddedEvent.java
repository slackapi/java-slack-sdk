package com.slack.api.model.event;

import lombok.Data;

/**
 * When a reaction is added to an item the reaction_added event is sent to all connected clients
 * for users who can see the content that was reacted to.
 * <p>
 * The user field indicates the ID of the user who performed this event.
 * The item_user field represents the ID of the user that created the original item that has been reacted to.
 * <p>
 * Some messages aren't authored by "users," like those created by incoming webhooks.
 * reaction_added events related to these messages will not include an item_user.
 * <p>
 * The item field is a brief reference to what was reacted to. The above example describes a reaction to a message.
 * <p>
 * https://api.slack.com/events/reaction_added
 */
@Data
public class ReactionAddedEvent implements Event {

    public static final String TYPE_NAME = "reaction_added";

    private final String type = TYPE_NAME;
    private String user;
    private String reaction;
    private String itemUser;
    private Item item;
    private String eventTs;

    @Data
    public static class Item {
        private String type;
        private String channel;
        private String ts;

        private String file; // "type": "file" or "type": "file_comment"
        private String fileComment; // "type": "file_comment"
    }
}