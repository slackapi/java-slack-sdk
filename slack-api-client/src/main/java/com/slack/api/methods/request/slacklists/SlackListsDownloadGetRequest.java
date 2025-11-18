package com.slack.api.methods.request.slacklists;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackLists.download.get
 */
@Data
@Builder
public class SlackListsDownloadGetRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.`
     */
    private String token;

    /**
     * ID of the List to export.
     */
    private String listId;
    
    /**
     * The ID of the recently started job to export the List.
     */
   private String jobId;
}