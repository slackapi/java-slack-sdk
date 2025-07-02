package com.slack.api.methods.response.files;

import com.google.gson.annotations.SerializedName;
import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.File;
import com.slack.api.model.FileComment;
import com.slack.api.model.Paging;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FilesInfoResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private File file;
    private String content;
    private String contentHighlightHtml;
    private String contentHighlightCss;
    @SerializedName("is_truncated")
    private boolean truncated;
    private Boolean contentHighlightHtmlTruncated;

    // https://docs.slack.dev/changelog/2018-05-file-threads-soon-tread
    @Deprecated
    private List<FileComment> comments;
    @Deprecated
    private Paging paging;

    private ResponseMetadata responseMetadata;
}