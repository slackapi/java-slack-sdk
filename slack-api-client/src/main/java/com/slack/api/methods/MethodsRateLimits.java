package com.slack.api.methods;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.slack.api.methods.Methods.*;
import static com.slack.api.methods.MethodsRateLimitTier.*;

/**
 * The comprehensive list of Slack Web API rate limits.
 *
 * @see <a href="https://api.slack.com/docs/rate-limits">api.slack.com document</a>
 */
@Slf4j
public class MethodsRateLimits {

    private static final ConcurrentMap<String, MethodsRateLimitTier> methodNameToTier = new ConcurrentHashMap<>();

    public static MethodsRateLimitTier lookupRateLimitTier(String methodName) {
        if (methodName == null) {
            return null;
        } else {
            return methodNameToTier.get(methodName);
        }
    }

    public TreeMap<String, String> toMap() {
        TreeMap<String, String> result = new TreeMap<>(); // using TreeMap to sort the keys
        for (Map.Entry<String, MethodsRateLimitTier> e : methodNameToTier.entrySet()) {
            result.put(e.getKey(), e.getValue().name());
        }
        return result;
    }

    public static void setRateLimitTier(String methodName, MethodsRateLimitTier tier) {
        MethodsRateLimitTier currentTier = lookupRateLimitTier(methodName);
        if (currentTier == null) {
            methodNameToTier.putIfAbsent(methodName, tier);
        } else {
            String skipMessage = "Skipped to set tier for " + methodName + " as it's already set as " + currentTier;
            throw new IllegalArgumentException(skipMessage);
        }
    }

