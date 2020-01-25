package com.slack.api.scim.request;

import com.slack.api.scim.SCIMApiRequest;
import com.slack.api.scim.model.Group;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupsCreateRequest implements SCIMApiRequest {
    private String token;
    private Group group;
}