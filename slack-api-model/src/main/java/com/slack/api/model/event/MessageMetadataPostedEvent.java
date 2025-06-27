package com.slack.api.model.event;

import lombok.Data;

import java.util.Map;

/**
 * https://docs.slack.dev/reference/events/message_metadata_posted
 */
@Data
public class MessageMetadataPostedEvent implements Event {

    public static final String TYPE_NAME = "message_metadata_posted";

    private final String type = TYPE_NAME;
    private Map<String, Object> metadata;
    private String teamId;
    private String channelId;
    private String messageTs;
    private String appId;
    private String botId;
    private String userId;
    private String eventTs;
}
