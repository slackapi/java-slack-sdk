package com.slack.api.methods.response.files;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.File;
import com.slack.api.model.Paging;
import lombok.Data;

import java.util.List;

@Data
public class FilesListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<File> files;
    private Paging paging;
}