package com.slack.api.methods.response.files;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.File;
import lombok.Data;

@Data
public class FilesUploadResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private File file;
}