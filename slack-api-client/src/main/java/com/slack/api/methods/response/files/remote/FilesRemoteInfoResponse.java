package com.slack.api.methods.response.files.remote;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.File;
import lombok.Data;

@Data
public class FilesRemoteInfoResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private File file;
}