package com.github.seratch.jslack.api.methods.response.files;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.slack.api.model.File;
import com.slack.api.model.FileComment;
import com.slack.api.model.Paging;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class FilesInfoResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private File file;
    private String content;
    private String contentHighlightHtml;
    private String contentHighlightCss;
    @SerializedName("is_truncated")
    private boolean truncated;

    // https://api.slack.com/changelog/2018-05-file-threads-soon-tread
    @Deprecated
    private List<FileComment> comments;
    @Deprecated
    private Paging paging;
}