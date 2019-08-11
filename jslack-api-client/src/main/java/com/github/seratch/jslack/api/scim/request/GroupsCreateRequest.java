package com.github.seratch.jslack.api.scim.request;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import com.github.seratch.jslack.api.scim.model.Group;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupsCreateRequest implements SlackApiRequest {
    private String token;
    private Group group;
}