package com.slack.api.methods.response.files;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

@Data
public class FilesDeleteResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
}