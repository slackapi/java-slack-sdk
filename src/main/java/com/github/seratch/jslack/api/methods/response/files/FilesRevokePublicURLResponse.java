package com.github.seratch.jslack.api.methods.response.files;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.File;
import lombok.Data;

@Data
public class FilesRevokePublicURLResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private File file;
}