package com.slack.api.methods.request.slack_lists;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackLists.items.info
 */
@Data
@Builder
public class SlackListsItemsInfoRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.`
     */
    private String token;

    /**
     * ID of the List containing the items.
     */
    private String listId;
    
    /**
     * ID of the row to get.
     */
    private String id;

    /**
     * Set to true to include is_subscribed data for the returned List row. (Optional)
     */
    @Builder.Default
    private Boolean includeIsSubscribed = false;
}