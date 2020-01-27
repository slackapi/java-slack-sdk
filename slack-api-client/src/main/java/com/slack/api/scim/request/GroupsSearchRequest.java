package com.slack.api.scim.request;

import com.slack.api.scim.SCIMApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupsSearchRequest implements SCIMApiRequest {
    private String token;
    // https://api.slack.com/scim#filter
    private String filter;
    // https://api.slack.com/changelog/2019-06-have-scim-will-paginate
    private Integer count;
    private Integer startIndex;
}
