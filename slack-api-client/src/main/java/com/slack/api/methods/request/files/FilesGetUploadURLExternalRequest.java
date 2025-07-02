package com.slack.api.methods.request.files;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/files.getUploadURLExternal
 */
@Data
@Builder
public class FilesGetUploadURLExternalRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `files:write`
     */
    private String token;

    /**
     * Name of the file being uploaded.
     */
    private String filename;

    /**
     * Size in bytes of the file being uploaded.
     */
    private Integer length;

    /**
     * Description of image for screen-reader.
     */
    private String altTxt;

    /**
     * Syntax type of the snippet being uploaded.
     */
    private String snippetType;

}