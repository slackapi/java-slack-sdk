package com.slack.api.methods.request.files.comments;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * No longer supported - https://docs.slack.dev/changelog/2018-05-file-threads-soon-tread
 */
@Deprecated // https://docs.slack.dev/changelog/2018-05-file-threads-soon-tread
@Data
@Builder
public class FilesCommentsAddRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `files:write:user`
     */
    private String token;

    /**
     * File to add a comment to.
     */
    private String file;

    /**
     * Text of the comment to add.
     */
    private String comment;

}