package com.slack.api.methods.response.slacklists;

import java.util.List;
import java.util.Map;

import com.slack.api.methods.SlackApiTextResponse;

import lombok.Data;

@Data

public class SlackListsItemsDeleteResponse implements SlackApiTextResponse {
    
    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;
}