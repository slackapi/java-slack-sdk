package com.github.seratch.jslack.api.model.event;

import lombok.Data;

/**
 * The file_change event is sent when any property of a file is changed.
 * It is sent to all connected clients for all users that have permission to see the file.
 * <p>
 * The file property includes the file ID, as well as a top-level file_id.
 * To obtain additional information about the file, use the files.info API method.
 * <p>
 * https://api.slack.com/events/file_change
 */
@Data
public class FileChangeEvent implements Event {

    public static final String TYPE_NAME = "file_change";

    private final String type = TYPE_NAME;
    private String fileId;
    private File file;

    @Data
    public static class File {
        private String id;
    }
}