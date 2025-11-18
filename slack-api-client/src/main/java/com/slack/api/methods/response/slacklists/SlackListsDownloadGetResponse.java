package com.slack.api.methods.response.slacklists;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.slack.api.methods.SlackApiTextResponse;

import lombok.Data;

@Data

public class SlackListsDownloadGetResponse implements SlackApiTextResponse {
    
    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String status;

    @SerializedName("download_url")
    private String downloadUrl;
}