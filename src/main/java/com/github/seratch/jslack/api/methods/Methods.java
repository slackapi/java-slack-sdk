package com.github.seratch.jslack.api.methods;

public class Methods {

    private Methods() {
    }

    // ------------------------------
    // api
    // ------------------------------

    public static final String API_TEST = "api.test";

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
    public static final String CHANNELS_SET_PURPOSE = "channels.setPurpose";
    public static final String CHANNELS_SET_TOPIC = "channels.setTopic";
    public static final String CHANNELS_UNARCHIVE = "channels.unarchive";

    // ------------------------------
    // chat
    // ------------------------------

    public static final String CHAT_DELETE = "chat.delete";
    public static final String CHAT_ME_MESSAGE = "chat.meMessage";
    public static final String CHAT_POST_MESSAGE = "chat.postMessage";
    public static final String CHAT_UPDATE = "chat.update";

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

    // ------------------------------
    // im
    // ------------------------------

    // ------------------------------
    // mpim
    // ------------------------------

    // ------------------------------
    // oauth
    // ------------------------------

    // ------------------------------
    // pins
    // ------------------------------

    // ------------------------------
    // reactions
    // ------------------------------

    // ------------------------------
    // reminders
    // ------------------------------

    // ------------------------------
    // rtm
    // ------------------------------

    public static final String RTM_START = "rtm.start";

    // ------------------------------
    // search
    // ------------------------------

    // ------------------------------
    // stars
    // ------------------------------

    // ------------------------------
    // team
    // ------------------------------

    // ------------------------------
    // team.profile
    // ------------------------------

    // ------------------------------
    // usergroups
    // ------------------------------

    // ------------------------------
    // usergroups.users
    // ------------------------------

    // ------------------------------
    // users
    // ------------------------------

    public static final String USERS_GET_PRESENCE = "users.getPresence";
    public static final String USERS_IDENTITY = "users.identity";
    public static final String USERS_INFO = "users.info";
    public static final String USERS_LIST = "users.list";
    public static final String USERS_SET_ACTIVE = "users.setActive";
    public static final String USERS_SET_PRESENCE = "users.setPresence";

    // ------------------------------
    // users.profile
    // ------------------------------

    public static final String USERS_PROFILE_GET = "users.profile.get";
    public static final String USERS_PROFILE_SET = "users.profile.set";

}
