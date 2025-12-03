package com.slack.api.methods.request.slack_lists;

import com.google.gson.annotations.SerializedName;
import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackLists.download.start
 */
@Data
@Builder
public class SlackListsDownloadStartRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.`
     */
    private String token;

    /**
     * ID of the List to export.
     */
    @SerializedName("list_id")
    private String listId;
    
    /**
     * Include archived rows. (Optional)
     */
    @SerializedName("include_archived")
    private Boolean includeArchived;
}