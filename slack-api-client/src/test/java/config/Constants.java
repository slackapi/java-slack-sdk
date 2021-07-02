package config;

public class Constants {
    private Constants() {
    }

    // For Socket Mode, Event Auth APIs
    public static final String SLACK_SDK_TEST_APP_TOKEN = "SLACK_SDK_TEST_APP_TOKEN";

    // Socket Mode
    public static final String SLACK_SDK_TEST_SOCKET_MODE_APP_TOKEN = "SLACK_SDK_TEST_SOCKET_MODE_APP_TOKEN";
    public static final String SLACK_SDK_TEST_SOCKET_MODE_BOT_TOKEN = "SLACK_SDK_TEST_SOCKET_MODE_BOT_TOKEN";

    // --------------------------------------------
    // Enterprise Grid
    // Workspace admin user's token in Grid
    public static final String SLACK_SDK_TEST_GRID_WORKSPACE_ADMIN_USER_TOKEN = "SLACK_SDK_TEST_GRID_WORKSPACE_ADMIN_USER_TOKEN";
    // Org admin user's token in Grid
    public static final String SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN = "SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN";
    // Main team Id for testing
    public static final String SLACK_SDK_TEST_GRID_TEAM_ID = "SLACK_SDK_TEST_GRID_TEAM_ID";
    // IDP User Group connected to SLACK_SDK_TEST_GRID_TEAM_ID
    public static final String SLACK_SDK_TEST_GRID_IDP_USERGROUP_ID = "SLACK_SDK_TEST_GRID_IDP_USERGROUP_ID";
    public static final String SLACK_SDK_TEST_GRID_IDP_USERGROUP_ID_2 = "SLACK_SDK_TEST_GRID_IDP_USERGROUP_ID_2";
    public static final String SLACK_SDK_TEST_GRID_USER_ID_ADMIN_AUTH = "SLACK_SDK_TEST_GRID_USER_ID_ADMIN_AUTH";
    // Shared channel ID for testing with Grid
    public static final String SLACK_SDK_TEST_GRID_SHARED_CHANNEL_ID = "SLACK_SDK_TEST_GRID_SHARED_CHANNEL_ID";
    // Org level installed app
    public static final String SLACK_SDK_TEST_GRID_ORG_LEVEL_APP_BOT_TOKEN = "SLACK_SDK_TEST_GRID_ORG_LEVEL_APP_BOT_TOKEN";
    // --------------------------------------------

    // normal user token / bot token
    public static final String SLACK_SDK_TEST_USER_TOKEN = "SLACK_SDK_TEST_USER_TOKEN";
    public static final String SLACK_SDK_TEST_BOT_TOKEN = "SLACK_SDK_TEST_BOT_TOKEN";
    // https://api.slack.com/apps?new_classic_app=1
    public static final String SLACK_SDK_TEST_CLASSIC_APP_BOT_TOKEN = "SLACK_SDK_TEST_CLASSIC_APP_BOT_TOKEN";

    // shared channel tests
    public static final String SLACK_SDK_TEST_SHARED_CHANNEL_ID = "SLACK_SDK_TEST_SHARED_CHANNEL_ID";
    public static final String SLACK_SDK_TEST_INCOMING_WEBHOOK_URL = "SLACK_SDK_TEST_INCOMING_WEBHOOK_URL";
    public static final String SLACK_SDK_TEST_INCOMING_WEBHOOK_CHANNEL_NAME = "SLACK_SDK_TEST_INCOMING_WEBHOOK_CHANNEL_NAME";

    public static final String SLACK_SDK_TEST_EMAIL_ADDRESS = "SLACK_SDK_TEST_EMAIL_ADDRESS";

    public static final String SLACK_SDK_TEST_REDIS_ENABLED = "SLACK_SDK_TEST_REDIS_ENABLED";
}
