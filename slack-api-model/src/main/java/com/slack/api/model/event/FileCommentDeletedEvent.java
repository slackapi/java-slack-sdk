package com.slack.api.model.event;

import lombok.Data;

/**
 * The file_comment_deleted event is sent when a file comment is deleted.
 * It is sent to all connected clients for users who can see the file.
 * Clients can use this notification to update comments in real-time for open files.
 * <p>
 * The file property includes the file ID, as well as a top-level file_id.
 * To obtain additional information about the file, use the files.info API method.
 * <p>
 * Unlike file_comment_added and file_comment_edited the comment property only contains the ID of the deleted comment,
 * not the full comment object.
 * <p>
 * https://docs.slack.dev/reference/events/file_comment_deleted
 */
@Deprecated // https://docs.slack.dev/changelog/2018-05-file-threads-soon-tread
@Data
public class FileCommentDeletedEvent implements Event {

    public static final String TYPE_NAME = "file_comment_deleted";

    private final String type = TYPE_NAME;
    private String comment;
    private String fileId;
    private File file;

    @Data
    public static class File {
        private String id;
    }
}