package com.slack.api.methods.response.admin.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class AdminConversationsSearchResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Conversation> conversations;
    private String nextCursor;
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
        private Integer internalTeamIdsCount;
        private String internalTeamIdsSampleTeam;
        private List<String> pendingConnectedTeamIds;
        private List<String> connectedTeamIds;
        private Boolean isPendingExtShared;
        private String conversationHostId;
    }
}