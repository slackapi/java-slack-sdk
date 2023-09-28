package com.slack.api.methods.response.admin.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ChannelEmailAddress;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminConversationsSearchResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<Conversation> conversations;
    private String nextCursor;
    private Integer totalCount;
    private ErrorResponseMetadata responseMetadata;

    @Data
    public static class Conversation {
        private String id;
        private String name;
        private String purpose;
        private Integer memberCount;
        private Integer created;
        private String creatorId;
        private Boolean isPrivate;
        private Boolean isArchived;
        private Boolean isGeneral;
        private Long lastActivityTs;
        private Boolean isExtShared;
        private Boolean isGlobalShared;
        private Boolean isOrgDefault;
        private Boolean isOrgMandatory;
        private Boolean isOrgShared;
        private Boolean isFrozen;
        private Integer externalUserCount;
        private Integer channelManagerCount;
        private List<String> internalTeamIds;
        private Integer internalTeamIdsCount;
        private String internalTeamIdsSampleTeam;
        private List<String> pendingConnectedTeamIds;
        private List<String> connectedTeamIds;
        private List<String> connectedLimitedTeamIds;
        private List<ChannelEmailAddress> channelEmailAddresses;
        private String contextTeamId;
        private Boolean isPendingExtShared;
        private Boolean isDisconnectInProgress;
        private String conversationHostId;
        private Canvas canvas;
    }

    @Data
    public static class Canvas {
        private Integer totalCount;
        private List<CanvasOwnershipDetail> ownershipDetails;
    }
    @Data
    public static class CanvasOwnershipDetail {
        private Integer count;
        private String teamId;
    }
}