    static {
        // --------------------------
        // Admin APIs
        // --------------------------

        // Tier1
        final List<String> adminApiMethods_Tier1 = Arrays.asList(
                ADMIN_CONVERSATIONS_RESTRICT_ACCESS_REMOVE_GROUP,
                ADMIN_CONVERSATIONS_WHITELIST_REMOVE,
                ADMIN_TEAMS_CREATE,
                ADMIN_USERGROUPS_ADD_CHANNELS,
                ADMIN_USERS_UNSUPPORTED_VERSIONS_EXPORT,
                ADMIN_APPS_UNINSTALL
        );
        for (String methods : adminApiMethods_Tier1) {
            setRateLimitTier(methods, Tier1);
        }

        // Tier2
        final List<String> adminApiMethods_Tier2 = Arrays.asList(
                ADMIN_ANALYTICS_GET_FILE,

                ADMIN_APPS_APPROVE,
                ADMIN_APPS_CLEAR_RESOLUTION,
                ADMIN_APPS_RESTRICT,
                ADMIN_APPS_APPROVED_LIST,
                ADMIN_APPS_RESTRICTED_LIST,
                ADMIN_APPS_REQUESTS_CANCEL,
                ADMIN_APPS_REQUESTS_LIST,

                ADMIN_APPS_CONFIG_LOOKUP,

                ADMIN_BARRIERS_CREATE,
                ADMIN_BARRIERS_DELETE,
                ADMIN_BARRIERS_LIST,
                ADMIN_BARRIERS_UPDATE,

                ADMIN_CONVERSATIONS_RESTRICT_ACCESS_ADD_GROUP,
                ADMIN_CONVERSATIONS_RESTRICT_ACCESS_LIST_GROUPS,
                ADMIN_CONVERSATIONS_WHITELIST_ADD,
                ADMIN_CONVERSATIONS_WHITELIST_LIST_GROUPS_LINKED_TO_CHANNEL,
                ADMIN_CONVERSATIONS_ARCHIVE,
                ADMIN_CONVERSATIONS_CONVERT_TO_PRIVATE,
                ADMIN_CONVERSATIONS_CREATE,
                ADMIN_CONVERSATIONS_DELETE,
                ADMIN_CONVERSATIONS_DISCONNECT_SHARED,
                ADMIN_CONVERSATIONS_GET_CONVERSATION_PREFS,
                ADMIN_CONVERSATIONS_GET_TEAMS,
                ADMIN_CONVERSATIONS_INVITE,
                ADMIN_CONVERSATIONS_RENAME,
                ADMIN_CONVERSATIONS_SEARCH,
                ADMIN_CONVERSATIONS_SET_CONVERSATION_PREFS,
                ADMIN_CONVERSATIONS_SET_TEAMS,
                ADMIN_CONVERSATIONS_UNARCHIVE,
                ADMIN_CONVERSATIONS_EKM_LIST_ORIGINAL_CONNECTED_CHANNEL_INFO,
                ADMIN_CONVERSATIONS_GET_CUSTOM_RETENTION,
                ADMIN_CONVERSATIONS_REMOVE_CUSTOM_RETENTION,
                ADMIN_CONVERSATIONS_SET_CUSTOM_RETENTION,
                ADMIN_CONVERSATIONS_BULK_ARCHIVE,
                ADMIN_CONVERSATIONS_BULK_DELETE,
                ADMIN_CONVERSATIONS_BULK_MOVE,
                ADMIN_CONVERSATIONS_CONVERT_TO_PUBLIC,
                ADMIN_CONVERSATIONS_LOOKUP,

                ADMIN_EMOJI_ADD,
                ADMIN_EMOJI_ADD_ALIAS,
                ADMIN_EMOJI_LIST,
                ADMIN_EMOJI_REMOVE,
                ADMIN_EMOJI_RENAME,

                ADMIN_INVITE_REQUESTS_APPROVE,
                ADMIN_INVITE_REQUESTS_DENY,
                ADMIN_INVITE_REQUESTS_LIST,
                ADMIN_INVITE_REQUESTS_APPROVED_LIST,
                ADMIN_INVITE_REQUESTS_DENIED_LIST,

                ADMIN_FUNCTIONS_LIST,

                ADMIN_ROLES_ADD_ASSIGNMENTS,
                ADMIN_ROLES_REMOVE_ASSIGNMENTS,

                ADMIN_TEAMS_SETTINGS_SET_DEFAULT_CHANNELS,
                ADMIN_TEAMS_SETTINGS_SET_DESCRIPTION,
                ADMIN_TEAMS_SETTINGS_SET_DISCOVERABILITY,
                ADMIN_TEAMS_SETTINGS_SET_ICON,

                ADMIN_USERS_ASSIGN,
                ADMIN_USERS_INVITE,
                ADMIN_USERS_REMOVE,
                ADMIN_USERS_SET_ADMIN,
                ADMIN_USERS_SET_EXPIRATION,
                ADMIN_USERS_SET_OWNER,
                ADMIN_USERS_SET_REGULAR,

                ADMIN_USERS_SESSION_INVALIDATE,
                ADMIN_USERS_SESSION_LIST,
                ADMIN_USERS_SESSION_RESET,
                ADMIN_USERS_SESSION_RESET_BULK,

                ADMIN_USERGROUPS_ADD_TEAMS,
                ADMIN_USERGROUPS_LIST_CHANNELS,
                ADMIN_USERGROUPS_REMOVE_CHANNELS,

                ADMIN_WORKFLOWS_COLLABORATORS_ADD,
                ADMIN_WORKFLOWS_COLLABORATORS_REMOVE,
                ADMIN_WORKFLOWS_PERMISSIONS_LOOKUP,
                ADMIN_WORKFLOWS_SEARCH,
                ADMIN_WORKFLOWS_UNPUBLISH
        );
        for (String methods : adminApiMethods_Tier2) {
            setRateLimitTier(methods, Tier2);
        }

        // Tier3
        final List<String> adminApiMethods_Tier3 = Arrays.asList(
                ADMIN_ROLES_LIST_ASSIGNMENTS,

                ADMIN_TEAMS_ADMINS_LIST,
                ADMIN_TEAMS_LIST,
                ADMIN_TEAMS_OWNERS_LIST,
                ADMIN_TEAMS_SETTINGS_INFO,
                ADMIN_TEAMS_SETTINGS_SET_NAME,

                ADMIN_USERS_LIST,

                ADMIN_APPS_ACTIVITIES_LIST,
                ADMIN_APPS_CONFIG_SET,
                ADMIN_FUNCTIONS_PERMISSIONS_LOOKUP,
                ADMIN_FUNCTIONS_PERMISSIONS_SET
        );
        for (String methods : adminApiMethods_Tier3) {
            setRateLimitTier(methods, Tier3);
        }

        // Tier4
        final List<String> adminApiMethods_Tier4 = Arrays.asList(
                ADMIN_USERS_SESSION_GET_SETTINGS,
                ADMIN_USERS_SESSION_SET_SETTINGS,
                ADMIN_USERS_SESSION_CLEAR_SETTINGS,
                ADMIN_AUTH_POLICY_ASSIGN_ENTITIES,
                ADMIN_AUTH_POLICY_GET_ENTITIES,
                ADMIN_AUTH_POLICY_REMOVE_ENTITIES
        );
        for (String methods : adminApiMethods_Tier4) {
            setRateLimitTier(methods, Tier4);
        }

        // --------------------------
        // Workspace App APIs
        // --------------------------
        final List<String> workspaceAppApis_Tier2 = Arrays.asList(
                APPS_PERMISSIONS_INFO,
                APPS_PERMISSIONS_REQUEST,
                APPS_PERMISSIONS_RESOURCES_LIST,
                APPS_PERMISSIONS_SCOPES_LIST,
                APPS_PERMISSIONS_USERS_LIST,
                APPS_PERMISSIONS_USERS_REQUEST
        );
        for (String methods : workspaceAppApis_Tier2) {
            setRateLimitTier(methods, Tier2);
        }

        // --------------------------
        // Public APIs
        // --------------------------


        setRateLimitTier(APPS_MANIFEST_CREATE, Tier1);
        setRateLimitTier(APPS_MANIFEST_DELETE, Tier1);
        setRateLimitTier(APPS_MANIFEST_EXPORT, Tier3);
        setRateLimitTier(APPS_MANIFEST_UPDATE, Tier1);
        setRateLimitTier(APPS_MANIFEST_VALIDATE, Tier3);
        setRateLimitTier(TOOLING_TOKENS_ROTATE, Tier1); // TODO: change this when the "special" tier is clearly explained in the document

        setRateLimitTier(API_TEST, Tier4);
        setRateLimitTier(APPS_CONNECTIONS_OPEN, Tier1);
        setRateLimitTier(APPS_UNINSTALL, Tier1);
        setRateLimitTier(APPS_EVENT_AUTHORIZATIONS_LIST, Tier4);

        setRateLimitTier(AUTH_REVOKE, Tier3);
        setRateLimitTier(AUTH_TEST, SpecialTier_auth_test);
        setRateLimitTier(AUTH_TEAMS_LIST, Tier2);

        setRateLimitTier(BOOKMARKS_ADD, Tier2);
        setRateLimitTier(BOOKMARKS_EDIT, Tier2);
        setRateLimitTier(BOOKMARKS_LIST, Tier3);
        setRateLimitTier(BOOKMARKS_REMOVE, Tier2);

        setRateLimitTier(BOTS_INFO, Tier3);

        setRateLimitTier(CALLS_ADD, Tier2);
        setRateLimitTier(CALLS_END, Tier2);
        setRateLimitTier(CALLS_INFO, Tier2);
        setRateLimitTier(CALLS_UPDATE, Tier2);
        setRateLimitTier(CALLS_PARTICIPANTS_ADD, Tier2);
        setRateLimitTier(CALLS_PARTICIPANTS_REMOVE, Tier2);

        setRateLimitTier(CANVASES_ACCESS_DELETE, Tier3);
        setRateLimitTier(CANVASES_ACCESS_SET, Tier3);
        setRateLimitTier(CANVASES_CREATE, Tier2);
        setRateLimitTier(CANVASES_DELETE, Tier3);
        setRateLimitTier(CANVASES_EDIT, Tier3);
        setRateLimitTier(CANVASES_SECTIONS_LOOKUP, Tier3);
        setRateLimitTier(CONVERSATIONS_CANVASES_CREATE, Tier2);

        setRateLimitTier(CHAT_DELETE, Tier3);
        setRateLimitTier(CHAT_DELETE_SCHEDULED_MESSAGE, Tier3);
        setRateLimitTier(CHAT_GET_PERMALINK, SpecialTier_chat_getPermalink);
        setRateLimitTier(CHAT_ME_MESSAGE, Tier3);
        setRateLimitTier(CHAT_POST_EPHEMERAL, Tier4);
        setRateLimitTier(CHAT_POST_MESSAGE, SpecialTier_chat_postMessage);
        setRateLimitTier(CHAT_SCHEDULE_MESSAGE, Tier3);
        setRateLimitTier(CHAT_UNFURL, Tier3);
        setRateLimitTier(CHAT_UPDATE, Tier3);
        setRateLimitTier(CHAT_SCHEDULED_MESSAGES_LIST, Tier3);

        setRateLimitTier(CONVERSATIONS_ARCHIVE, Tier2);
        setRateLimitTier(CHANNELS_ARCHIVE, Tier2);
        setRateLimitTier(GROUPS_ARCHIVE, Tier2);

        setRateLimitTier(CONVERSATIONS_CLOSE, Tier2);
        setRateLimitTier(IM_CLOSE, Tier2);
        setRateLimitTier(MPIM_CLOSE, Tier2);

        setRateLimitTier(CONVERSATIONS_CREATE, Tier2);
        setRateLimitTier(CHANNELS_CREATE, Tier2);
        setRateLimitTier(GROUPS_CREATE, Tier2);

        setRateLimitTier(CONVERSATIONS_HISTORY, Tier3);
        setRateLimitTier(CHANNELS_HISTORY, Tier2);
        setRateLimitTier(GROUPS_HISTORY, Tier2);
        setRateLimitTier(IM_HISTORY, Tier4);
        setRateLimitTier(MPIM_HISTORY, Tier3);

        setRateLimitTier(CONVERSATIONS_INFO, Tier3);
        setRateLimitTier(CHANNELS_INFO, Tier3);
        setRateLimitTier(GROUPS_INFO, Tier3);

        setRateLimitTier(CONVERSATIONS_INVITE, Tier3);
        setRateLimitTier(CHANNELS_INVITE, Tier3);
        setRateLimitTier(GROUPS_INVITE, Tier3);

        setRateLimitTier(CONVERSATIONS_JOIN, Tier3);
        setRateLimitTier(CHANNELS_JOIN, Tier3);

        setRateLimitTier(CONVERSATIONS_KICK, Tier3);
        setRateLimitTier(CHANNELS_KICK, Tier3);
        setRateLimitTier(GROUPS_KICK, Tier3);

        setRateLimitTier(CONVERSATIONS_LEAVE, Tier3);
        setRateLimitTier(CHANNELS_LEAVE, Tier3);
        setRateLimitTier(GROUPS_LEAVE, Tier3);

        setRateLimitTier(CONVERSATIONS_LIST, Tier2);
        setRateLimitTier(CHANNELS_LIST, Tier3);
        setRateLimitTier(GROUPS_LIST, Tier3);
        setRateLimitTier(IM_LIST, Tier2);
        setRateLimitTier(MPIM_LIST, Tier2);

        setRateLimitTier(CONVERSATIONS_MEMBERS, Tier4);

        setRateLimitTier(CONVERSATIONS_OPEN, Tier3);
        setRateLimitTier(GROUPS_OPEN, Tier3);
        setRateLimitTier(IM_OPEN, Tier4);
        setRateLimitTier(MPIM_OPEN, Tier3);

        setRateLimitTier(CONVERSATIONS_RENAME, Tier2);
        setRateLimitTier(CHANNELS_RENAME, Tier2);
        setRateLimitTier(GROUPS_RENAME, Tier2);

        setRateLimitTier(CONVERSATIONS_REPLIES, Tier3);
        setRateLimitTier(CHANNELS_REPLIES, Tier3);
        setRateLimitTier(GROUPS_REPLIES, Tier2);
        setRateLimitTier(IM_REPLIES, Tier2);
        setRateLimitTier(MPIM_REPLIES, Tier2);

        setRateLimitTier(CONVERSATIONS_SET_PURPOSE, Tier2);
        setRateLimitTier(CHANNELS_SET_PURPOSE, Tier2);
        setRateLimitTier(GROUPS_SET_PURPOSE, Tier2);

        setRateLimitTier(CONVERSATIONS_SET_TOPIC, Tier2);
        setRateLimitTier(CHANNELS_SET_TOPIC, Tier2);
        setRateLimitTier(GROUPS_SET_TOPIC, Tier2);

        setRateLimitTier(CONVERSATIONS_UNARCHIVE, Tier2);
        setRateLimitTier(CHANNELS_UNARCHIVE, Tier2);
        setRateLimitTier(GROUPS_UNARCHIVE, Tier2);

        setRateLimitTier(CONVERSATIONS_MARK, Tier3);
        setRateLimitTier(CHANNELS_MARK, Tier3);
        setRateLimitTier(GROUPS_MARK, Tier3);
        setRateLimitTier(IM_MARK, Tier3);
        setRateLimitTier(MPIM_MARK, Tier3);
        setRateLimitTier(GROUPS_CREATE_CHILD, Tier2);

        setRateLimitTier(CONVERSATIONS_INVITE_SHARED, Tier2);
        setRateLimitTier(CONVERSATIONS_ACCEPT_SHARED_INVITE, Tier1);
        setRateLimitTier(CONVERSATIONS_APPROVE_SHARED_INVITE, Tier1);
        setRateLimitTier(CONVERSATIONS_DECLINE_SHARED_INVITE, Tier1);
        setRateLimitTier(CONVERSATIONS_LIST_CONNECT_INVITES, Tier1);
        setRateLimitTier(CONVERSATIONS_EXTERNAL_INVITE_PERMISSIONS_SET, Tier2);

        setRateLimitTier(CONVERSATIONS_REQUEST_SHARED_INVITE_APPROVE, Tier2);
        setRateLimitTier(CONVERSATIONS_REQUEST_SHARED_INVITE_DENY, Tier2);

        setRateLimitTier(DIALOG_OPEN, Tier4);

        setRateLimitTier(DND_END_DND, Tier2);
        setRateLimitTier(DND_END_SNOOZE, Tier2);
        setRateLimitTier(DND_INFO, Tier3);
        setRateLimitTier(DND_SET_SNOOZE, Tier2);
        setRateLimitTier(DND_TEAM_INFO, Tier2);

        setRateLimitTier(EMOJI_LIST, Tier2);

        setRateLimitTier(FILES_COMMENTS_DELETE, Tier2);

        setRateLimitTier(FILES_DELETE, Tier3);
        setRateLimitTier(FILES_INFO, Tier4);
        setRateLimitTier(FILES_LIST, Tier3);
        setRateLimitTier(FILES_REVOKE_PUBLIC_URL, Tier3);
        setRateLimitTier(FILES_SHARED_PUBLIC_URL, Tier3);
        setRateLimitTier(FILES_UPLOAD, Tier2);
        setRateLimitTier(FILES_GET_UPLOAD_URL_EXTERNAL, Tier4);
        setRateLimitTier(FILES_COMPLETE_UPLOAD_EXTERNAL, Tier4);

        setRateLimitTier(FILES_REMOTE_ADD, Tier2);
        setRateLimitTier(FILES_REMOTE_INFO, Tier2);
        setRateLimitTier(FILES_REMOTE_LIST, Tier2);
        setRateLimitTier(FILES_REMOTE_REMOVE, Tier2);
        setRateLimitTier(FILES_REMOTE_SHARE, Tier2);
        setRateLimitTier(FILES_REMOTE_UPDATE, Tier2);

        setRateLimitTier(FUNCTIONS_COMPLETE_SUCCESS, Tier3);
        setRateLimitTier(FUNCTIONS_COMPLETE_ERROR, Tier3);

        setRateLimitTier(MIGRATION_EXCHANGE, Tier2);

        setRateLimitTier(OAUTH_ACCESS, Tier4);
        setRateLimitTier(OAUTH_TOKEN, Tier4);
        setRateLimitTier(OAUTH_V2_ACCESS, Tier4);
        setRateLimitTier(OAUTH_V2_EXCHANGE, Tier3);

        setRateLimitTier(OPENID_CONNECT_TOKEN, Tier4);
        setRateLimitTier(OPENID_CONNECT_USER_INFO, Tier3);

        setRateLimitTier(PINS_ADD, Tier2);
        setRateLimitTier(PINS_LIST, Tier2);
        setRateLimitTier(PINS_REMOVE, Tier2);

        setRateLimitTier(REACTIONS_ADD, Tier3);
        setRateLimitTier(REACTIONS_GET, Tier3);
        setRateLimitTier(REACTIONS_LIST, Tier2);
        setRateLimitTier(REACTIONS_REMOVE, Tier2);

        setRateLimitTier(REMINDERS_ADD, Tier2);
        setRateLimitTier(REMINDERS_COMPLETE, Tier2);
        setRateLimitTier(REMINDERS_DELETE, Tier2);
        setRateLimitTier(REMINDERS_INFO, Tier2);
        setRateLimitTier(REMINDERS_LIST, Tier2);

        setRateLimitTier(RTM_CONNECT, Tier1);
        setRateLimitTier(RTM_START, Tier1);

        setRateLimitTier(SEARCH_ALL, Tier2);
        setRateLimitTier(SEARCH_FILES, Tier2);
        setRateLimitTier(SEARCH_MESSAGES, Tier2);

        setRateLimitTier(STARS_ADD, Tier2);
        setRateLimitTier(STARS_LIST, Tier3);
        setRateLimitTier(STARS_REMOVE, Tier2);

        setRateLimitTier(TEAM_ACCESS_LOGS, Tier2);
        setRateLimitTier(TEAM_BILLABLE_INFO, Tier2);
        setRateLimitTier(TEAM_INFO, Tier3);
        setRateLimitTier(TEAM_INTEGRATION_LOGS, Tier2);
        setRateLimitTier(TEAM_PROFILE_GET, Tier3);
        setRateLimitTier(TEAM_BILLING_INFO, Tier3);
        setRateLimitTier(TEAM_PREFERENCES_LIST, Tier3);
        setRateLimitTier(TEAM_EXTERNAL_TEAMS_LIST, Tier2);
        setRateLimitTier(TEAM_EXTERNAL_TEAMS_DISCONNECT, Tier1);

        setRateLimitTier(USERGROUPS_CREATE, Tier2);
        setRateLimitTier(USERGROUPS_DISABLE, Tier2);
        setRateLimitTier(USERGROUPS_ENABLE, Tier2);
        setRateLimitTier(USERGROUPS_LIST, Tier2);
        setRateLimitTier(USERGROUPS_UPDATE, Tier2);
        setRateLimitTier(USERGROUPS_USERS_LIST, Tier2);
        setRateLimitTier(USERGROUPS_USERS_UPDATE, Tier2);

        setRateLimitTier(USERS_CONVERSATIONS, Tier2);
        setRateLimitTier(USERS_DELETE_PHOTO, Tier2);
        setRateLimitTier(USERS_GET_PRESENCE, Tier3);
        setRateLimitTier(USERS_IDENTITY, Tier3);
        setRateLimitTier(USERS_INFO, Tier4);
        setRateLimitTier(USERS_LIST, Tier2);
        setRateLimitTier(USERS_LOOKUP_BY_EMAIL, Tier3);
        setRateLimitTier(USERS_SET_ACTIVE, Tier2);
        setRateLimitTier(USERS_SET_PHOTO, Tier2);
        setRateLimitTier(USERS_SET_PRESENCE, Tier2);
        setRateLimitTier(USERS_PROFILE_GET, Tier4);
        setRateLimitTier(USERS_PROFILE_SET, Tier3);
        setRateLimitTier(USERS_DISCOVERABLE_CONTACTS_LOOKUP, Tier2);

        setRateLimitTier(VIEWS_OPEN, Tier4);
        setRateLimitTier(VIEWS_PUBLISH, Tier4);
        setRateLimitTier(VIEWS_PUSH, Tier4);
        setRateLimitTier(VIEWS_UPDATE, Tier4);

        setRateLimitTier(WORKFLOWS_STEP_COMPLETED, Tier2);
        setRateLimitTier(WORKFLOWS_STEP_FAILED, Tier2);
        setRateLimitTier(WORKFLOWS_UPDATE_STEP, Tier2);
    }

}
