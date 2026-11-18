package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.conversations.unlinkObjects
 */
@Data
@Builder
public class AdminConversationsUnlinkObjectsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Channel ID for Slack channel that will be unlinked from the Salesforce record.
     */
    private String channel;

    /**
     * Channel name you would like to give to the channel that is being unlinked from the Salesforce record.
     */
    private String newName;

}
