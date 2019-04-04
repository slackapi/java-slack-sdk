package com.github.seratch.jslack.api.model.event;

import lombok.Data;

/**
 * The email_domain_changed event is sent to all connections for a workspace
 * when the email domain settings for a workspace change.
 * Most clients can ignore this event.
 * <p>
 * https://api.slack.com/events/email_domain_changed
 */
@Data
public class EmailDomainChangedEvent implements Event {

    public static final String TYPE_NAME = "email_domain_changed";

    private final String type = TYPE_NAME;
    private String emailDomain;
    private String eventTs;
}