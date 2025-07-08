package com.slack.api.methods.request.admin.usergroups;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.usergroups.addTeams
 */
@Data
@Builder
public class AdminUsergroupsAddTeamsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * A comma separated list of encoded team IDs. Teams MUST belong to the organization
     */
    private List<String> teamIds;

    /**
     * An encoded usergroup ID
     */
    private String usergroupId;

    /**
     * A boolean to control whether to automatically create new team users for the usergroup members or not.
     * Default: false
     */
    private Boolean autoProvision;

}
