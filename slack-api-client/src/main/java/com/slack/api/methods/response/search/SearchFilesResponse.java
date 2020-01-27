package com.slack.api.methods.response.search;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.SearchResult;
import lombok.Data;

@Data
public class SearchFilesResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String query;
    private SearchResult files;
}