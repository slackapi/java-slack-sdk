package com.slack.api.methods.request.admin.functions;

import com.slack.api.methods.SlackApiRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.functions.permissions.set
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
    private List<Permission> permissions;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Permission {
        private String visibility;
        private String permissionType;
        private List<String> userIds;
        private List<String> teamIds;
        private List<String> orgIds;
    }
}
