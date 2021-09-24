package com.slack.api.methods.response.files.remote;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.File;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FilesRemoteInfoResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private File file;
}