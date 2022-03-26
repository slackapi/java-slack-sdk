package com.slack.api.methods.request.files.comments;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/files.comments.delete
 */
@Data
@Builder
public class FilesCommentsDeleteRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `files:write:user`
     */
    private String token;

    /**
     * File to delete a comment from.
     */
    private String file;

    /**
     * The comment to delete.
     */
    private String id;
}