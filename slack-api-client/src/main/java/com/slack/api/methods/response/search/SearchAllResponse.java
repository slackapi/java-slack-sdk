package com.slack.api.methods.response.search;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.SearchResult;
import lombok.Data;

import java.util.List;

@Data
public class SearchAllResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String query;
    private SearchResult messages;
    private SearchResult files;
    private Posts posts;

    @Data
    public static class Posts {
        private Integer total;
        private List<String> matches;
    }
}