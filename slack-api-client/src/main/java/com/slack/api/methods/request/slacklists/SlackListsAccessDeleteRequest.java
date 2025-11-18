package com.slack.api.methods.request.slacklists;

import com.slack.api.methods.SlackApiRequest;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackLists.access.delete
 */
@Data
@Builder
public class SlackListsAccessDeleteRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Encoded ID of the List.
     */
    private String listId;

    /**
     * List of channels you wish to update access for. Can only be used if user_ids is not provided. (Optional)
     */
    private List<String> channelIds;

    /**
     * List of users you wish to update access for. Can only be used if channel_ids is not provided. (Optional)
     */
    private List<String> userIds;
}

