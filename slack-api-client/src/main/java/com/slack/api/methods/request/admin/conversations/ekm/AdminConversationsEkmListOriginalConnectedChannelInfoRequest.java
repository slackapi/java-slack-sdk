package com.slack.api.methods.request.admin.conversations.ekm;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.conversations.ekm.listOriginalConnectedChannelInfo
 */
@Data
@Builder
public class AdminConversationsEkmListOriginalConnectedChannelInfoRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * A comma-separated list of channels to filter to.
     */
    private List<String> channelIds;

    /**
     * Set cursor to next_cursor returned by the previous call to list items in the next page.
     */
    private String cursor;

    /**
     * Maximum number of items to be returned. Must be between 1 - 1000 both inclusive.
     * Default: 100
     */
    private Integer limit;

    /**
     * A comma-separated list of the workspaces to which the channels you would like returned belong.
     */
    private List<String> teamIds;
}
