package com.slack.api.methods.request.slack_lists;

import com.google.gson.annotations.SerializedName;
import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackLists.items.list
 */
@Data
@Builder
public class SlackListsItemsListRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.`
     */
    private String token;

    /**
     * ID of the List.
     */
    @SerializedName("list_id")
    private String listId;
    

    /**
     * The maximum number of records to return. (Optional)
     */
    private Integer limit;
    
    /**
     * Next cursor for pagination. (Optional)
     */
    private String cursor;

    /**
     * Boolean indicating whether archived items or normal items should be returned. (Optional)
     */
    private Boolean archived;
}
