package com.github.seratch.jslack.api.audit;

public class Actions {
    private Actions() {
    }

    public static class WorkspaceOrOrg {
        private WorkspaceOrOrg() {
        }

        public static final String workspace_created = "workspace_created";
        public static final String workspace_deleted = "workspace_deleted";
        public static final String organization_created = "organization_created";
        public static final String organization_deleted = "organization_deleted";
        public static final String organization_renamed = "organization_renamed";
        public static final String organization_domain_changed = "organization_domain_changed";
        public static final String organization_accepted_migration = "organization_accepted_migration";
        public static final String organization_declined_migration = "organization_declined_migration";
        public static final String emoji_added = "emoji_added";
        public static final String emoji_removed = "emoji_removed";
        public static final String emoji_aliased = "emoji_aliased";
        public static final String emoji_renamed = "emoji_renamed";
        public static final String billing_address_added = "billing_address_added";
        public static final String migration_scheduled = "migration_scheduled";
        public static final String workspace_accepted_migration = "workspace_accepted_migration";
        public static final String workspace_declined_migration = "workspace_declined_migration";
        public static final String migration_completed = "migration_completed";
        public static final String migration_dms_mpdms_completed = "migration_dms_mpdms_completed";
        public static final String corporate_exports_approved = "corporate_exports_approved";
        public static final String corporate_exports_enabled = "corporate_exports_enabled";
        public static final String manual_export_started = "manual_export_started";
        public static final String manual_export_completed = "manual_export_completed";
        public static final String scheduled_export_started = "scheduled_export_started";
        public static final String scheduled_export_completed = "scheduled_export_completed";
        public static final String channels_export_started = "channels_export_started";
        public static final String channels_export_completed = "channels_export_completed";
        public static final String ekm_enrolled = "ekm_enrolled";
        public static final String ekm_unenrolled = "ekm_unenrolled";
        public static final String ekm_key_added = "ekm_key_added";
        public static final String ekm_key_removed = "ekm_key_removed";
        public static final String ekm_clear_cache_set = "ekm_clear_cache_set";
        public static final String ekm_logging_config_set = "ekm_logging_config_set";
        public static final String ekm_slackbot_enroll_notification_sent = "ekm_slackbot_enroll_notification_sent";
        public static final String ekm_slackbot_unenroll_notification_sent = "ekm_slackbot_unenroll_notification_sent";
        public static final String ekm_slackbot_rekey_notification_sent = "ekm_slackbot_rekey_notification_sent";
        public static final String ekm_slackbot_logging_notification_sent = "ekm_slackbot_logging_notification_sent";
        public static final String external_shared_channel_connected = "external_shared_channel_connected";
        public static final String external_shared_channel_disconnected = "external_shared_channel_disconnected";
        public static final String pref_sso_setting_changed = "pref.sso_setting_changed";
        public static final String pref_two_factor_auth_changed = "pref.two_factor_auth_changed";
        public static final String pref_public_channel_retention_changed = "pref.public_channel_retention_changed";
        public static final String pref_private_channel_retention_changed = "pref.private_channel_retention_changed";
        public static final String pref_dm_retention_changed = "pref.dm_retention_changed";
        public static final String pref_file_retention_changed = "pref.file_retention_changed";
        public static final String pref_retention_override_changed = "pref.retention_override_changed";
        public static final String pref_block_download_and_copy_on_untrusted_mobile = "pref.block_download_and_copy_on_untrusted_mobile";
        public static final String pref_block_file_download_for_unapproved_ip = "pref.block_file_download_for_unapproved_ip";
        public static final String pref_mobile_secondary_auth_timeout_changed = "pref.mobile_secondary_auth_timeout_changed";
        public static final String pref_dlp_access_changed = "pref.dlp_access_changed";
        public static final String pref_ent_browser_control = "pref.ent_browser_control";
        public static final String pref_ent_required_browser_name = "pref.ent_required_browser_name";
        public static final String pref_ent_required_browser = "pref.ent_required_browser";
        public static final String pref_allow_calls = "pref.allow_calls";
        public static final String pref_custom_tos = "pref.custom_tos";
        public static final String pref_who_can_manage_guests = "pref.who_can_manage_guests";
        public static final String pref_allow_message_deletion = "pref.allow_message_deletion";
        public static final String pref_msg_edit_window_mins = "pref.msg_edit_window_mins";
        public static final String pref_disallow_public_file_urls = "pref.disallow_public_file_urls";
        public static final String pref_who_can_create_public_channels = "pref.who_can_create_public_channels";
        public static final String pref_who_can_archive_channels = "pref.who_can_archive_channels";
        public static final String pref_who_can_create_private_channels = "pref.who_can_create_private_channels";
        public static final String pref_who_can_remove_from_public_channels = "pref.who_can_remove_from_public_channels";
        public static final String pref_who_can_remove_from_private_channels = "pref.who_can_remove_from_private_channels";
        public static final String pref_who_can_manage_channel_posting_prefs = "pref.who_can_manage_channel_posting_prefs";
        public static final String pref_app_whitelist_enabled = "pref.app_whitelist_enabled";
        public static final String pref_app_dir_only = "pref.app_dir_only";
        public static final String pref_sign_in_with_slack_disabled = "pref.sign_in_with_slack_disabled";
        public static final String pref_commands_only_regular = "pref.commands_only_regular";
        public static final String pref_hide_referers = "pref.hide_referers";
        public static final String pref_can_receive_shared_channels_invites = "pref.can_receive_shared_channels_invites";
        public static final String pref_who_can_manage_ext_shared_channels = "pref.who_can_manage_ext_shared_channels";
        public static final String pref_who_can_manage_shared_channels = "pref.who_can_manage_shared_channels";
        public static final String pref_enterprise_default_channels = "pref.enterprise_default_channels";
        public static final String pref_who_can_create_delete_user_groups = "pref.who_can_create_delete_user_groups";
        public static final String pref_who_can_edit_user_groups = "pref.who_can_edit_user_groups";
        public static final String pref_stats_only_admins = "pref.stats_only_admins";
        public static final String pref_emoji_only_admins = "pref.emoji_only_admins";
        public static final String pref_slackbot_responses_disabled = "pref.slackbot_responses_disabled";
        public static final String pref_slackbot_responses_only_admins = "pref.slackbot_responses_only_admins";
        public static final String pref_dnd_enabled = "pref.dnd_enabled";
        public static final String pref_dnd_start_hour = "pref.dnd_start_hour";
        public static final String pref_dnd_end_hour = "pref.dnd_end_hour";
        public static final String pref_username_policy = "pref.username_policy";
        public static final String pref_enterprise_team_creation_request = "pref.enterprise_team_creation_request";
        public static final String pref_loading_only_admins = "pref.loading_only_admins";
        public static final String pref_display_real_names = "pref.display_real_names";
        public static final String pref_enterprise_mobile_device_check = "pref.enterprise_mobile_device_check";
        public static final String manual_user_export_started = "manual_user_export_started";
        public static final String manual_user_export_completed = "manual_user_export_completed";
    }

