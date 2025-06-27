package com.slack.api.methods.request.admin.usergroups;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.usergroups.addChannels
 */
@Data
@Builder
public class AdminUsergroupsAddChannelsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Comma separated string of channel IDs.
     */
    private List<String> channelIds;

    /**
     * The workspace to add default channels in.
     */
    private String teamId;

    /**
     * ID of the IDP group to add default channels for.
     */
    private String usergroupId;

}
