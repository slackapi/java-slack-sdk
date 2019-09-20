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
    }

}
