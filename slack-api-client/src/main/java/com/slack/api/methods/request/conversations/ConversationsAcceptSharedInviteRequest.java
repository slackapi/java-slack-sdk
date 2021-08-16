package com.slack.api.methods.request.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationsAcceptSharedInviteRequest implements SlackApiRequest {

    private String token;

    /**
     * Name of the channel. If the channel does not exist already in your workspace,
     * this name is the one that the channel will take.
     */
    private String channelName;

    /**
     * ID of the channel that you'd like to accept. Must provide either invite_id or channel_id.
     */
    private String channelId;

    /**
     * Whether you'd like to use your workspace's free trial to begin using Slack Connect.
     */
    private Boolean freeTrialAccept;

    /**
     * See the shared_channel_invite_received event payload for more details
     * on how to retrieve the ID of the invitation.
     */
    private String inviteId;

    /**
     * Whether the channel should be private.
     */
    private Boolean isPrivate;

    /**
     * The ID of the workspace to accept the channel in.
     * If an org-level token is used to call this method, the team_id argument is required.
     */
    private String teamId;
}
