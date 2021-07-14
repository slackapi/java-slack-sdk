package com.slack.api.methods;

public class Methods {

    private Methods() {
    }

    public static final String ADMIN_ANALYTICS_GET_FILE = "admin.analytics.getFile";

    // ------------------------------
    // admin.apps
    // ------------------------------

    public static final String ADMIN_APPS_APPROVE = "admin.apps.approve";
    public static final String ADMIN_APPS_CLEAR_RESOLUTION = "admin.apps.clearResolution";
    public static final String ADMIN_APPS_RESTRICT = "admin.apps.restrict";
    public static final String ADMIN_APPS_APPROVED_LIST = "admin.apps.approved.list";
    public static final String ADMIN_APPS_RESTRICTED_LIST = "admin.apps.restricted.list";
    public static final String ADMIN_APPS_UNINSTALL = "admin.apps.uninstall";

    // ------------------------------
    // admin.apps.requests
    // ------------------------------

    public static final String ADMIN_APPS_REQUESTS_LIST = "admin.apps.requests.list";

    // ------------------------------
    // admin.auth.policy
    // ------------------------------

    public static final String ADMIN_AUTH_POLICY_ASSIGN_ENTITIES = "admin.auth.policy.assignEntities";
    public static final String ADMIN_AUTH_POLICY_GET_ENTITIES = "admin.auth.policy.getEntities";
    public static final String ADMIN_AUTH_POLICY_REMOVE_ENTITIES = "admin.auth.policy.removeEntities";

    // ------------------------------
    // admin.barriers
    // ------------------------------

    public static final String ADMIN_BARRIERS_CREATE = "admin.barriers.create";
    public static final String ADMIN_BARRIERS_DELETE = "admin.barriers.delete";
    public static final String ADMIN_BARRIERS_LIST = "admin.barriers.list";
    public static final String ADMIN_BARRIERS_UPDATE = "admin.barriers.update";

    // ------------------------------
    // admin.conversations
    // ------------------------------

    public static final String ADMIN_CONVERSATIONS_SET_TEAMS = "admin.conversations.setTeams";
    public static final String ADMIN_CONVERSATIONS_ARCHIVE = "admin.conversations.archive";
    public static final String ADMIN_CONVERSATIONS_CONVERT_TO_PRIVATE = "admin.conversations.convertToPrivate";
    public static final String ADMIN_CONVERSATIONS_CREATE = "admin.conversations.create";
    public static final String ADMIN_CONVERSATIONS_DELETE = "admin.conversations.delete";
    public static final String ADMIN_CONVERSATIONS_DISCONNECT_SHARED = "admin.conversations.disconnectShared";
    public static final String ADMIN_CONVERSATIONS_GET_CONVERSATION_PREFS = "admin.conversations.getConversationPrefs";
    public static final String ADMIN_CONVERSATIONS_GET_TEAMS = "admin.conversations.getTeams";
    public static final String ADMIN_CONVERSATIONS_INVITE = "admin.conversations.invite";
    public static final String ADMIN_CONVERSATIONS_RENAME = "admin.conversations.rename";
    public static final String ADMIN_CONVERSATIONS_SEARCH = "admin.conversations.search";
    public static final String ADMIN_CONVERSATIONS_SET_CONVERSATION_PREFS = "admin.conversations.setConversationPrefs";
    public static final String ADMIN_CONVERSATIONS_UNARCHIVE = "admin.conversations.unarchive";

    // ------------------------------
    // admin.conversations.ekm
    // ------------------------------

    public static final String ADMIN_CONVERSATIONS_EKM_LIST_ORIGINAL_CONNECTED_CHANNEL_INFO = "admin.conversations.ekm.listOriginalConnectedChannelInfo";

    // ------------------------------
    // admin.conversations.restrictAccess
    // ------------------------------

