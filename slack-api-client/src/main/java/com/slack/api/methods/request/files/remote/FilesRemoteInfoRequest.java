package com.slack.api.methods.request.files.remote;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/files.remote.info
 */
@Data
@Builder
public class FilesRemoteInfoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `remote_files:read`
     */
    private String token;

    /**
     * Creator defined GUID for the file.
     */
    private String externalId;

    /**
     * Specify a file by providing its ID.
     */
    private String file;

}