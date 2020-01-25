package com.github.seratch.jslack.api.methods.response.files.remote;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.slack.api.model.File;
import lombok.Data;

@Data
public class FilesRemoteShareResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private File file;
}