    public static class User {
        private User() {
        }

        public static final String custom_tos_accepted = "custom_tos_accepted";
        public static final String guest_created = "guest_created";
        public static final String guest_deactivated = "guest_deactivated";
        public static final String guest_reactivated = "guest_reactivated";
        public static final String owner_transferred = "owner_transferred";
        public static final String role_change_to_admin = "role_change_to_admin";
        public static final String role_change_to_guest = "role_change_to_guest";
        public static final String role_change_to_owner = "role_change_to_owner";
        public static final String role_change_to_user = "role_change_to_user";
        public static final String user_created = "user_created";
        public static final String user_deactivated = "user_deactivated";
        public static final String user_login = "user_login";
        public static final String user_logout = "user_logout";
        public static final String user_reactivated = "user_reactivated";
        public static final String guest_expiration_set = "guest_expiration_set";
        public static final String guest_expiration_cleared = "guest_expiration_cleared";
        public static final String guest_expired = "guest_expired";
    }

    public static class File {
        private File() {
        }

        public static final String file_downloaded = "file_downloaded";
        public static final String file_uploaded = "file_uploaded";
        public static final String file_public_link_created = "file_public_link_created";
        public static final String file_public_link_revoked = "file_public_link_revoked";
        public static final String file_shared = "file_shared";
        public static final String file_downloaded_blocked = "file_downloaded_blocked";
    }

    public static class Channel {
        private Channel() {
        }

        public static final String user_channel_join = "user_channel_join";
        public static final String user_channel_leave = "user_channel_leave";
        public static final String guest_channel_join = "guest_channel_join";
        public static final String guest_channel_leave = "guest_channel_leave";
        public static final String public_channel_created = "public_channel_created";
        public static final String private_channel_created = "private_channel_created";
        public static final String public_channel_deleted = "public_channel_deleted";
        public static final String private_channel_deleted = "private_channel_deleted";
        public static final String public_channel_archive = "public_channel_archive";
        public static final String private_channel_archive = "private_channel_archive";
        public static final String public_channel_unarchive = "public_channel_unarchive";
        public static final String private_channel_unarchive = "private_channel_unarchive";
        public static final String public_channel_converted_to_private = "public_channel_converted_to_private";
        public static final String channel_workspaces_updated = "channel_workspaces_updated";
        public static final String external_shared_channel_invite_sent = "external_shared_channel_invite_sent";
        public static final String external_shared_channel_invite_accepted = "external_shared_channel_invite_accepted";
        public static final String external_shared_channel_invite_declined = "external_shared_channel_invite_declined";
        public static final String external_shared_channel_invite_expired = "external_shared_channel_invite_expired";
        public static final String external_shared_channel_invite_revoked = "external_shared_channel_invite_revoked";
        public static final String mpim_converted_to_private = "mpim_converted_to_private";
        public static final String external_shared_channel_connected = "external_shared_channel_connected";
        public static final String external_shared_channel_disconnected = "external_shared_channel_disconnected";
        public static final String external_shared_channel_reconnected = "external_shared_channel_reconnected";
        public static final String external_shared_channel_invite_approved = "external_shared_channel_invite_approved";
        public static final String external_shared_channel_invite_created = "external_shared_channel_invite_created";
        public static final String channel_moved = "channel_moved";
        public static final String group_converted_to_channel = "group_converted_to_channel";
    }

    public static class App {
        private App() {
        }

        public static final String app_installed = "app_installed";
        public static final String app_uninstalled = "app_uninstalled";
        public static final String app_scopes_expanded = "app_scopes_expanded";
        public static final String app_approved = "app_approved";
        public static final String app_resources_granted = "app_resources_granted";
        public static final String app_token_preserved = "app_token_preserved";
        public static final String workflow_app_token_preserved = "workflow_app_token_preserved";
        public static final String bot_token_upgraded = "bot_token_upgraded";
        public static final String bot_token_downgraded = "bot_token_downgraded";
        public static final String app_restricted = "app_restricted";
        public static final String app_removed_from_whitelist = "app_removed_from_whitelist";
    }

    public static class Message {
        private Message() {
        }

        public static final String message_tombstoned = "message_tombstoned";
        public static final String message_restored = "message_restored";
    }

}
