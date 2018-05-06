package com.github.seratch.jslack.api.methods;

public class Methods {

    private Methods() {
    }

    // ------------------------------
    // api
    // ------------------------------

    public static final String API_TEST = "api.test";

    // ------------------------------
    // apps.permissions
    // ------------------------------

    public static final String APPS_PERMISSIONS_INFO = "apps.permissions.info";
    public static final String APPS_PERMISSIONS_REQUEST = "apps.permissions.request";

    // ------------------------------
    // auth
    // ------------------------------

    public static final String AUTH_REVOKE = "auth.revoke";
    public static final String AUTH_TEST = "auth.test";

    // ------------------------------
    // bots
    // ------------------------------

    public static final String BOTS_INFO = "bots.info";

    // ------------------------------
    // channels
    // ------------------------------

    public static final String CHANNELS_ARCHIVE = "channels.archive";
    public static final String CHANNELS_CREATE = "channels.create";
    public static final String CHANNELS_HISTORY = "channels.history";
    public static final String CHANNELS_INFO = "channels.info";
    public static final String CHANNELS_INVITE = "channels.invite";
    public static final String CHANNELS_JOIN = "channels.join";
    public static final String CHANNELS_KICK = "channels.kick";
    public static final String CHANNELS_LEAVE = "channels.leave";
    public static final String CHANNELS_LIST = "channels.list";
    public static final String CHANNELS_MARK = "channels.mark";
    public static final String CHANNELS_RENAME = "channels.rename";
    public static final String CHANNELS_REPLIES = "channels.replies";
    public static final String CHANNELS_SET_PURPOSE = "channels.setPurpose";
    public static final String CHANNELS_SET_TOPIC = "channels.setTopic";
    public static final String CHANNELS_UNARCHIVE = "channels.unarchive";

    // ------------------------------
    // chat
    // ------------------------------

    public static final String CHAT_DELETE = "chat.delete";
    public static final String CHAT_GET_PERMALINK = "chat.getPermalink";
    public static final String CHAT_ME_MESSAGE = "chat.meMessage";
    public static final String CHAT_POST_EPHEMERAL = "chat.postEphemeral";
    public static final String CHAT_POST_MESSAGE = "chat.postMessage";
    public static final String CHAT_UNFURL = "chat.unfurl";
    public static final String CHAT_UPDATE = "chat.update";

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

    public static final String FILES_COMMENTS_ADD = "files.comments.add";
    public static final String FILES_COMMENTS_DELETE = "files.comments.delete";
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
    // groups
    // ------------------------------

    public static final String GROUPS_ARCHIVE = "groups.archive";
    public static final String GROUPS_CLOSE = "groups.close";
    public static final String GROUPS_CREATE = "groups.create";
    public static final String GROUPS_CREATE_CHILD = "groups.createChild";
    public static final String GROUPS_HISTORY = "groups.history";
    public static final String GROUPS_INFO = "groups.info";
    public static final String GROUPS_INVITE = "groups.invite";
    public static final String GROUPS_KICK = "groups.kick";
    public static final String GROUPS_LEAVE = "groups.leave";
    public static final String GROUPS_LIST = "groups.list";
    public static final String GROUPS_MARK = "groups.mark";
    public static final String GROUPS_OPEN = "groups.open";
    public static final String GROUPS_RENAME = "groups.rename";
    public static final String GROUPS_SET_PURPOSE = "groups.setPurpose";
    public static final String GROUPS_SET_TOPIC = "groups.setTopic";
    public static final String GROUPS_UNARCHIVE = "groups.unarchive";
    public static final String GROUPS_REPLIES = "groups.replies";

    // ------------------------------
    // im
    // ------------------------------

    public static final String IM_CLOSE = "im.close";
    public static final String IM_HISTORY = "im.history";
    public static final String IM_LIST = "im.list";
    public static final String IM_MARK = "im.mark";
    public static final String IM_OPEN = "im.open";
    public static final String IM_REPLIES = "im.replies";

    // ------------------------------
    // migration
    // ------------------------------

    public static final String MIGRATION_EXCHANGE = "migration.exchange";

    // ------------------------------
    // mpim
    // ------------------------------

    public static final String MPIM_CLOSE = "mpim.close";
    public static final String MPIM_HISTORY = "mpim.history";
    public static final String MPIM_LIST = "mpim.list";
    public static final String MPIM_MARK = "mpim.mark";
    public static final String MPIM_OPEN = "mpim.open";
    public static final String MPIM_REPLIES = "mpim.replies";

    // ------------------------------
    // oauth
    // ------------------------------

    public static final String OAUTH_ACCESS = "oauth.access";
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

}
