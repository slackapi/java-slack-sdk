package com.slack.api.methods.request.files.remote;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/files.remote.update
 */
@Data
@Builder
public class FilesRemoteUpdateRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `remote_files:write`
     */
    private String token;

    /**
     * Creator defined GUID for the file.
     */
    private String externalId;

    /**
     * URL of the remote file.
     */
    private String externalUrl;

    /**
     * Title of the file being shared.
     */
    private String title;

    /**
     * type of file
     */
    private String filetype;

    /**
     * File containing contents that can be used to improve searchability for the remote file.
     */
    private byte[] indexableFileContents;

    /**
     * Preview of the document via multipart/form-data.
     */
    private byte[] previewImage;

}