package com.slack.api.methods.response.files.remote;

import com.slack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class FilesRemoteRemoveResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
}