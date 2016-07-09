package com.github.seratch.jslack.api.methods.response.files.comments;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class FilesCommentsDeleteResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
}