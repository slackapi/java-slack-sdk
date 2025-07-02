package com.slack.api.scim.request;

import com.slack.api.scim.SCIMApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersSearchRequest implements SCIMApiRequest {
    private String token;
    // https://docs.slack.dev/reference/scim-api#filter
    private String filter;
    // https://docs.slack.dev/changelog/2019-06-have-scim-will-paginate
    private Integer count;
    private Integer startIndex;
}