    public static final String ADMIN_CONVERSATIONS_RESTRICT_ACCESS_ADD_GROUP = "admin.conversations.restrictAccess.addGroup";
    public static final String ADMIN_CONVERSATIONS_RESTRICT_ACCESS_LIST_GROUPS = "admin.conversations.restrictAccess.listGroups";
    public static final String ADMIN_CONVERSATIONS_RESTRICT_ACCESS_REMOVE_GROUP = "admin.conversations.restrictAccess.removeGroup";

    // ------------------------------
    // admin.conversations.whitelist
    // ------------------------------

    @Deprecated // use admin.conversations.restrictAccess.addGroups instead
    public static final String ADMIN_CONVERSATIONS_WHITELIST_ADD = "admin.conversations.whitelist.add";
    @Deprecated // use admin.conversations.restrictAccess.listGroups instead
    public static final String ADMIN_CONVERSATIONS_WHITELIST_LIST_GROUPS_LINKED_TO_CHANNEL = "admin.conversations.whitelist.listGroupsLinkedToChannel";
    @Deprecated // use admin.conversations.restrictAccess.removeGroup instead
    public static final String ADMIN_CONVERSATIONS_WHITELIST_REMOVE = "admin.conversations.whitelist.remove";

    // ------------------------------
    // admin.emoji
    // ------------------------------

    public static final String ADMIN_EMOJI_ADD = "admin.emoji.add";
    public static final String ADMIN_EMOJI_ADD_ALIAS = "admin.emoji.addAlias";
    public static final String ADMIN_EMOJI_LIST = "admin.emoji.list";
    public static final String ADMIN_EMOJI_REMOVE = "admin.emoji.remove";
    public static final String ADMIN_EMOJI_RENAME = "admin.emoji.rename";

    // ------------------------------
    // admin.inviteRequests
    // ------------------------------

    public static final String ADMIN_INVITE_REQUESTS_APPROVE = "admin.inviteRequests.approve";
    public static final String ADMIN_INVITE_REQUESTS_DENY = "admin.inviteRequests.deny";
    public static final String ADMIN_INVITE_REQUESTS_LIST = "admin.inviteRequests.list";
    public static final String ADMIN_INVITE_REQUESTS_APPROVED_LIST = "admin.inviteRequests.approved.list";
    public static final String ADMIN_INVITE_REQUESTS_DENIED_LIST = "admin.inviteRequests.denied.list";

    // ------------------------------
    // admin.teams.admins
    // ------------------------------

    public static final String ADMIN_TEAMS_ADMINS_LIST = "admin.teams.admins.list";

    // ------------------------------
    // admin.teams
    // ------------------------------

    public static final String ADMIN_TEAMS_CREATE = "admin.teams.create";
    public static final String ADMIN_TEAMS_LIST = "admin.teams.list";

    // ------------------------------
    // admin.teams.owners
    // ------------------------------

    public static final String ADMIN_TEAMS_OWNERS_LIST = "admin.teams.owners.list";

    // ------------------------------
    // admin.teams.settings
    // ------------------------------

    public static final String ADMIN_TEAMS_SETTINGS_INFO = "admin.teams.settings.info";
    public static final String ADMIN_TEAMS_SETTINGS_SET_DEFAULT_CHANNELS = "admin.teams.settings.setDefaultChannels";
    public static final String ADMIN_TEAMS_SETTINGS_SET_DESCRIPTION = "admin.teams.settings.setDescription";
    public static final String ADMIN_TEAMS_SETTINGS_SET_DISCOVERABILITY = "admin.teams.settings.setDiscoverability";
    public static final String ADMIN_TEAMS_SETTINGS_SET_ICON = "admin.teams.settings.setIcon";
    public static final String ADMIN_TEAMS_SETTINGS_SET_NAME = "admin.teams.settings.setName";

    // ------------------------------
    // admin.usergroups
    // ------------------------------

    public static final String ADMIN_USERGROUPS_ADD_CHANNELS = "admin.usergroups.addChannels";
    public static final String ADMIN_USERGROUPS_ADD_TEAMS = "admin.usergroups.addTeams";
    public static final String ADMIN_USERGROUPS_LIST_CHANNELS = "admin.usergroups.listChannels";
    public static final String ADMIN_USERGROUPS_REMOVE_CHANNELS = "admin.usergroups.removeChannels";

