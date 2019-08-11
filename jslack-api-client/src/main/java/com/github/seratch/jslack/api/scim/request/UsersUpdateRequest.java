package com.github.seratch.jslack.api.scim.request;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import com.github.seratch.jslack.api.scim.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersUpdateRequest implements SlackApiRequest {
    private String token;
    private String id;
    private User user;
}
