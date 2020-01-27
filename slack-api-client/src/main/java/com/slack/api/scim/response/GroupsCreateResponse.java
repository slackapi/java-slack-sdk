package com.slack.api.scim.response;

import com.slack.api.scim.SCIMApiResponse;
import com.slack.api.scim.model.Group;
import lombok.Data;

@Data
public class GroupsCreateResponse extends Group implements SCIMApiResponse {
}