    // ------------------------------
    // admin.users
    // ------------------------------

    public static final String ADMIN_USERS_ASSIGN = "admin.users.assign";
    public static final String ADMIN_USERS_INVITE = "admin.users.invite";
    public static final String ADMIN_USERS_LIST = "admin.users.list";
    public static final String ADMIN_USERS_REMOVE = "admin.users.remove";
    public static final String ADMIN_USERS_SET_ADMIN = "admin.users.setAdmin";
    public static final String ADMIN_USERS_SET_EXPIRATION = "admin.users.setExpiration";
    public static final String ADMIN_USERS_SET_OWNER = "admin.users.setOwner";
    public static final String ADMIN_USERS_SET_REGULAR = "admin.users.setRegular";

    // ------------------------------
    // admin.users.session
    // ------------------------------

    public static final String ADMIN_USERS_SESSION_INVALIDATE = "admin.users.session.invalidate";
    public static final String ADMIN_USERS_SESSION_LIST = "admin.users.session.list";
    public static final String ADMIN_USERS_SESSION_RESET = "admin.users.session.reset";
    public static final String ADMIN_USERS_SESSION_SET_SETTINGS = "admin.users.session.setSettings";
    public static final String ADMIN_USERS_SESSION_GET_SETTINGS = "admin.users.session.getSettings";
    public static final String ADMIN_USERS_SESSION_CLEAR_SETTINGS = "admin.users.session.clearSettings";

    // ------------------------------
    // api
    // ------------------------------

    public static final String API_TEST = "api.test";

    // ------------------------------
    // apps
    // ------------------------------

    public static final String APPS_UNINSTALL = "apps.uninstall";

    // ------------------------------
    // apps.connections
    // ------------------------------

    public static final String APPS_CONNECTIONS_OPEN = "apps.connections.open";

    // ------------------------------
    // apps.event.authorizations
    // ------------------------------

    public static final String APPS_EVENT_AUTHORIZATIONS_LIST = "apps.event.authorizations.list";

    // ------------------------------
    // apps.permissions
    // ------------------------------

    // Developer preview has ended
    // This feature was exclusive to our workspace apps developer preview.
    // The preview has now ended, but fan-favorite features such as token rotation
    // and the Conversations API will become available to classic Slack apps over the coming months.

    @Deprecated
    public static final String APPS_PERMISSIONS_INFO = "apps.permissions.info";
    @Deprecated
    public static final String APPS_PERMISSIONS_REQUEST = "apps.permissions.request";
    @Deprecated
    public static final String APPS_PERMISSIONS_RESOURCES_LIST = "apps.permissions.resources.list";
    @Deprecated
    public static final String APPS_PERMISSIONS_SCOPES_LIST = "apps.permissions.scopes.list";
    @Deprecated
    public static final String APPS_PERMISSIONS_USERS_LIST = "apps.permissions.users.list";
    @Deprecated
    public static final String APPS_PERMISSIONS_USERS_REQUEST = "apps.permissions.users.request";

    // ------------------------------
    // auth
    // ------------------------------

    public static final String AUTH_REVOKE = "auth.revoke";
    public static final String AUTH_TEST = "auth.test";

    // ------------------------------
    // auth.teams
    // ------------------------------

    public static final String AUTH_TEAMS_LIST = "auth.teams.list";

    // ------------------------------
    // bots
    // ------------------------------

    public static final String BOTS_INFO = "bots.info";

    // ------------------------------
    // calls
    // ------------------------------

    public static final String CALLS_ADD = "calls.add";
    public static final String CALLS_END = "calls.end";
    public static final String CALLS_INFO = "calls.info";
    public static final String CALLS_UPDATE = "calls.update";

    // ------------------------------
    // calls.participants
    // ------------------------------

    public static final String CALLS_PARTICIPANTS_ADD = "calls.participants.add";
    public static final String CALLS_PARTICIPANTS_REMOVE = "calls.participants.remove";

