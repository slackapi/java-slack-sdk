package com.slack.api.model.event;

import lombok.Data;

import java.util.Map;

/**
 * https://docs.slack.dev/reference/events/message_metadata_deleted
 */
@Data
public class MessageMetadataDeletedEvent implements Event {

    public static final String TYPE_NAME = "message_metadata_deleted";

    private final String type = TYPE_NAME;
    private Map<String, Object> previousMetadata;
    private String teamId;
    private String channelId;
    private String messageTs;
    private String deletedTs;
    private String appId;
    private String botId;
    private String userId;
    private String eventTs;
}
