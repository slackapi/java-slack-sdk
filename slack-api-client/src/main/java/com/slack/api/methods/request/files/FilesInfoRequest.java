package com.slack.api.methods.request.files;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/files.info
 */
@Data
@Builder
public class FilesInfoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `files:read`
     */
    private String token;

    /**
     * Specify a file by providing its ID.
     */
    private String file;

    private Integer count;

    private Integer page;

}