    // ------------------------------
    // channels
    // ------------------------------

    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String CHANNELS_ARCHIVE = "channels.archive";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String CHANNELS_CREATE = "channels.create";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String CHANNELS_HISTORY = "channels.history";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String CHANNELS_INFO = "channels.info";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String CHANNELS_INVITE = "channels.invite";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String CHANNELS_JOIN = "channels.join";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String CHANNELS_KICK = "channels.kick";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String CHANNELS_LEAVE = "channels.leave";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String CHANNELS_LIST = "channels.list";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String CHANNELS_MARK = "channels.mark";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String CHANNELS_RENAME = "channels.rename";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String CHANNELS_REPLIES = "channels.replies";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String CHANNELS_SET_PURPOSE = "channels.setPurpose";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String CHANNELS_SET_TOPIC = "channels.setTopic";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String CHANNELS_UNARCHIVE = "channels.unarchive";

    // ------------------------------
    // chat
    // ------------------------------

    public static final String CHAT_DELETE = "chat.delete";
    public static final String CHAT_DELETE_SCHEDULED_MESSAGE = "chat.deleteScheduledMessage";
    public static final String CHAT_GET_PERMALINK = "chat.getPermalink";
    public static final String CHAT_ME_MESSAGE = "chat.meMessage";
    public static final String CHAT_POST_EPHEMERAL = "chat.postEphemeral";
    public static final String CHAT_POST_MESSAGE = "chat.postMessage";
    public static final String CHAT_SCHEDULE_MESSAGE = "chat.scheduleMessage";
    public static final String CHAT_UNFURL = "chat.unfurl";
    public static final String CHAT_UPDATE = "chat.update";

    // ------------------------------
    // chat.scheduledMessages
    // ------------------------------

    public static final String CHAT_SCHEDULED_MESSAGES_LIST = "chat.scheduledMessages.list";

    // ------------------------------
    // conversations
    // ------------------------------

    public static final String CONVERSATIONS_ARCHIVE = "conversations.archive";
    public static final String CONVERSATIONS_CLOSE = "conversations.close";
    public static final String CONVERSATIONS_CREATE = "conversations.create";
    public static final String CONVERSATIONS_HISTORY = "conversations.history";
    public static final String CONVERSATIONS_INFO = "conversations.info";
    public static final String CONVERSATIONS_INVITE = "conversations.invite";
    public static final String CONVERSATIONS_JOIN = "conversations.join";
    public static final String CONVERSATIONS_KICK = "conversations.kick";
    public static final String CONVERSATIONS_LEAVE = "conversations.leave";
    public static final String CONVERSATIONS_LIST = "conversations.list";
    public static final String CONVERSATIONS_MARK = "conversations.mark";
    public static final String CONVERSATIONS_MEMBERS = "conversations.members";
    public static final String CONVERSATIONS_OPEN = "conversations.open";
    public static final String CONVERSATIONS_RENAME = "conversations.rename";
    public static final String CONVERSATIONS_REPLIES = "conversations.replies";
    public static final String CONVERSATIONS_SET_PURPOSE = "conversations.setPurpose";
    public static final String CONVERSATIONS_SET_TOPIC = "conversations.setTopic";
    public static final String CONVERSATIONS_UNARCHIVE = "conversations.unarchive";

    // ------------------------------
    // dialog
    // ------------------------------

    public static final String DIALOG_OPEN = "dialog.open";

    // ------------------------------
    // dnd
    // ------------------------------

    public static final String DND_END_DND = "dnd.endDnd";
    public static final String DND_END_SNOOZE = "dnd.endSnooze";
    public static final String DND_INFO = "dnd.info";
    public static final String DND_SET_SNOOZE = "dnd.setSnooze";
    public static final String DND_TEAM_INFO = "dnd.teamInfo";

    // ------------------------------
    // emoji
    // ------------------------------

    public static final String EMOJI_LIST = "emoji.list";

    // ------------------------------
    // files.comments
    // ------------------------------

