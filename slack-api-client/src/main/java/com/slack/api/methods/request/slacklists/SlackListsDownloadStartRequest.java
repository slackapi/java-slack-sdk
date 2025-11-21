package com.slack.api.methods.request.slacklists;

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
    private String listId;
    
    /**
     * Include archived rows. (Optional)
     */
    private Boolean includeArchived;
}