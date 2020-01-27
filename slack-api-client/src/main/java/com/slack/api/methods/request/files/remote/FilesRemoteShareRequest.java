package com.slack.api.methods.request.files.remote;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FilesRemoteShareRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `remote_files:write`
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

    /**
     * Comma-separated list of channel IDs where the file will be shared.
     */
    private List<String> channels;

}