    @Deprecated // https://api.slack.com/changelog/2018-05-file-threads-soon-tread
    public static final String FILES_COMMENTS_ADD = "files.comments.add";
    @Deprecated // https://api.slack.com/changelog/2018-05-file-threads-soon-tread
    public static final String FILES_COMMENTS_DELETE = "files.comments.delete";
    @Deprecated // https://api.slack.com/changelog/2018-05-file-threads-soon-tread
    public static final String FILES_COMMENTS_EDIT = "files.comments.edit";

    // ------------------------------
    // files
    // ------------------------------

    public static final String FILES_DELETE = "files.delete";
    public static final String FILES_INFO = "files.info";
    public static final String FILES_LIST = "files.list";
    public static final String FILES_REVOKE_PUBLIC_URL = "files.revokePublicURL";
    public static final String FILES_SHARED_PUBLIC_URL = "files.sharedPublicURL";
    public static final String FILES_UPLOAD = "files.upload";

    // ------------------------------
    // files.remote
    // https://api.slack.com/messaging/files/remote
    // ------------------------------

    public static final String FILES_REMOTE_ADD = "files.remote.add";
    public static final String FILES_REMOTE_INFO = "files.remote.info";
    public static final String FILES_REMOTE_LIST = "files.remote.list";
    public static final String FILES_REMOTE_REMOVE = "files.remote.remove";
    public static final String FILES_REMOTE_SHARE = "files.remote.share";
    public static final String FILES_REMOTE_UPDATE = "files.remote.update";

    // ------------------------------
    // groups
    // ------------------------------

    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String GROUPS_ARCHIVE = "groups.archive";
    @Deprecated // https://github.com/slackapi/slack-api-specs/issues/12
    public static final String GROUPS_CLOSE = "groups.close";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String GROUPS_CREATE = "groups.create";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String GROUPS_CREATE_CHILD = "groups.createChild";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String GROUPS_HISTORY = "groups.history";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String GROUPS_INFO = "groups.info";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String GROUPS_INVITE = "groups.invite";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String GROUPS_KICK = "groups.kick";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String GROUPS_LEAVE = "groups.leave";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String GROUPS_LIST = "groups.list";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String GROUPS_MARK = "groups.mark";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String GROUPS_OPEN = "groups.open";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String GROUPS_RENAME = "groups.rename";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String GROUPS_SET_PURPOSE = "groups.setPurpose";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String GROUPS_SET_TOPIC = "groups.setTopic";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String GROUPS_UNARCHIVE = "groups.unarchive";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String GROUPS_REPLIES = "groups.replies";

    // ------------------------------
    // im
    // ------------------------------

    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String IM_CLOSE = "im.close";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String IM_HISTORY = "im.history";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String IM_LIST = "im.list";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String IM_MARK = "im.mark";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String IM_OPEN = "im.open";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String IM_REPLIES = "im.replies";

    // ------------------------------
    // migration
    // ------------------------------

    public static final String MIGRATION_EXCHANGE = "migration.exchange";

    // ------------------------------
    // mpim
    // ------------------------------

    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String MPIM_CLOSE = "mpim.close";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String MPIM_HISTORY = "mpim.history";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String MPIM_LIST = "mpim.list";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String MPIM_MARK = "mpim.mark";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String MPIM_OPEN = "mpim.open";
    @Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
    public static final String MPIM_REPLIES = "mpim.replies";

    // ------------------------------
    // oauth
    // ------------------------------

    public static final String OAUTH_ACCESS = "oauth.access";
    public static final String OAUTH_V2_ACCESS = "oauth.v2.access";
    public static final String OAUTH_V2_EXCHANGE = "oauth.v2.exchange";
    public static final String OAUTH_TOKEN = "oauth.token";

    // ------------------------------
    // pins
    // ------------------------------

    public static final String PINS_ADD = "pins.add";
    public static final String PINS_LIST = "pins.list";
    public static final String PINS_REMOVE = "pins.remove";

    // ------------------------------
    // reactions
    // ------------------------------

