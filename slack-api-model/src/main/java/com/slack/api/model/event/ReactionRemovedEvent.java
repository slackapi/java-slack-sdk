package com.slack.api.model.event;

import lombok.Data;

/**
 * When a reaction is removed from an item the reaction_removed event is sent to all connected clients
 * for users who can see the content that had the reaction.
 * <p>
 * The user field indicates the ID of the user who performed this event.
 * The item_user field represents the ID of the user that created the original item that has been reacted to.
 * <p>
 * Some messages aren't authored by "users," like those created by incoming webhooks.
 * reaction_removed events related to these messages will not include an item_user.
 * <p>
 * The item field is a brief reference to what the reaction was attached to.
 * The above example describes a reaction being removed from a message.
 * <p>
 * https://api.slack.com/events/reaction_removed
 */
@Data
public class ReactionRemovedEvent implements Event {

    public static final String TYPE_NAME = "reaction_removed";

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