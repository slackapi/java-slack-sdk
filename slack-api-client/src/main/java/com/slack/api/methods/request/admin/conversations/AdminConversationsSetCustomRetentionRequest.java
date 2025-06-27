package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.conversations.setCustomRetention
 */
@Data
@Builder
public class AdminConversationsSetCustomRetentionRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The channel to set the retention policy for.
     */
    private String channelId;

    /**
     * The message retention duration in days to set for this channel
     */
    private Integer durationDays;

}
