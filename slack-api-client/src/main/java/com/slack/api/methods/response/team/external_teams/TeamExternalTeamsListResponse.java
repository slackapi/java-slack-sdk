package com.slack.api.methods.response.team.external_teams;

import com.google.gson.annotations.SerializedName;
import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
public class TeamExternalTeamsListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<ExternalOrganization> organizations;
    private Integer totalCount;
    private ResponseMetadata responseMetadata;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExternalOrganization {
        private String teamId;
        private String teamName;
        private String teamDomain;
        private Integer publicChannelCount;
        private Integer privateChannelCount;
        private Integer imChannelCount;
        private Integer mpimChannelCount;
        private List<ConnectedWorkspace> connectedWorkspaces;
        private SlackConnectPrefs slackConnectPrefs;
        private String connectionStatus;
        private Integer lastActiveTimestamp;
        @SerializedName("is_sponsored")
        private boolean sponsored;
        private Canvas canvas;
        private Lists lists;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConnectedWorkspace {
        private String workspaceId;
        private String workspaceName;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SlackConnectPrefs {
        private AllowScFileUploads allowScFileUploads;
        private ApprovedOrgInfo approvedOrgInfo;
        private ProfileVisibility profileVisibility;
        private AllowedWorkspaces allowedWorkspaces;
        private AllowedCanvasSharing allowedCanvasSharing;
        private AllowedListSharing allowedListSharing;
        private AwayTeamScInvitePermissions awayTeamScInvitePermissions;
        @SerializedName("away_team_sc_invite_require_2fa")
        private AwayTeamScInviteRequire2fa awayTeamScInviteRequire2fa;
        private AcceptScInvites acceptScInvites;
        private ScChannelLimitedAccess scChannelLimitedAccess;
        private ScMpdmToPrivate scMpdmToPrivate;
        private ExternalAwarenessContextBar externalAwarenessContextBar;
        private RequireScChannelForScDm requireScChannelForScDm;
        private SharedChannelInviteRequested sharedChannelInviteRequested;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class AllowScFileUploads {
            private boolean value;
            private String actor;
            private Integer dateUpdate;
            private String source;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ApprovedOrgInfo {
            private String actor;
            private Integer dateUpdate;
            private String approvalType;
            private String source;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ProfileVisibility {
            private String type;
            private String actor;
            private Integer dateUpdate;
            private String source;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class AllowedWorkspaces {
            private String type;
            private List<String> teamIds;
            private String actor;
            private Integer dateUpdate;
            private String source;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class AllowedCanvasSharing {
            private boolean value;
            private String actor;
            private Integer dateUpdate;
            private String source;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class AllowedListSharing {
            private boolean value;
            private String actor;
            private Integer dateUpdate;
            private String source;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class AwayTeamScInvitePermissions {
            private String type;
            private List<String> teamIds;
            private String actor;
            private Integer dateUpdate;
            private String source;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class AwayTeamScInviteRequire2fa {
            private boolean type;
            private String actor;
            private Integer dateUpdate;
            private String source;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class AcceptScInvites {
            private String type;
            private List<String> acceptInWorkspaceIds;
            private List<String> invalidWorkspaceIds;
            private boolean useAllowedWorkspaces;
            private boolean acceptPrivate;
            private String actor;
            private Integer dateUpdate;
            private String source;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ScChannelLimitedAccess {
            private String type;
            private String actor;
            private Integer dateUpdate;
            private String source;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ScMpdmToPrivate {
            private String type;
            private String acceptInWorkspaceId;
            private List<String> invalidWorkspaceIds;
            private String actor;
            private Integer dateUpdate;
            private String source;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ExternalAwarenessContextBar {
            private String type;
            private String actor;
            private Integer dateUpdate;
            private String source;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class RequireScChannelForScDm {
            private boolean value;
            private String actor;
            private Integer dateUpdate;
            private String source;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class SharedChannelInviteRequested {
            private boolean enabled;
            private UsergroupRule usergroupInclude;
            private UsergroupRule usergroupExclude;
            private ApprovalDestination approvalDestination;
            private String actor;
            private Integer dateUpdate;
            private String source;

            @Data
            @Builder
            @AllArgsConstructor
            @NoArgsConstructor
            public static class UsergroupRule {
                private String id;
                private String teamId;
            }

            @Data
            @Builder
            @AllArgsConstructor
            @NoArgsConstructor
            public static class ApprovalDestination {
                private boolean allWhoCanManageSharedChannels;
                private String channelId;
            }
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OwnershipDetail {
        private String teamId;
        private Integer count;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Canvas {
        private Integer totalCount;
        private List<OwnershipDetail> ownershipDetails;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Lists {
        private Integer totalCount;
        private List<OwnershipDetail> ownershipDetails;
    }
}