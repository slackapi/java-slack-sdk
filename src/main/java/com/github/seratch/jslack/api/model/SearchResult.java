package com.github.seratch.jslack.api.model;

import lombok.Data;

import java.util.List;

@Data
public class SearchResult {
    private Integer total;
    private Paging paging;
    private List<MatchedItem> matches;
}
