package com.slack.api.model.event;

import lombok.Data;

/**
 * https://docs.slack.dev/reference/events/entity_details_requested
 */
@Data
public class EntityDetailsRequestedEvent implements Event {

    public static final String TYPE_NAME = "entity_details_requested";

    private final String type = TYPE_NAME;
    private String user;
    private String userLocale;
    private Link link;
    private String entityUrl;
    private String appUnfurlUrl;
    private String trigger_id;
    private ExternalRef external_ref;
    private String channel;
    private String messageTs;
    // The thread_ts field only appears when the link was shared within a message thread.
    private String threadTs;
    private String eventTs;

    @Data
    public static class Link {
        private String domain;
        private String url;
    }

    @Data
    public static class ExternalRef {
        private String id;
        private String type;
    }
}
