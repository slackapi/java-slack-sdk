package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.conversations.createForObjects
 */
@Data
@Builder
public class AdminConversationsCreateForObjectsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Object / Record ID (15 or 18 digit accepted). See [here](https://help.salesforce.com/s/articleView?id=000385008&type=1) for how to look up an ID.
     */
    private String objectId;

    /**
     * Salesforce org ID (15 or 18 digit accepted). See [here](https://help.salesforce.com/s/articleView?id=000385215&type=1) for how to look up Salesforce org ID.
     */
    private String salesforceOrgId;

    /**
     * Optional flag to add all team members related to the object to the newly created Salesforce channel. When true, adds a maximum of 100 team members to the channel.
     */
    private Boolean inviteObjectTeam;

}
