package com.slack.api.methods.response.files.comments;

import com.slack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class FilesCommentsDeleteResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
}