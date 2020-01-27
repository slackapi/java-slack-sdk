package com.slack.api.methods.response.files;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.File;
import com.slack.api.model.FileComment;
import com.slack.api.model.Paging;
import lombok.Data;

import java.util.List;

@Data
public class FilesSharedPublicURLResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private File file;

    // https://api.slack.com/changelog/2018-05-file-threads-soon-tread
    @Deprecated
    private List<FileComment> comments;
    @Deprecated
    private Paging paging;
}