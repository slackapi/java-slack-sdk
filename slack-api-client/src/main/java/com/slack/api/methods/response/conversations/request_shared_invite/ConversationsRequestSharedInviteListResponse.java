package com.slack.api.methods.response.conversations.request_shared_invite;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.TeamIcon;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ConversationsRequestSharedInviteListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<InviteRequest> inviteRequests;

    @Data
    public static class InviteRequest {
        private String id;
        private Integer dateCreated;
        private Integer expiresAt;
        private Team invitingTeam;
        private User invitingUser;
        private Channel channel;
        private Boolean isExternalLimited;
        private Integer dateLastUpdated;
        private TargetUser targetUser;

        @Data
        public static class Team {
            private String id;
            private String name;
            private TeamIcon icon;
            private String avatarBaseUrl;
            private Boolean isVerified;
            private String domain;
            private Integer dateCreated;
            private Boolean requiresSponsorship;
        }

        @Data
        public static class User {
            private String id;
            private String name;
            private String teamId;
            private Integer updated;
            private String whoCanShareContactCard;
            private com.slack.api.model.User.Profile profile;
        }

        @Data
        public static class Channel {
            private String id;
            private Boolean isIm;
            private Boolean isPrivate;
            private Integer dateCreated;
            private String name;
            private List<Connection> connections;
            private List<Connection> pendingConnections;
            private List<Connection> previousConnections;
        }

        @Data
        public static class Connection {
            private Boolean isPrivate;
            private Team team;
        }

        @Data
        public static class TargetUser {
            private String recipientEmail;
        }
    }
}
