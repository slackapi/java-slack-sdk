package com.github.seratch.jslack.api.model.event;

import lombok.Data;

/**
 * The file_comment_edited event is sent when a file comment is edited.
 * It is sent to all connected clients for users who can see the file.
 * Clients can use this notification to update comments in real-time for open files.
 * <p>
 * The file property includes the file ID, as well as a top-level file_id.
 * To obtain additional information about the file, use the files.info API method.
 * <p>
 * https://api.slack.com/events/file_comment_edited
 */
@Deprecated // https://api.slack.com/changelog/2018-05-file-threads-soon-tread
@Data
public class FileCommentEditedEvent implements Event {

    public static final String TYPE_NAME = "file_comment_edited";

    private final String type = TYPE_NAME;
    private FileComment comment;
    private String fileId;
    private File file;

    @Data
    public static class FileComment {
    }

    @Data
    public static class File {
        private String id;
    }
}