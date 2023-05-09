package com.slack.api.scim2.request;

import com.slack.api.scim2.SCIM2ApiRequest;
import com.slack.api.scim2.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersCreateRequest implements SCIM2ApiRequest {
    private String token;
    private User user;
}
