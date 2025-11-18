package com.slack.api.methods.request.slacklists;

import com.slack.api.methods.SlackApiRequest;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackLists.items.update
 */
@Data
@Builder
public class SlackListsItemsUpdateRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.`
     */
    private String token;

    /**
     * ID of the List containing the items.
     */
    private String listId;

    /**
     * Cells to update.
     */
    private List<Map<String, Object>> cells;
    
}