package com.slack.api.scim.response;

import com.slack.api.scim.SCIMApiResponse;
import com.slack.api.scim.model.Group;
import lombok.Data;

@Data
public class GroupsPatchResponse extends Group implements SCIMApiResponse {
}
