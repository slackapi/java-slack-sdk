package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.conversations.linkObjects
 */
@Data
@Builder
public class AdminConversationsLinkObjectsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Channel ID for Slack channel that will be linked to a Salesforce record.
     */
    private String channel;

    /**
     * Salesforce record ID (15 or 18 digit accepted). See [here](https://help.salesforce.com/s/articleView?id=000385008&type=1) for how to look up record ID.
     */
    private String recordId;

    /**
     * Salesforce org ID (15 or 18 digit accepted). See [here](https://help.salesforce.com/s/articleView?id=000385215&type=1) for how to look up Salesforce org ID.
     */
    private String salesforceOrgId;

}
