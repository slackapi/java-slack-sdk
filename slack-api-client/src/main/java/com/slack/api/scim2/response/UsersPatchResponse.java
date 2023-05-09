package com.slack.api.scim2.response;

import com.slack.api.scim2.SCIM2ApiResponse;
import com.slack.api.scim2.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UsersPatchResponse extends User implements SCIM2ApiResponse {
}
