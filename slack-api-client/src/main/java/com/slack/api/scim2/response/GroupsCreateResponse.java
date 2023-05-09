package com.slack.api.scim2.response;

import com.slack.api.scim2.SCIM2ApiResponse;
import com.slack.api.scim2.model.Group;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GroupsCreateResponse extends Group implements SCIM2ApiResponse {
}
