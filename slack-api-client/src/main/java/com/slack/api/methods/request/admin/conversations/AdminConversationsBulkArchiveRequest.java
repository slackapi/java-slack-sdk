package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.conversations.bulkArchive
 */
@Data
@Builder
public class AdminConversationsBulkArchiveRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * An array of channel IDs.
     */
    private List<String> channelIds;

}
