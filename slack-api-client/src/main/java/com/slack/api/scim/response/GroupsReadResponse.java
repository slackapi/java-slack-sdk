package com.slack.api.scim.response;

import com.slack.api.scim.SCIMApiResponse;
import com.slack.api.scim.model.Group;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class GroupsReadResponse extends Group implements SCIMApiResponse {
}
