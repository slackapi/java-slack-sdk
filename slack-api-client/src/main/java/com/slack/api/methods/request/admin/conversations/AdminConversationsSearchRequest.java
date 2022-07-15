package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.conversations.search
 */
@Data
@Builder
public class AdminConversationsSearchRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Set cursor to next_cursor returned by the previous call to list items in the next page.
     */
    private String cursor;

    /**
     * Maximum number of items to be returned. Must be between 1 - 20 both inclusive. Default is 10.
     * Default: 10
     */
    private Integer limit;

    /**
     * Name of the channel to query by.
     */
    private String query;

    /**
     * The type of channel to include or exclude in the search.
     * For example private will search private channels, while private_exclude will exclude them.
     * For a full list of types, check the Types section.
     * https://api.slack.com/methods/admin.conversations.search#types
     */
    private List<String> searchChannelTypes;

    /**
     * Possible values are relevant (search ranking based on what we think is closest), name (alphabetical),
     * member_count (number of users in the channel), and created (date channel was created).
     * You can optionally pair this with the sort_dir arg to change how it is sorted
     * Default: member_count
     */
    private String sort;

    /**
     * Sort direction. Possible values are asc for ascending order like (1, 2, 3) or (a, b, c),
     * and desc for descending order like (3, 2, 1) or (c, b, a)
     * Default: desc
     */
    private String sortDir;

    /**
     * Comma separated string of team IDs, signifying the workspaces to search through.
     */
    private List<String> teamIds;

}
