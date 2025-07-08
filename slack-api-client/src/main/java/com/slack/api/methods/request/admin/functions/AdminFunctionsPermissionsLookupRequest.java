package com.slack.api.methods.request.admin.functions;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.functions.permissions.lookup
 */
@Data
@Builder
public class AdminFunctionsPermissionsLookupRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private List<String> functionIds;
}
