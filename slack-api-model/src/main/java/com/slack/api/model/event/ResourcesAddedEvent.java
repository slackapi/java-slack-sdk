package com.slack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * Subscribe to this event to receive deliveries as users install your Slack app,
 * add your app to channels and conversations, or approve your app for additional permissions and resources.
 * <p>
 * See Permissions API for further detail.
 * <p>
 * https://docs.slack.dev/changelog/2021-01-workspace-apps-retiring-the-platform-graveyard-in-aug-2021
 */
@Data
public class ResourcesAddedEvent implements Event {

    public static final String TYPE_NAME = "resources_added";

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