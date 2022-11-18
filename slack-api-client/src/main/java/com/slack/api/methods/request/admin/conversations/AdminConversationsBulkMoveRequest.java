package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.conversations.bulkMove
 */
@Data
@Builder
public class AdminConversationsBulkMoveRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * An array of channel IDs.
     */
    private List<String> channelIds;

    /**
     * Target team ID
     */
    private String targetTeamId;

}
