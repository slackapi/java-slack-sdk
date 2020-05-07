package com.slack.api.methods.request.admin.usergroups;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.usergroups.listChannels
 */
@Data
@Builder
public class AdminUsergroupsListChannelsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * ID of the IDP group to list default channels for.
     */
    private String usergroupId;

    /**
     * Flag to include or exclude the count of members per channel.
     */
    private Boolean includeNumMembers;

    /**
     * ID of the the workspace.
     */
    private String teamId;

}
