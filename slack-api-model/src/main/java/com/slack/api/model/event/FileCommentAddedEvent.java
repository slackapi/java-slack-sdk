package com.slack.api.model.event;

import lombok.Data;

/**
 * The file_comment_added event is sent when a comment is added to file.
 * It is sent to all connected clients for users who can see the file.
 * Clients can use this notification to show comments in real-time for open files.
 * <p>
 * The file property includes the file ID, as well as a top-level file_id.
 * To obtain additional information about the file, use the files.info API method.
 * <p>
 * https://docs.slack.dev/reference/events/file_comment_added
 */
@Deprecated // https://docs.slack.dev/changelog/2018-05-file-threads-soon-tread
@Data
public class FileCommentAddedEvent implements Event {

    public static final String TYPE_NAME = "file_comment_added";

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