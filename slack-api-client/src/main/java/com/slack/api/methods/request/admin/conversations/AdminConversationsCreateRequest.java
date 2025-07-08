package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.conversations.create
 */
@Data
@Builder
public class AdminConversationsCreateRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * When true, creates a private channel instead of a public channel
     */
    private Boolean isPrivate;

    /**
     * Name of the public or private channel to create.
     */
    private String name;

    /**
     * Description of the public or private channel to create.
     */
    private String description;

    /**
     * When true, the channel will be available org-wide.
     * Note: if the channel is not org_wide=true, you must specify a team_id for this channel
     */
    private Boolean orgWide;

    /**
     * The workspace to create the channel in.
     * Note: this argument is required unless you set org_wide=true.
     */
    private String teamId;

}
