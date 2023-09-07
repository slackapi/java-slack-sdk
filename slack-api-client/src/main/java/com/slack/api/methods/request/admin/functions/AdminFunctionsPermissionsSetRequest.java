package com.slack.api.methods.request.admin.functions;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.functions.permissions.set
 */
@Data
@Builder
public class AdminFunctionsPermissionsSetRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private String functionId;
    private String visibility; // named_entities, everyone, no_one
    private List<String> userIds;
}
