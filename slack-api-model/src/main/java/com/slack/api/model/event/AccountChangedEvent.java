package com.slack.api.model.event;

import lombok.Data;

/**
 * The list of accounts a user is signed into has changed
 * <p>
 * The accounts_changed event is used by our web client
 * to maintain a list of logged-in accounts.
 * Other clients should ignore this event.
 * <p>
 * https://docs.slack.dev/reference/events/accounts_changed
 */
@Data
public class AccountChangedEvent implements Event {

    public static final String TYPE_NAME = "accounts_changed";

    private final String type = TYPE_NAME;
}