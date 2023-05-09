package com.slack.api.scim2.request;

import com.slack.api.scim2.SCIM2ApiRequest;
import com.slack.api.scim2.model.Group;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupsCreateRequest implements SCIM2ApiRequest {
    private String token;
    private Group group;
}