package com.slack.api.methods.response.files.remote;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.File;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class FilesRemoteListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<File> files;
    private ResponseMetadata responseMetadata;
}