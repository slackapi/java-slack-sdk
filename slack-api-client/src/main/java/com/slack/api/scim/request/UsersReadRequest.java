package com.slack.api.scim.request;

import com.slack.api.scim.SCIMApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersReadRequest implements SCIMApiRequest {
    private String token;
    private String id;
}
