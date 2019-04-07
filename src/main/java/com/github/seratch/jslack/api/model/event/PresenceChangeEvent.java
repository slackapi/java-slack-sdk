package com.github.seratch.jslack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * The presence_change event is sent to connections for a workspace
 * when a user changes presence status and the app has subscribed using presence_sub.
 * Clients can use this to update their local list of users' presence.
 * <p>
 * If a user updates their presence manually, the manual_presence_change event will also be sent to all connected clients for that user.
 * <p>
 * There are two forms of this event. When only one user's presence is being communicated,
 * you'll receive a user field with a single user ID present within. This form is deprecated.
 * <p>
 * Pass the batch_presence_aware=1 parameter to rtm.start or rtm.connect to instruct the Slack message server
 * to batch your presence messages and send a users attribute instead, containing an array of users changing to the same status.
 * <p>
 * Sometimes you'll get a single event for a single user but if you use batch_presence_aware=1,
 * that single user event will be single item in the users array.
 * <p>
 * In case you missed that: if you send batch_presence_aware=1 then the shape of presence_change events changes.
 * Instead of a string-based user field, you'll get users, an array.
 * <p>
 * If you're writing a library that supports presence_change events, you should be prepared to handle both kinds of presence events.
 * <p>
 * RTM API Presence is now only available via subscription.
 * As of January 2018, presence_change events are not dispatched without presence subscriptions established with presence_sub.
 * Relatedly, current user presence status is no longer communicated in rtm.start. Learn more.
 */
@Data
public class PresenceChangeEvent implements Event {

    public static final String TYPE_NAME = "presence_change";

    private final String type = TYPE_NAME;
    private String user; // Single-user presence change event:
    private List<String> users; // Multiple-user batch presence change event:
    private String presence;

}