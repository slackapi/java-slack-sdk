package com.github.seratch.jslack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * Subscribe to this event to receive deliveries as users uninstall your Slack app
 * and remove your app to channels &amp; conversations.
 * <p>
 * See Permissions API for further detail.
 * <p>
 * https://api.slack.com/events/resources_removed
 */
@Data
public class ResourcesRemovedEvent implements Event {

    public static final String TYPE_NAME = "resources_removed";

    private final String type = TYPE_NAME;
    private List<ResourceItem> resources;

    @Data
    public static class ResourceItem {
        private Resource resource;
        private List<String> scopes;
    }

    @Data
    public static class Resource {
        private String type;
        private Grant grant;
    }

    @Data
    public static class Grant {
        private String type;
        private String resourceId;
    }

}