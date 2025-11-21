package com.slack.api.methods.request.slacklists;

import com.slack.api.methods.SlackApiRequest;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackLists.items.create
 */
@Data
@Builder
public class SlackListsItemsCreateRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.`
     */
    private String token;

    /**
     * ID of the List containing the items.
     */
    private String listId;
    
    /**
     * ID of the record to make a copy of. (Optional)
     */
    private String duplicatedItemId;

    /**
     * ID of the parent record for this subtask. (Optional)
     */
    private String parentItemId;

    /**
     * Initial item data. (Optional)
     */
    private List<Map<String, Object>> initialFields;
}