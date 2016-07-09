package com.github.seratch.jslack.api.methods.response.search;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.SearchResult;
import lombok.Data;

@Data
public class SearchMessagesResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private String query;
    private SearchResult messages;
}