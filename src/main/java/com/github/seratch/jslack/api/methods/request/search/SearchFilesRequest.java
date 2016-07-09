package com.github.seratch.jslack.api.methods.request.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchFilesRequest {

    private String token;
    private String query;
    private String sort;
    private String sortDir;
    private Integer highlight;
    private Integer count;
    private Integer page;
}