    public static final String REACTIONS_ADD = "reactions.add";
    public static final String REACTIONS_GET = "reactions.get";
    public static final String REACTIONS_LIST = "reactions.list";
    public static final String REACTIONS_REMOVE = "reactions.remove";

    // ------------------------------
    // reminders
    // ------------------------------

    public static final String REMINDERS_ADD = "reminders.add";
    public static final String REMINDERS_COMPLETE = "reminders.complete";
    public static final String REMINDERS_DELETE = "reminders.delete";
    public static final String REMINDERS_INFO = "reminders.info";
    public static final String REMINDERS_LIST = "reminders.list";

    // ------------------------------
    // rtm
    // ------------------------------

    public static final String RTM_CONNECT = "rtm.connect";
    public static final String RTM_START = "rtm.start";

    // ------------------------------
    // search
    // ------------------------------

    public static final String SEARCH_ALL = "search.all";
    public static final String SEARCH_FILES = "search.files";
    public static final String SEARCH_MESSAGES = "search.messages";

    // ------------------------------
    // stars
    // ------------------------------

    public static final String STARS_ADD = "stars.add";
    public static final String STARS_LIST = "stars.list";
    public static final String STARS_REMOVE = "stars.remove";

    // ------------------------------
    // team
    // ------------------------------

    public static final String TEAM_ACCESS_LOGS = "team.accessLogs";
    public static final String TEAM_BILLABLE_INFO = "team.billableInfo";
    public static final String TEAM_INFO = "team.info";
    public static final String TEAM_INTEGRATION_LOGS = "team.integrationLogs";

    // ------------------------------
    // team.profile
    // ------------------------------

    public static final String TEAM_PROFILE_GET = "team.profile.get";

    // ------------------------------
    // usergroups
    // ------------------------------

    public static final String USERGROUPS_CREATE = "usergroups.create";
    public static final String USERGROUPS_DISABLE = "usergroups.disable";
    public static final String USERGROUPS_ENABLE = "usergroups.enable";
    public static final String USERGROUPS_LIST = "usergroups.list";
    public static final String USERGROUPS_UPDATE = "usergroups.update";

    // ------------------------------
    // usergroups.users
    // ------------------------------

    public static final String USERGROUPS_USERS_LIST = "usergroups.users.list";
    public static final String USERGROUPS_USERS_UPDATE = "usergroups.users.update";

    // ------------------------------
    // users
    // ------------------------------

    public static final String USERS_CONVERSATIONS = "users.conversations";
    public static final String USERS_DELETE_PHOTO = "users.deletePhoto";
    public static final String USERS_GET_PRESENCE = "users.getPresence";
    public static final String USERS_IDENTITY = "users.identity";
    public static final String USERS_INFO = "users.info";
    public static final String USERS_LIST = "users.list";
    public static final String USERS_LOOKUP_BY_EMAIL = "users.lookupByEmail";
    public static final String USERS_SET_ACTIVE = "users.setActive";
    public static final String USERS_SET_PHOTO = "users.setPhoto";
    public static final String USERS_SET_PRESENCE = "users.setPresence";

    // ------------------------------
    // users.profile
    // ------------------------------

    public static final String USERS_PROFILE_GET = "users.profile.get";
    public static final String USERS_PROFILE_SET = "users.profile.set";

    // ------------------------------
    // views
    // https://api.slack.com/block-kit/surfaces/modals
    // ------------------------------

    public static final String VIEWS_OPEN = "views.open";
    public static final String VIEWS_PUSH = "views.push";
    public static final String VIEWS_UPDATE = "views.update";

    // https://api.slack.com/surfaces/tabs
    public static final String VIEWS_PUBLISH = "views.publish";

    // ------------------------------
    // workflows
    // ------------------------------

    public static final String WORKFLOWS_STEP_COMPLETED = "workflows.stepCompleted";
    public static final String WORKFLOWS_STEP_FAILED = "workflows.stepFailed";
    public static final String WORKFLOWS_UPDATE_STEP = "workflows.updateStep";
}
