package com.github.seratch.jslack.api.methods.request.files.comments;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

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