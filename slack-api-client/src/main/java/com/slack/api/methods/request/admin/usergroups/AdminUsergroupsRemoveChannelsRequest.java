package com.slack.api.methods.request.admin.usergroups;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.usergroups.removeChannels
 */
@Data
@Builder
public class AdminUsergroupsRemoveChannelsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Comma separated string of channel IDs.
     */
    private List<String> channelIds;

    /**
     * ID of the IDP Group.
     */
    private String usergroupId;

}
