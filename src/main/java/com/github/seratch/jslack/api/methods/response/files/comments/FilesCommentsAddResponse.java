package com.github.seratch.jslack.api.methods.response.files.comments;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.FileComment;
import lombok.Data;

@Data
public class FilesCommentsAddResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private FileComment comment;
}