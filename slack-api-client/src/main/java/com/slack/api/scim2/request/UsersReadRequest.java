package com.slack.api.scim2.request;

import com.slack.api.scim2.SCIM2ApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersReadRequest implements SCIM2ApiRequest {
    private String token;
    private String id;
}
