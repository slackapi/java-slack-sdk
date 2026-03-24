package com.slack.api.methods.response.slack_lists;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.methods.SlackApiTextResponse;

import lombok.Data;

@Data

public class SlackListsDownloadStartResponse implements SlackApiTextResponse {
    
    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    @SerializedName("job_id")
    private String jobId;

    private ResponseMetadata responseMetadata;
}