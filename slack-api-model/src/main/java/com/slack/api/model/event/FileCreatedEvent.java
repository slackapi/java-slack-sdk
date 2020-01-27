package com.slack.api.model.event;

import lombok.Data;

/**
 * The file_created event is sent to all connected clients for a user when that user uploads a file to Slack.
 * The file property includes the file ID, as well as a top-level file_id.
 * To obtain additional information about the file, use the files.info API method.
 * <p>
 * When a file is shared with other members of the workspace (which can happen at upload time)
 * a file_shared event will also be sent.
 * <p>
 * https://api.slack.com/events/file_created
 */
@Data
public class FileCreatedEvent implements Event {

    public static final String TYPE_NAME = "file_created";

    private final String type = TYPE_NAME;
    private String fileId;
    private File file;

    @Data
    public static class File {
        private String id;
    }
}