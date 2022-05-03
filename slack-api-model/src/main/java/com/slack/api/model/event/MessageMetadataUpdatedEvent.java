package com.slack.api.model.event;

import lombok.Data;

import java.util.Map;

/**
 * https://api.slack.com/events/message_metadata_updated
 */
@Data
public class MessageMetadataUpdatedEvent implements Event {

    public static final String TYPE_NAME = "message_metadata_updated";

    private final String type = TYPE_NAME;
    private String teamId;
    private String channelId;
    private String messageTs;
    private Map<String, Object> previousMetadata;
    private Map<String, Object> metadata;
    private String appId;
    private String botId;
    private String userId;
    private String eventTs;
}
