package com.slack.api.methods.request.slacklists;

import com.google.gson.annotations.SerializedName;
import com.slack.api.methods.SlackApiRequest;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackLists.access.set
 */
@Data
@Builder
public class SlackListsAccessSetRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;
        
    /**
     * Encoded ID of the List.
     */
    @SerializedName("list_id")
    private String listId;

    /**
     * Desired level of access.
     */
    @SerializedName("access_level")
    private String accessLevel;

    /**
     * List of channels you wish to update access for. Can only be used if user_ids is not provided. (Optional)
     */
    @SerializedName("channel_ids")
    private List<String> channelIds;

    /**
     * List of users you wish to update access for. Can only be used if channel_ids is not provided. (Optional)
     */
    @SerializedName("user_ids")
    private List<String> userIds;
}

