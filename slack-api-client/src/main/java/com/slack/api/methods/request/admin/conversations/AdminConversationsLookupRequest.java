package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.conversations.lookup
 */
@Data
@Builder
public class AdminConversationsLookupRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Filter by public channels where the most recent message was sent before last_message_activity
     */
    private Integer lastMessageActivityBefore;

    /**
     * Array of team IDs to filter by
     */
    private List<String> teamIds;

    /**
     * Set cursor to next_cursor returned by the previous call to list items in the next page.
     */
    private String cursor;

    /**
     * Maximum number of results
     * Default: 1000
     */
    private Integer limit;

    /**
     * Filter by public channels with member count equal to or less than the specified number
     * Default: 1
     */
    private Integer maxMemberCount;

}
