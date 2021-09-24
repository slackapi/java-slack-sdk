package com.slack.api.methods.response.search;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.SearchResult;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SearchMessagesResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String query;
    private SearchResult messages;
}