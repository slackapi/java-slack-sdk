package com.github.seratch.jslack.api.methods.response.files.comments;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.slack.api.model.FileComment;
import lombok.Data;

@Data
public class FilesCommentsAddResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String reqMethod;

    private FileComment comment;
}