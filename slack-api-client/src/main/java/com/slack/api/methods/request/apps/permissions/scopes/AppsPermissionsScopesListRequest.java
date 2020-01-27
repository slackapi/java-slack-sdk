package com.slack.api.methods.request.apps.permissions.scopes;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppsPermissionsScopesListRequest implements SlackApiRequest {

    private String token;

}