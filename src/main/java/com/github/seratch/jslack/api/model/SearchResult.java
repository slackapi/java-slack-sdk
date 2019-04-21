package com.github.seratch.jslack.api.model;

import lombok.Data;

import java.util.List;

@Data
public class SearchResult {
    private Integer total;
    private Pagination pagination;
    private Paging paging;
    private List<MatchedItem> matches;
    private List<String> refinements; // not sure the type yet

    @Data
    public static class Pagination {
        private Integer totalCount;
        private Integer page;
        private Integer perPage;
        private Integer pageCount;
        private Integer first;
        private Integer last;
    }
}
