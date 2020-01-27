package com.slack.api.scim.request;

import com.slack.api.scim.SCIMApiRequest;
import com.slack.api.scim.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersUpdateRequest implements SCIMApiRequest {
    private String token;
    private String id;
    private User user;
}
