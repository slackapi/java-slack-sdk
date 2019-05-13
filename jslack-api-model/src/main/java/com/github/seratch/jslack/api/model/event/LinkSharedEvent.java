package com.github.seratch.jslack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * The thread_ts field only appears when the link was shared within a message thread.
 * <p>
 * https://api.slack.com/events/link_shared
 */
@Data
public class LinkSharedEvent implements Event {

    public static final String TYPE_NAME = "link_shared";

    private final String type = TYPE_NAME;
    private String channel;
    private String user;
    private String messageTs;
    private String threadTs;
    private List<Link> links;

    @Data
    public static class Link {
        private String domain;
        private String url;
    }
}