package com.slack.api.scim2.request;

import com.slack.api.scim2.SCIM2ApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersSearchRequest implements SCIM2ApiRequest {
    private String token;
    // https://docs.slack.dev/reference/scim-api#filter
    private String filter;
    // https://docs.slack.dev/changelog/2019-06-have-scim-will-paginate
    private Integer count;
    private Integer startIndex;
}
