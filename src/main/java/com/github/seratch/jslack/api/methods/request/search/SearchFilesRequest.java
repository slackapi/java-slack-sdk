package com.github.seratch.jslack.api.methods.request.search;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchFilesRequest implements SlackApiRequest {

    private String token;
    private String query;
    private String sort;
    private String sortDir;
    private Integer highlight;
    private Integer count;
    private Integer page;
}