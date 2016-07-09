package com.github.seratch.jslack.api.methods.response.files;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.File;
import com.github.seratch.jslack.api.model.FileComment;
import com.github.seratch.jslack.api.model.Paging;
import lombok.Data;

import java.util.List;

@Data
public class FilesSharedPublicURLResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private File file;
    private List<FileComment> comments;
    private Paging paging;
}