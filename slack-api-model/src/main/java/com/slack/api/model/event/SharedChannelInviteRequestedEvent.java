package com.slack.api.model.event;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.connect.ConnectRequestActor;
import com.slack.api.model.connect.ConnectTeam;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/events/shared_channel_invite_requested
 */
@Data
public class SharedChannelInviteRequestedEvent implements Event {

    public static final String TYPE_NAME = "shared_channel_invite_requested";

    private final String type = TYPE_NAME;
    private ConnectRequestActor actor;
    private String channelId;
    private String eventType;
    private String channelName;
    private String channelType;
    private List<TargetUser> targetUsers;
    private List<ConnectTeam> teamsInChannel;
    @SerializedName("is_external_limited")
    private boolean externalLimited;
    private Integer channelDateCreated;
    private Integer channelMessageLatestCountedTimestamp;
    private String eventTs;

    @Data
    public static class TargetUser {
        private String email;
        private String inviteId;
    }
}