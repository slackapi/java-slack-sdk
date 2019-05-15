package com.github.seratch.jslack.api.model.event;

import lombok.Data;

/**
 * The file_unshared event is sent when a file is unshared.
 * It is sent to all connected clients for all users that had permission to see the file.
 * The file property includes the file ID, as well as a top-level file_id.
 * To obtain additional information about the unshared file, use the files.info API method.
 * <p>
 * https://api.slack.com/events/file_unshared
 */
@Data
public class FileUnsharedEvent implements Event {

    public static final String TYPE_NAME = "file_unshared";

    private final String type = TYPE_NAME;
    private String fileId;
    private File file;

    @Data
    public static class File {
        private String id;
    }
}