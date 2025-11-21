package com.slack.api.methods.response.slacklists;

import java.util.List;
import java.util.Map;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.File;
import com.slack.api.model.list.ListRecord;

import lombok.Data;

@Data

public class SlackListsItemsInfoResponse implements SlackApiTextResponse {
    
    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private File list;
    private ListRecord record;
    private List<ListRecord> subtasks;
}