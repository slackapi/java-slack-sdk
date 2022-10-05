package com.slack.api.methods.response.files;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FilesGetUploadURLExternalResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String fileId; // File ID such as "F0TA2CQQY"
    private String uploadUrl; // "https://files.slack.com/upload/v1/..."
    private ResponseMetadata responseMetadata;
}