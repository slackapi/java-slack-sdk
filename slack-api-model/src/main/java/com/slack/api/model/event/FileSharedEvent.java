package com.slack.api.model.event;

import lombok.Data;

/**
 * The file_shared event is sent when a file is shared.
 * It is sent to all connected clients for all users that have permission to see the file.
 * The file property includes the file ID, as well as a top-level file_id.
 * To obtain additional information about the file, use the files.info API method.
 * <p>
 * https://docs.slack.dev/reference/events/file_shared
 */
@Data
public class FileSharedEvent implements Event {

    public static final String TYPE_NAME = "file_shared";

    private final String type = TYPE_NAME;
    private String fileId;
    private File file;
    private String channelId;
    private String userId;
    private String eventTs;

    @Data
    public static class File {
        private String id;
    }
}