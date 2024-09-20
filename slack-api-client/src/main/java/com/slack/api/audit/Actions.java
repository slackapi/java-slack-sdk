package com.slack.api.audit;

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
        public static final String manual_user_export_started = "manual_user_export_started";
        public static final String manual_user_export_completed = "manual_user_export_completed";
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
        public static final String pref_who_can_dm_anyone = "pref.who_can_dm_anyone";
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
        public static final String pref_required_minimum_mobile_version_changed = "pref.required_minimum_mobile_version_changed";
        public static final String pref_admin_retention_override_changed = "pref.admin_retention_override_changed";
        public static final String pref_notification_redaction_type = "pref.notification_redaction_type";
        public static final String pref_session_duration_changed = "pref.session_duration_changed";
        public static final String pref_session_duration_type_changed = "pref.session_duration_type_changed";
        public static final String pref_slack_connect_allowed_workspaces = "pref.slack_connect_allowed_workspaces";
        public static final String pref_slack_connect_dm_only_verified_orgs = "pref.slack_connect_dm_only_verified_orgs";
        public static final String pref_content_review_enabled = "pref.content_review_enabled";
        public static final String pref_flagged_content_review_channel = "pref.flagged_content_review_channel";
        public static final String pref_slack_connect_approval_type = "pref.slack_connect_approval_type";
        public static final String pref_hermes_has_accepted_tos = "pref.hermes_has_accepted_tos";
        public static final String pref_allow_sponsored_slack_connections = "pref.allow_sponsored_slack_connections";
        public static final String pref_hermes_triggers_trippable_by_slack_connected_teams = "pref.hermes_triggers_trippable_by_slack_connected_teams";
        public static final String pref_dm_retention_redaction_duration = "pref.dm_retention_redaction_duration";
        public static final String pref_private_retention_redaction_duration = "pref.private_retention_redaction_duration";
        public static final String pref_public_retention_redaction_duration = "pref.public_retention_redaction_duration";
        public static final String pref_allow_huddles = "pref.allow_huddles";
        public static final String pref_workspace_access_changed = "pref.workspace_access_changed";
        public static final String pref_uneditable_user_profile_fields = "pref.uneditable_user_profile_fields";
        public static final String pref_ext_audit_log_retention_type_changed = "pref.ext_audit_log_retention_type_changed";
        public static final String pref_ext_audit_log_retention_duration_changed = "pref.ext_audit_log_retention_duration_changed";
        public static final String pref_required_minimum_desktop_version_changed = "pref.required_minimum_desktop_version_changed";
        public static final String pref_canvas_changed = "pref.canvas_changed";
        public static final String pref_platform_beta_enabled = "pref.platform_beta_enabled";
        public static final String pref_who_can_use_platform_beta = "pref.who_can_use_platform_beta";
        public static final String pref_platform_beta_interactions_with_external_triggers_and_workflows_enabled = "pref.platform_beta_interactions_with_external_triggers_and_workflows_enabled";
        public static final String pref_platform_beta_external_triggers_enabled = "pref.platform_beta_external_triggers_enabled";
        public static final String pref_two_factor_prevent_sms_changed = "pref.two_factor_prevent_sms_changed";
        public static final String pref_canvas_retention_changed = "pref.canvas_retention_changed";
        public static final String pref_atlas_profiles_access_changed = "pref.atlas_profiles_access_changed";
        public static final String pref_atlas_org_charts_access_changed = "pref.atlas_org_charts_access_changed";
        public static final String pref_mobile_session_duration_changed = "pref.mobile_session_duration_changed";
        public static final String pref_allow_native_gif_picker = "pref.allow_native_gif_picker";
        public static final String pref_invites_only_admins_changed = "pref.invites_only_admins_changed";
        public static final String pref_invite_requests_approval_channel_changed = "pref.invite_requests_approval_channel_changed";
        public static final String pref_members_only = "pref.members_only";
        public static final String pref_member_analytics_disabled = "pref.member_analytics_disabled";
        public static final String pref_private_channel_analytics_disabled = "pref.private_channel_analytics_disabled";
        public static final String pref_allow_canvas_version_history_changed = "pref.allow_canvas_version_history_changed";
        public static final String pref_flag_content_custom_link = "pref.flag_content_custom_link";
        public static final String pref_flag_content_users_to_notify = "pref.flag_content_users_to_notify";
        public static final String pref_flag_content_review_note_required = "pref.flag_content_review_note_required";
        public static final String pref_invited_user_preset = "pref.invited_user_preset";
        public static final String pref_max_guest_duration = "pref.max_guest_duration";
        public static final String pref_sso_sync_with_provider = "pref.sso_sync_with_provider";
        public static final String pref_flag_message_flagger_outcome_notif = "pref.flag_message_flagger_outcome_notif";
        public static final String pref_flag_message_author_outcome_notif = "pref.flag_message_author_outcome_notif";
        public static final String pref_allow_lists = "pref.allow_lists";
        public static final String manual_export_downloaded = "manual_export_downloaded";
        public static final String manual_export_deleted = "manual_export_deleted";
        public static final String scheduled_export_downloaded = "scheduled_export_downloaded";
        public static final String scheduled_export_deleted = "scheduled_export_deleted";
        public static final String channels_export_downloaded = "channels_export_downloaded";
        public static final String channels_export_deleted = "channels_export_deleted";
        public static final String manual_user_export_downloaded = "manual_user_export_downloaded";
        public static final String manual_user_export_deleted = "manual_user_export_deleted";
        public static final String approved_orgs_added = "approved_orgs_added";
        public static final String approved_orgs_removed = "approved_orgs_removed";
        public static final String org_verified = "org_verified";
        public static final String org_unverified = "org_unverified";
        public static final String org_public_url_updated = "org_public_url_updated";
        public static final String organization_verified = "organization_verified";
        public static final String organization_unverified = "organization_unverified";
        public static final String organization_public_url_updated = "organization_public_url_updated";
        public static final String idp_configuration_added = "idp_configuration_added";
        public static final String idp_configuration_deleted = "idp_configuration_deleted";
        public static final String idp_prod_configuration_updated = "idp_prod_configuration_updated";
        public static final String idp_test_configuration_updated = "idp_test_configuration_updated";
        public static final String team_unsupportedVersions_start_success = "team.unsupportedVersions.start.success";
        public static final String team_unsupportedVersions_start_failure = "team.unsupportedVersions.start.failure";
        public static final String team_unsupportedVersions_job_start = "team.unsupportedVersions.job.start";
        public static final String team_unsupportedVersions_job_end = "team.unsupportedVersions.job.end";
        public static final String intune_enabled = "intune_enabled";
        public static final String intune_disabled = "intune_disabled";
        public static final String workspace_copy_approval_source = "workspace_copy_approval_source";
        public static final String workspace_copy_approval_target = "workspace_copy_approval_target";
        public static final String workspace_copy_scheduled = "workspace_copy_scheduled";
        public static final String workspace_copy_started = "workspace_copy_started";
        public static final String workspace_copy_completed = "workspace_copy_completed";
        public static final String workspace_copy_approved = "workspace_copy_approved";
        public static final String domain_created = "domain_created";
        public static final String domain_verified = "domain_verified";
        public static final String domain_unverified = "domain_unverified";
        public static final String domain_deleted = "domain_deleted";
        public static final String slack_connect_invite_routing_enabled = "slack_connect_invite_routing_enabled";
        public static final String slack_connect_invite_routing_disabled = "slack_connect_invite_routing_disabled";
        public static final String flexible_access_control_set = "flexible_access_control_set";
        public static final String team_unsupported_versions_start_success = "team_unsupported_versions_start_success";
        public static final String team_unsupported_versions_start_failure = "team_unsupported_versions_start_failure";
        public static final String team_unsupported_versions_job_start = "team_unsupported_versions_job_start";
        public static final String team_unsupported_versions_job_end = "team_unsupported_versions_job_end";
        public static final String team_authorized_ip_range_set = "team_authorized_ip_range_set";
        public static final String bulk_session_reset_by_admin = "bulk_session_reset_by_admin";
        public static final String export_private_channel_analytics_created = "export_private_channel_analytics_created";
        public static final String audit_logs_export_csv_started = "audit_logs_export_csv_started";
        public static final String audit_logs_export_json_started = "audit_logs_export_json_started";
        public static final String audit_logs_records_searched = "audit_logs_records_searched";
        public static final String apps_analytics_export_started = "apps_analytics_export_started";
        public static final String apps_analytics_export_prepared = "apps_analytics_export_prepared";
        public static final String channel_audit_export_started = "channel_audit_export_started";
        public static final String channel_audit_export_completed = "channel_audit_export_completed";
        public static final String channel_audit_export_downloaded = "channel_audit_export_downloaded";
        public static final String domain_email_restricted = "domain_email_restricted";
        public static final String domain_email_unrestricted = "domain_email_unrestricted";
        public static final String slack_ai_summary_requested = "slack_ai_summary_requested";
        public static final String channel_audit_export_deleted = "channel_audit_export_deleted";
        public static final String quip_migration_org_mapping_removed = "quip_migration_org_mapping_removed";
        public static final String quip_migration_org_mapping_added = "quip_migration_org_mapping_added";
        public static final String organization_channel_audit_export_enabled = "organization_channel_audit_export_enabled";
        public static final String workspace_channel_audit_export_enabled = "workspace_channel_audit_export_enabled";
        public static final String organization_created_migration_intent = "organization_created_migration_intent";
        public static final String organization_accepted_migration_intent = "organization_accepted_migration_intent";
        public static final String organization_declined_migration_intent = "organization_declined_migration_intent";
        public static final String team_accepted_migration_intent = "team_accepted_migration_intent";
        public static final String team_declined_migration_intent = "team_declined_migration_intent";
        public static final String workflow_analytics_export_started = "workflow_analytics_export_started";
        public static final String seamless_auth_org_update = "seamless_auth_org_update";
        public static final String seamless_auth_org_disconnected = "seamless_auth_org_disconnected";
        public static final String seamless_auth_team_token_created = "seamless_auth_team_token_created";
        public static final String seamless_auth_team_token_revoked = "seamless_auth_team_token_revoked";
        public static final String seamless_auth_user_token_created = "seamless_auth_user_token_created";
        public static final String seamless_auth_user_token_revoked = "seamless_auth_user_token_revoked";
        public static final String migration_checklist_item_completed = "migration_checklist_item_completed";
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
        public static final String user_login_failed = "user_login_failed";
        public static final String user_logout = "user_logout";
        public static final String user_reactivated = "user_reactivated";
        public static final String user_session_invalidated = "user_session_invalidated";
        public static final String user_session_settings_changed = "user_session_settings_changed";
        public static final String guest_expiration_set = "guest_expiration_set";
        public static final String guest_expiration_cleared = "guest_expiration_cleared";
        public static final String guest_expired = "guest_expired";
        public static final String user_logout_compromised = "user_logout_compromised";
        public static final String user_session_reset_by_admin = "user_session_reset_by_admin";
        public static final String user_logout_non_compliant_mobile_app_version = "user_logout_non_compliant_mobile_app_version";
        public static final String user_force_upgrade_non_compliant_mobile_app_version = "user_force_upgrade_non_compliant_mobile_app_version";
        public static final String connect_dm_invite_generated = "connect_dm_invite_generated";
        public static final String connect_dm_invite_revoked = "connect_dm_invite_revoked";
        public static final String connect_dm_invite_accepted = "connect_dm_invite_accepted";
        public static final String connect_dm_invite_ignored = "connect_dm_invite_ignored";
        public static final String user_email_updated = "user_email_updated";
        public static final String user_username_updated = "user_username_updated";
        public static final String user_joined_workspace = "user_joined_workspace";
        public static final String guest_joined_workspace = "guest_joined_workspace";
        public static final String guest_transferred_to_external_user = "guest_transferred_to_external_user";
        public static final String user_password_reset_requested = "user_password_reset_requested";
        public static final String user_profile_updated = "user_profile_updated";
        public static final String user_profile_deleted = "user_profile_deleted";
        public static final String user_password_reset_slack_security = "user_password_reset_slack_security";
        public static final String quip_migration_user_mapping_removed = "quip_migration_user_mapping_removed";
        public static final String quip_migration_user_mapping_added = "quip_migration_user_mapping_added";
    }

    public static class File {
        private File() {
        }

        public static final String file_downloaded = "file_downloaded";
        public static final String file_uploaded = "file_uploaded";
        public static final String file_public_link_created = "file_public_link_created";
        public static final String file_public_link_revoked = "file_public_link_revoked";
        public static final String file_shared = "file_shared";
        public static final String file_download_blocked = "file_download_blocked";
        public static final String file_downloaded_blocked = "file_downloaded_blocked";
        public static final String file_malicious_content_detected = "file_malicious_content_detected";
        public static final String file_deleted = "file_deleted";
        public static final String file_upload_blocked_by_type = "file_upload_blocked_by_type";
        public static final String file_share_blocked_by_type = "file_share_blocked_by_type";
        public static final String file_owner_reassigned_for_quip_migration = "file_owner_reassigned_for_quip_migration";
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
        public static final String external_shared_channel_connected = "external_shared_channel_connected";
        public static final String external_shared_channel_disconnected = "external_shared_channel_disconnected";
        public static final String external_shared_channel_reconnected = "external_shared_channel_reconnected";
        public static final String external_shared_channel_invite_approved = "external_shared_channel_invite_approved";
        public static final String external_shared_channel_invite_created = "external_shared_channel_invite_created";
        public static final String external_shared_channel_access_upgraded = "external_shared_channel_access_upgraded";
        public static final String external_shared_channel_access_downgraded = "external_shared_channel_access_downgraded";
        public static final String external_shared_channel_disconnect_and_archived = "external_shared_channel_disconnect_and_archived";
        public static final String mpim_converted_to_private = "mpim_converted_to_private";
        public static final String channel_moved = "channel_moved";
        public static final String group_converted_to_channel = "group_converted_to_channel";
        public static final String channel_renamed = "channel_renamed";
        public static final String channel_posting_pref_changed_from_org_level = "channel_posting_pref_changed_from_org_level";
        public static final String channel_posting_permissions_updated = "channel_posting_permissions_updated";
        public static final String channel_enable_at_channel_pref_changed_from_org_level = "channel_enable_at_channel_pref_changed_from_org_level";
        public static final String channel_enable_at_here_pref_changed_from_org_level = "channel_enable_at_here_pref_changed_from_org_level";
        public static final String external_shared_channel_invite_auto_revoked = "external_shared_channel_invite_auto_revoked";
        public static final String channel_email_address_created = "channel_email_address_created";
        public static final String channel_email_address_deleted = "channel_email_address_deleted";
        public static final String channel_retention_changed = "channel_retention_changed";
        public static final String channel_membership_limit_changed_from_org_level = "channel_membership_limit_changed_from_org_level";
        public static final String channel_can_huddle_pref_changed_from_org_level = "channel_can_huddle_pref_changed_from_org_level";
        public static final String connect_dm_invite_generated = "connect_dm_invite_generated";
        public static final String connect_dm_invite_revoked = "connect_dm_invite_revoked";
        public static final String connect_dm_invite_accepted = "connect_dm_invite_accepted";
        public static final String connect_dm_invite_ignored = "connect_dm_invite_ignored";
        public static final String dm_user_added = "dm_user_added";
        public static final String public_channel_preview = "public_channel_preview";
        public static final String private_channel_converted_to_public = "private_channel_converted_to_public";
        public static final String record_channel_archive = "record_channel_archive";
        public static final String record_channel_unarchive = "record_channel_unarchive";
        public static final String channel_converted_to_record_channel = "channel_converted_to_record_channel";
        public static final String record_channel_converted_to_channel = "record_channel_converted_to_channel";
        public static final String external_shared_channel_invite_requested = "external_shared_channel_invite_requested";
        public static final String external_shared_channel_invite_request_approved = "external_shared_channel_invite_request_approved";
        public static final String external_shared_channel_invite_request_denied = "external_shared_channel_invite_request_denied";
        public static final String channel_tab_added = "channel_tab_added";
        public static final String channel_tab_removed = "channel_tab_removed";
        public static final String external_shared_channel_host_transferred = "external_shared_channel_host_transferred";
        public static final String channel_template_provisioned = "channel_template_provisioned";
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
        public static final String app_datastore_created = "app_datastore_created";
        public static final String app_datastore_updated = "app_datastore_updated";
        public static final String app_datastore_deleted = "app_datastore_deleted";
        public static final String workflow_app_token_preserved = "workflow_app_token_preserved";
        public static final String bot_token_upgraded = "bot_token_upgraded";
        public static final String bot_token_downgraded = "bot_token_downgraded";
        public static final String app_restricted = "app_restricted";
        public static final String app_removed_from_whitelist = "app_removed_from_whitelist";
        public static final String org_app_workspace_added = "org_app_workspace_added";
        public static final String org_app_workspace_removed = "org_app_workspace_removed";
        public static final String org_app_installed = "org_app_installed";
        public static final String org_app_uninstalled = "org_app_uninstalled";
        public static final String org_app_future_workspace_install_enabled = "org_app_future_workspace_install_enabled";
        public static final String org_app_future_workspace_install_disabled = "org_app_future_workspace_install_disabled";
        public static final String org_app_upgraded_to_org_install = "org_app_upgraded_to_org_install";
        public static final String app_allowlist_rule_matched = "app_allowlist_rule_matched";
        public static final String app_collaborator_added = "app_collaborator_added";
        public static final String app_collaborator_removed = "app_collaborator_removed";
        public static final String app_deleted = "app_deleted";
        public static final String app_variable_added = "app_variable_added";
        public static final String app_variable_removed = "app_variable_removed";
        public static final String app_deployed = "app_deployed";
        public static final String app_manifest_created = "app_manifest_created";
        public static final String app_manifest_updated = "app_manifest_updated";
    }

    public static class Message {
        private Message() {
        }

        public static final String message_tombstoned = "message_tombstoned";
        public static final String message_restored = "message_restored";
        public static final String message_flagged = "message_flagged";
        public static final String message_moderated = "message_moderated";
        public static final String thread_replies_disabled = "thread_replies_disabled";
        public static final String thread_replies_enabled = "thread_replies_enabled";
        public static final String permissions_assigned = "permissions_assigned";
        public static final String permissions_removed = "permissions_removed";
        public static final String private_message_forwarded = "private_message_forwarded";
        public static final String message_activity_viewed = "message_activity_viewed";
        public static final String message_flag_dismissed = "message_flag_dismissed";
        public static final String sales_home_notification_forwarded = "sales_home_notification_forwarded";
        public static final String record_shared = "record_shared";
        public static final String message_flag_assignment = "message_flag_assignment";
        public static final String message_flag_unassignment = "message_flag_unassignment";
    }

    public static class WorkflowBuilder {
        private WorkflowBuilder() {
        }

        public static final String workflow_created = "workflow_created";
        public static final String workflow_deleted = "workflow_deleted";
        public static final String workflow_published = "workflow_published";
        public static final String workflow_unpublished = "workflow_unpublished";
        public static final String workflow_modified = "workflow_modified";
        public static final String workflow_responses_csv_download = "workflow_responses_csv_download";
        public static final String workflow_unknown_action = "workflow_unknown_action";
    }

    public static class Barrier {
        private Barrier() {
        }

        public static final String barrier_created = "barrier_created";
        public static final String barrier_updated = "barrier_updated";
        public static final String barrier_deleted = "barrier_deleted";
    }

    public static class SlackCLI {
        private SlackCLI() {
        }

        public static final String cli_login = "cli_login";
        public static final String cli_logout = "cli_logout";
        public static final String cli_app_deploy = "cli_app_deploy";
        public static final String cli_app_install = "cli_app_install";
        public static final String cli_app_delete = "cli_app_delete";
    }

    public static class Huddle {
        private Huddle() {
        }

        public static final String huddle_ended = "huddle_ended";
        public static final String huddle_started = "huddle_started";
        public static final String huddle_participant_joined = "huddle_participant_joined";
        public static final String huddle_participant_left = "huddle_participant_left";
        public static final String huddle_participant_dropped = "huddle_participant_dropped";
        public static final String huddle_screenshare_on = "huddle_screenshare_on";
        public static final String huddle_screenshare_off = "huddle_screenshare_off";
        public static final String huddle_knock_accepted = "huddle_knock_accepted";
        public static final String huddle_participant_transcript_consent = "huddle_participant_transcript_consent";
        public static final String huddle_transcription_start_notification = "huddle_transcription_start_notification";
        public static final String huddle_transcription_started = "huddle_transcription_started";
        public static final String huddle_transcription_resumed = "huddle_transcription_resumed";
        public static final String huddle_transcription_cancelled = "huddle_transcription_cancelled";
        public static final String huddle_transcription_paused = "huddle_transcription_paused";
    }

    public static class Anomaly {
        private Anomaly() {
        }

        public static final String anomaly = "anomaly";
    }

    public static class Role {
        private Role() {
        }

        public static final String role_assigned = "role_assigned";
        public static final String role_removed = "role_removed";
        public static final String role_created = "role_created";
        public static final String role_updated = "role_updated";
        public static final String role_deleted = "role_deleted";
    }

    public static class Subteam {
        private Subteam() {
        }

        public static final String user_added_to_usergroup = "user_added_to_usergroup";
        public static final String user_removed_from_usergroup = "user_removed_from_usergroup";
        public static final String default_channel_added_to_usergroup = "default_channel_added_to_usergroup";
        public static final String default_channel_removed_from_usergroup = "default_channel_removed_from_usergroup";
        public static final String role_added_to_usergroup = "role_added_to_usergroup";
        public static final String role_removed_from_usergroup = "role_removed_from_usergroup";
        public static final String role_modified_on_usergroup = "role_modified_on_usergroup";
        public static final String usergroup_added_to_team = "usergroup_added_to_team";
        public static final String usergroup_removed_from_team = "usergroup_removed_from_team";
        public static final String usergroup_add_to_team_enqueued = "usergroup_add_to_team_enqueued";
        public static final String usergroup_remove_from_team_enqueued = "usergroup_remove_from_team_enqueued";
        public static final String usergroup_updated = "usergroup_updated";
        public static final String usergroup_update_enqueued = "usergroup_update_enqueued";
        public static final String usergroup_section_updated = "usergroup_section_updated";
    }

    public static class AccountTypeRole {
        private AccountTypeRole() {
        }

        public static final String permissions_assigned = "permissions_assigned";
        public static final String permissions_removed = "permissions_removed";
    }

    public static class AppApprovalAutomationRule {
        private AppApprovalAutomationRule() {
        }

        public static final String app_allowlist_rule_created = "app_allowlist_rule_created";
        public static final String app_allowlist_rule_updated = "app_allowlist_rule_updated";
        public static final String app_allowlist_rule_activated = "app_allowlist_rule_activated";
        public static final String app_allowlist_rule_deactivated = "app_allowlist_rule_deactivated";
        public static final String app_allowlist_rule_deleted = "app_allowlist_rule_deleted";
        public static final String app_allowlist_rule_reordered = "app_allowlist_rule_reordered";
        public static final String app_allowlist_scopes_rating_updated = "app_allowlist_scopes_rating_updated";
    }

    public static class WorkflowV2 {
        private WorkflowV2() {
        }

        public static final String workflow_v2_published = "workflow_v2_published";
        public static final String workflow_trigger_permission_set = "workflow_trigger_permission_set";
        public static final String workflow_trigger_permission_added = "workflow_trigger_permission_added";
        public static final String workflow_trigger_permission_removed = "workflow_trigger_permission_removed";
    }

    public static class Canvas {
        private Canvas() {
        }

        public static final String canvas_access_added = "canvas_access_added";
        public static final String canvas_access_downgraded = "canvas_access_downgraded";
        public static final String canvas_access_revoked = "canvas_access_revoked";
        public static final String canvas_access_upgraded = "canvas_access_upgraded";
        public static final String canvas_created = "canvas_created";
        public static final String canvas_deleted = "canvas_deleted";
        public static final String canvas_downloaded = "canvas_downloaded";
        public static final String canvas_edited = "canvas_edited";
        public static final String canvas_linksharing_disabled = "canvas_linksharing_disabled";
        public static final String canvas_linksharing_enabled = "canvas_linksharing_enabled";
        public static final String canvas_merged = "canvas_merged";
        public static final String canvas_opened = "canvas_opened";
        public static final String canvas_ownership_transferred = "canvas_ownership_transferred";
        public static final String canvas_restored = "canvas_restored";
        public static final String canvas_shared = "canvas_shared";
        public static final String canvas_tombstoned = "canvas_tombstoned";
        public static final String canvas_unshared = "canvas_unshared";
        public static final String canvas_converted_to_template = "canvas_converted_to_template";
        public static final String canvas_template_used = "canvas_template_used";
        public static final String canvas_template_reverted = "canvas_template_reverted";
        public static final String canvas_quip_document_cloned = "canvas_quip_document_cloned";
        public static final String canvas_undeleted = "canvas_undeleted";
        public static final String canvas_restricted_sharing_enabled = "canvas_restricted_sharing_enabled";
        public static final String canvas_restricted_sharing_disabled = "canvas_restricted_sharing_disabled";
    }

    public static class Function {
        private Function() {
        }

        public static final String function_distribution_permission_added = "function_distribution_permission_added";
        public static final String function_distribution_permission_removed = "function_distribution_permission_removed";
        public static final String function_distribution_permission_set = "function_distribution_permission_set";
    }

    public static class SalesElevate {
        private SalesElevate() {
        }

        public static final String sales_elevate_workflow_updated = "sales_elevate_workflow_updated";
        public static final String sales_elevate_members_added = "sales_elevate_members_added";
        public static final String sales_elevate_members_removed = "sales_elevate_members_removed";
        public static final String sales_elevate_object_mappings_set = "sales_elevate_object_mappings_set";
        public static final String sales_elevate_object_mappings_updated = "sales_elevate_object_mappings_updated";
        public static final String sales_elevate_notifications_settings_updated = "sales_elevate_notifications_settings_updated";
        public static final String sales_elevate_opportunity_list_settings_updated = "sales_elevate_opportunity_list_settings_updated";
        public static final String sales_elevate_org_connection_added = "sales_elevate_org_connection_added";
        public static final String sales_elevate_org_connection_removed = "sales_elevate_org_connection_removed";
        public static final String sales_elevate_sales_admin_activity_config_changed = "sales_elevate_sales_admin_activity_config_changed";
    }

    public static class NativeDlp {
        private NativeDlp() {
        }

        public static final String native_dlp_rule_created = "native_dlp_rule_created";
        public static final String native_dlp_rule_deactivated = "native_dlp_rule_deactivated";
        public static final String native_dlp_rule_reactivated = "native_dlp_rule_reactivated";
        public static final String native_dlp_rule_edited = "native_dlp_rule_edited";
        public static final String native_dlp_rule_action_applied = "native_dlp_rule_action_applied";
    }

    public static class Template {
        private Template() {
        }

        public static final String template_created = "template_created";
        public static final String template_deleted = "template_deleted";
        public static final String template_provisioned = "template_provisioned";
    }
}
