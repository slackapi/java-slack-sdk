package com.slack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * The file_deleted event is sent to all connected clients for a workspace when a file is deleted. Unlike most file events,
 * the file property contains a file ID and not a full file object.
 * <p>
 * https://api.slack.com/events/file_deleted
 */
@Data
public class FileDeletedEvent implements Event {

    public static final String TYPE_NAME = "file_deleted";

    private final String type = TYPE_NAME;
    private String fileId;
    private List<String> channelIds;
    private String eventTs;
}