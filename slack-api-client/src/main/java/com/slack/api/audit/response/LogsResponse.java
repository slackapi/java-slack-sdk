package com.slack.api.audit.response;

import com.google.gson.annotations.SerializedName;
import com.slack.api.audit.AuditApiResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class LogsResponse implements AuditApiResponse {
    private transient String rawBody;

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private ResponseMetadata responseMetadata;

    private List<Entry> entries;

    @Data
    public static class Entry {
        private String id;
        private Integer dateCreate;
        private String action;
        private Actor actor;
        private Entity entity;
        private Context context;
        private Details details;
    }

    @Data
    public static class Actor {
        private String type;
        private User user;
    }

    @Data
    public static class User {
        private String id;
        private String name;
        private String email;
        private String team;
    }

    @Data
    public static class Entity {
        private String type;
        private App app;
        private User user;
        private Usergroup usergroup;
        private Workspace workspace;
        private Enterprise enterprise;
        private File file;
        private Channel channel;
        private Huddle huddle;
        private Role role;
        private Workflow workflow;
        private InformationBarrier barrier;
    }

    @Data
    public static class App {
        private String id;
        private String name;
        @SerializedName("is_distributed")
        private Boolean distributed;
        @SerializedName("is_directory_approved")
        private Boolean directoryApproved;
        @SerializedName("is_workflow_app")
        private Boolean workflowApp;
        private List<String> scopes;
        private List<String> scopesBot;
        private String creator; // user ID
        private String team; // team ID
    }

    @Data
    public static class Usergroup {
        private String id;
        private String name;
    }

    @Data
    public static class Workspace {
        private String id;
        private String name;
        private String domain;
    }

    @Data
    public static class Enterprise {
        private String id;
        private String name;
        private String domain;
    }

    @Data
    public static class File {
        private String id;
        private String name;
        private String filetype;
        private String title;
    }

    @Data
    public static class Channel {
        private String id;
        private String name;
        private String privacy;
        @SerializedName("is_shared")
        private Boolean shared;
        @SerializedName("is_org_shared")
        private Boolean orgShared;
        private List<String> teamsSharedWith;
        private String originalConnectedChannelId;
    }

    @Data
    public static class Workflow {
        private String id;
        private String name;
    }

    @Data
    public static class Context {
        private String sessionId;
        private Location location;
        private String ua;
        private String ipAddress;
        private App app;
    }

    @Data
    public static class Huddle {
        private String id;
        private Integer dateStart;
        private Integer dateEnd;
        private List<String> participants;
    }

    @Data
    public static class Role {
        private String id;
        private String name;
        private String type;
    }

    @Data
    public static class Location {
        private String type;
        private String id;
        private String name;
        private String domain;
    }

    @Data
    public static class InformationBarrier {
        private String id;
        private String primaryUsergroup;
        private List<String> barrieredFromUsergroups;
        private List<String> restrictedSubjects;
    }

    /**
     * The data structure for new_value, previous_value is greatly flexible.
     * This class supports multiple patterns for those.
     */
    @Data
    public static class DetailsChangedValue {
        // e.g., RETAIN_ONLY_MSGS
        private String stringValue;
        // e.g., ["C111", "C222"]
        private List<String> stringValues;
        // e.g., {"type": ["TOPLEVEL_ADMINS_AND_OWNERS_AND_SELECTED"]}
        private Map<String, List<String>> namedStringValues;
    }

    @Data
    public static class Details {
        private String type;
        private String appOwnerId;
        private List<String> scopes; // app_scopes_expanded
        private List<String> botScopes;
        private List<String> newScopes;
        private List<String> previousScopes;
        private Inviter inviter;
        private DetailsChangedValue newValue; // pref.who_can_manage_shared_channels etc
        private DetailsChangedValue previousValue; // pref.who_can_manage_shared_channels etc
        private Kicker kicker;
        private String installerUserId;
        private String approverId;
        private String approvalType;
        private Boolean appPreviouslyApproved;
        private List<String> oldScopes;
        private String name;
        private String botId;
        private List<String> channels;
        private List<Permission> permissions;
        private String sharedTo; // channel_workspaces_updated
        private String reason;
        @SerializedName("is_internal_integration")
        private Boolean internalIntegration;
        private String clearedResolution; // app_removed_from_whitelist
        @SerializedName("is_workflow")
        private Boolean workflow; // user_channel_join
        private Boolean mobileOnly; // user_session_reset_by_admin
        private Boolean webOnly; // user_session_reset_by_admin
        private Boolean nonSsoOnly; // user_session_reset_by_admin
        private Integer expiresOn; // guest_expiration_set
        private String newVersionId; // workflow_published
        private String trigger; // workflow_published
        private Boolean granularBotToken; // app_scopes_expanded
        private String originTeam; // external_shared_channel_invite_approved
        private String targetTeam; // external_shared_channel_invite_approved
        private String resolution; // app_approved
        private Boolean appPreviouslyResolved; // app_approved
        private String adminAppId; // app_approved
        private String exportType; // manual_export_completed
        private String exportStartTs; // manual_export_completed
        private String exportEndTs; // manual_export_completed
        private String barrierId; // information barrier
        private String primaryUsergroupId; // information barrier
        private List<String> barrieredFromUsergroupIds; // information barrier
        private List<String> restrictedSubjects; // information barrier
        private Integer duration; // user_session_settings_changed
        private Boolean desktopAppBrowserQuit; // user_session_settings_changed
        private String inviteId; // connect_dm_invite_generated
        private String externalOrganizationId; // connect_dm_invite_accepted
        private String externalOrganizationName; // connect_dm_invite_accepted
        private String externalUserId; // connect_dm_invite_accepted
        private String externalUserEmail; // connect_dm_invite_accepted
        private String channelId; // connect_dm_invite_accepted
        private String addedTeamId; // approved_orgs_added
        @SerializedName("is_token_rotation_enabled_app")
        private Boolean tokenRotationEnabledApp; // app_scopes_expanded
        private MessageRetentionPolicy oldRetentionPolicy; // channel_retention_changed
        private MessageRetentionPolicy newRetentionPolicy; // channel_retention_changed
        private ConversationPref whoCanPost; // channel_posting_permissions_updated
        private ConversationPref canThread; // channel_posting_permissions_updated
        @SerializedName("is_external_limited")
        private Boolean externalLimited; // external_shared_channel_invite_accepted
        private Long exportingTeamId; // team.unsupportedVersions.start.success
        private Integer sessionSearchStart; // team.unsupportedVersions.start.success
        private Integer deprecationSearchEnd; // team.unsupportedVersions.start.success
        private Boolean isError; // team.unsupportedVersions.job.end
        private String appId; // cli_app_deploy
        private FeatureEnablement enableAtHere; // channel_posting_permissions_updated
        private FeatureEnablement enableAtChannel; // channel_posting_permissions_updated
        private FeatureEnablement canHuddle; // channel_posting_permissions_updated
        private String urlPrivate; // file_shared
        private SharedWith sharedWith; // file_shared
        private String initiatedBy; // "admin.conversations.bulkDelete" for public_channel_deleted etc.
        private String sourceTeam; // channel_moved (by an admin.conversations.bulkMove API call)
        private String destinationTeam; // channel_moved (by an admin.conversations.bulkMove API call)

        // Note that the actual data for this property can be either an array of string
        // or a single string representing an encoded JSON array.
        // GsonAuditLogsDetailsUSerIDsFactory deals with the patterns under the hood
        private UserIDs succeededUsers; // user IDs for bulk_session_reset_by_admin

        // Note that the actual data for this property can be either an array of string
        // or a single string representing an encoded JSON array.
        // GsonAuditLogsDetailsUSerIDsFactory deals with the patterns under the hood
        private UserIDs failedUsers; // user IDs for bulk_session_reset_by_admin

        private String enterprise; // usergroup_updated
        private String team; // usergroup_updated
        private String subteam; // usergroup_updated
        private String action; // usergroup_updated
        private Integer idpGroupMemberCount; // usergroup_updated
        private Integer workspaceMemberCount; // usergroup_updated
        private Integer addedUserCount; // usergroup_updated
        private Integer addedUserErrorCount; // usergroup_updated
        private Integer reactivatedUserCount; // usergroup_updated
        private Integer removedUserCount; // usergroup_updated
        private Integer removedUserErrorCount; // usergroup_updated
        private Integer totalRemovalCount; // usergroup_updated
        private String isFlagged; // usergroup_updated
        private String targetUser; // role_assigned
        private String idpConfigId; // user_login
        private String configType; // user_login
        private String idpEntityId; // user_login
        private String idpEntityIdHash; // user_login
        private String label; // user_login
        private Profile previousProfile; // user_profile_updated
        private Profile newProfile; // user_profile_updated

    }

    @Data
    public static class Inviter {
        private String type;

        private User user;

        private String id;
        private String name;
        private String email;
        private String team;
    }

    @Data
    public static class Kicker {
        private String type;

        private User user;

        private String id;
        private String name;
        private String email;
        private String team;
    }

    @Data
    public static class Permission {
        private Resource resource;
        private List<String> scopes;
    }

    @Data
    public static class Resource {
        private String type;
        private Grant grant;
    }

    @Data
    public static class Grant {
        private String type;
        private String resourceId;
        private WildCard wildcard;
    }

    @Data
    public static class WildCard {
        private String type;
    }

    @Data
    public static class MessageRetentionPolicy {
        private String type;
        private Integer durationDays;
    }

    @Data
    public static class ConversationPref {
        private List<String> type;
        private List<String> user;
    }

    @Data
    public static class FeatureEnablement {
        private Boolean enabled;
    }

    @Data
    public static class SharedWith {
        private String channelId;
    }

    @Data
    public static class UserIDs {
        private List<String> users;
    }

    @Data
    public static class Profile {
        private String realName;
        private String firstName;
        private String lastName;
        private String displayName;
    }
}
