package com.slack.api.methods.request.files;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/files.delete
 */
@Data
@Builder
public class FilesDeleteRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `files:write:user`
     */
    private String token;

    /**
     * ID of file to delete.
     */
    private String file;

}