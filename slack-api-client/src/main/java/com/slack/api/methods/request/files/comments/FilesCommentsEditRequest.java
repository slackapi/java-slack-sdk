package com.slack.api.methods.request.files.comments;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * No longer supported - https://api.slack.com/changelog/2018-05-file-threads-soon-tread
 */
@Deprecated // no longer supported
@Data
@Builder
public class FilesCommentsEditRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `files:write:user`
     */
    private String token;

    /**
     * File containing the comment to edit.
     */
    private String file;

    /**
     * The comment to edit.
     */
    private String id;

    /**
     * Text of the comment to edit.
     */
    private String comment;

}