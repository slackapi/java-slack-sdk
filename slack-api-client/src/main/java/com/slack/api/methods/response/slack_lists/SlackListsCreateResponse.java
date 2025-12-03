package com.slack.api.methods.response.slack_lists;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.list.ListMetadata;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.methods.SlackApiTextResponse;

import lombok.Data;

@Data

public class SlackListsCreateResponse implements SlackApiTextResponse {
    
    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    @SerializedName("list_id")
    private String listId;
    
    @SerializedName("list_metadata")
    private ListMetadata listMetadata;

    private ResponseMetadata responseMetadata;
}