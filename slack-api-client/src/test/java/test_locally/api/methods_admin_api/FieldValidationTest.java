package test_locally.api.methods_admin_api;

import com.slack.api.methods.response.admin.apps.*;
import com.slack.api.methods.response.admin.auth.policy.AdminAuthPolicyAssignEntitiesResponse;
import com.slack.api.methods.response.admin.auth.policy.AdminAuthPolicyGetEntitiesResponse;
import com.slack.api.methods.response.admin.auth.policy.AdminAuthPolicyRemoveEntitiesResponse;
import com.slack.api.methods.response.admin.barriers.AdminBarriersCreateResponse;
import com.slack.api.methods.response.admin.barriers.AdminBarriersDeleteResponse;
import com.slack.api.methods.response.admin.barriers.AdminBarriersListResponse;
import com.slack.api.methods.response.admin.barriers.AdminBarriersUpdateResponse;
import com.slack.api.methods.response.admin.conversations.*;
import com.slack.api.methods.response.admin.conversations.ekm.AdminConversationsEkmListOriginalConnectedChannelInfoResponse;
import com.slack.api.methods.response.admin.conversations.restrict_access.AdminConversationsRestrictAccessAddGroupResponse;
import com.slack.api.methods.response.admin.conversations.restrict_access.AdminConversationsRestrictAccessListGroupsResponse;
import com.slack.api.methods.response.admin.conversations.restrict_access.AdminConversationsRestrictAccessRemoveGroupResponse;
import com.slack.api.methods.response.admin.conversations.whitelist.AdminConversationsWhitelistAddResponse;
import com.slack.api.methods.response.admin.conversations.whitelist.AdminConversationsWhitelistListGroupsLinkedToChannelResponse;
import com.slack.api.methods.response.admin.conversations.whitelist.AdminConversationsWhitelistRemoveResponse;
import com.slack.api.methods.response.admin.emoji.*;
import com.slack.api.methods.response.admin.functions.AdminFunctionsListResponse;
import com.slack.api.methods.response.admin.functions.AdminFunctionsPermissionsLookupResponse;
import com.slack.api.methods.response.admin.functions.AdminFunctionsPermissionsSetResponse;
import com.slack.api.methods.response.admin.invite_requests.*;
import com.slack.api.methods.response.admin.teams.AdminTeamsAdminsListResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsCreateResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsListResponse;
import com.slack.api.methods.response.admin.teams.owners.AdminTeamsOwnersListResponse;
import com.slack.api.methods.response.admin.teams.settings.*;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsAddChannelsResponse;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsAddTeamsResponse;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsListChannelsResponse;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsRemoveChannelsResponse;
import com.slack.api.methods.response.admin.users.*;
import com.slack.api.methods.response.admin.workflows.*;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import util.FileReader;

import java.io.IOException;

import static test_locally.api.util.FieldVerification.verifyIfAllGettersReturnNonNull;
import static test_locally.api.util.FieldVerification.verifyIfAllGettersReturnNonNullRecursively;

@Slf4j
public class FieldValidationTest {

    private FileReader reader = new FileReader("../json-logs/samples/api/");

    private <T> T parse(String path, Class<T> clazz) throws IOException {
        return GsonFactory.createSnakeCase().fromJson(reader.readWholeAsString(path + ".json"), clazz);
    }

    @Test
    public void adminApps() throws Exception {
        String prefix = "admin.apps.";
        {
            AdminAppsApproveResponse obj = parse(prefix + "approve", AdminAppsApproveResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminAppsApprovedListResponse obj = parse(prefix + "approved.list", AdminAppsApprovedListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getApprovedApps().get(0));
        }
        {
            AdminAppsClearResolutionResponse obj = parse(prefix + "clearResolution", AdminAppsClearResolutionResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminAppsRequestsListResponse obj = parse(prefix + "requests.list", AdminAppsRequestsListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getResponseMetadata(), "getMessages", "getWarnings");
            verifyIfAllGettersReturnNonNullRecursively(obj.getAppRequests());
        }
        {
            AdminAppsRequestsCancelResponse obj = parse(prefix + "requests.cancel", AdminAppsRequestsCancelResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminAppsRestrictResponse obj = parse(prefix + "restrict", AdminAppsRestrictResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminAppsRestrictedListResponse obj = parse(prefix + "restricted.list", AdminAppsRestrictedListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(
                    obj.getRestrictedApps().get(0),
                    "getImageOriginal");
        }
        {
            AdminAppsActivitiesListResponse obj = parse(prefix + "activities.list", AdminAppsActivitiesListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminAppsConfigSetResponse obj = parse(prefix + "config.set", AdminAppsConfigSetResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminAppsConfigLookupResponse obj = parse(prefix + "config.lookup", AdminAppsConfigLookupResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
    }

    @Test
    public void adminAuthPolicy() throws Exception {
        verifyIfAllGettersReturnNonNull(parse("admin.auth.policy.assignEntities", AdminAuthPolicyAssignEntitiesResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.auth.policy.getEntities", AdminAuthPolicyGetEntitiesResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.auth.policy.removeEntities", AdminAuthPolicyRemoveEntitiesResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
    }

    @Test
    public void adminBarriers() throws Exception {
        verifyIfAllGettersReturnNonNull(parse("admin.barriers.create", AdminBarriersCreateResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.barriers.delete", AdminBarriersDeleteResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.barriers.list", AdminBarriersListResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.barriers.update", AdminBarriersUpdateResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
    }

    @Test
    public void adminConversations() throws Exception {
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.setTeams", AdminConversationsSetTeamsResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.archive", AdminConversationsArchiveResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.convertToPrivate", AdminConversationsConvertToPrivateResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.create", AdminConversationsCreateResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.delete", AdminConversationsDeleteResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.disconnectShared", AdminConversationsDisconnectSharedResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.getConversationPrefs", AdminConversationsGetConversationPrefsResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.getTeams", AdminConversationsGetTeamsResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.invite", AdminConversationsInviteResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.rename", AdminConversationsRenameResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.search", AdminConversationsSearchResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.setConversationPrefs", AdminConversationsSetConversationPrefsResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.unarchive", AdminConversationsUnarchiveResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.ekm.listOriginalConnectedChannelInfo", AdminConversationsEkmListOriginalConnectedChannelInfoResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.bulkArchive", AdminConversationsBulkArchiveResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.bulkDelete", AdminConversationsBulkDeleteResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.bulkMove", AdminConversationsBulkMoveResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.convertToPublic", AdminConversationsConvertToPublicResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
        verifyIfAllGettersReturnNonNull(parse("admin.conversations.lookup", AdminConversationsLookupResponse.class),
                "getWarning",
                "getResponseMetadata"
        );
    }

    @Test
    public void adminConversationsWhitelist() throws Exception {
        {
            AdminConversationsWhitelistAddResponse obj = parse("admin.conversations.whitelist.add", AdminConversationsWhitelistAddResponse.class);
            verifyIfAllGettersReturnNonNull(obj,
                    "getWarning"
            );
        }
        {
            AdminConversationsWhitelistRemoveResponse obj = parse("admin.conversations.whitelist.remove", AdminConversationsWhitelistRemoveResponse.class);
            verifyIfAllGettersReturnNonNull(obj,
                    "getWarning"
            );
        }
        {
            AdminConversationsWhitelistListGroupsLinkedToChannelResponse obj = parse("admin.conversations.whitelist.listGroupsLinkedToChannel", AdminConversationsWhitelistListGroupsLinkedToChannelResponse.class);
            verifyIfAllGettersReturnNonNull(obj,
                    "getWarning"
            );
        }
    }

    @Test
    public void adminConversationsRestrictAccess() throws Exception {
        {
            AdminConversationsRestrictAccessAddGroupResponse obj = parse("admin.conversations.restrictAccess.addGroup", AdminConversationsRestrictAccessAddGroupResponse.class);
            verifyIfAllGettersReturnNonNull(obj,
                    "getWarning"
            );
        }
        {
            AdminConversationsRestrictAccessRemoveGroupResponse obj = parse("admin.conversations.restrictAccess.removeGroup", AdminConversationsRestrictAccessRemoveGroupResponse.class);
            verifyIfAllGettersReturnNonNull(obj,
                    "getWarning"
            );
        }
        {
            AdminConversationsRestrictAccessListGroupsResponse obj = parse("admin.conversations.restrictAccess.listGroups", AdminConversationsRestrictAccessListGroupsResponse.class);
            verifyIfAllGettersReturnNonNull(obj,
                    "getWarning"
            );
        }
    }

    @Test
    public void adminConversationsCustomRetention() throws Exception {
        {
            AdminConversationsGetCustomRetentionResponse obj = parse("admin.conversations.getCustomRetention", AdminConversationsGetCustomRetentionResponse.class);
            verifyIfAllGettersReturnNonNull(obj,
                    "getWarning",
                    "getResponseMetadata"
            );
        }
        {
            AdminConversationsSetCustomRetentionResponse obj = parse("admin.conversations.setCustomRetention", AdminConversationsSetCustomRetentionResponse.class);
            verifyIfAllGettersReturnNonNull(obj,
                    "getWarning",
                    "getResponseMetadata"
            );
        }
        {
            AdminConversationsRemoveCustomRetentionResponse obj = parse("admin.conversations.removeCustomRetention", AdminConversationsRemoveCustomRetentionResponse.class);
            verifyIfAllGettersReturnNonNull(obj,
                    "getWarning",
                    "getResponseMetadata"
            );
        }
    }

    @Test
    public void adminInviteRequests() throws Exception {
        String prefix = "admin.inviteRequests.";
        {
            AdminInviteRequestsApproveResponse obj = parse(prefix + "approve", AdminInviteRequestsApproveResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminInviteRequestsApprovedListResponse obj = parse(prefix + "approved.list", AdminInviteRequestsApprovedListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminInviteRequestsDeniedListResponse obj = parse(prefix + "denied.list", AdminInviteRequestsDeniedListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminInviteRequestsDenyResponse obj = parse(prefix + "deny", AdminInviteRequestsDenyResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminInviteRequestsListResponse obj = parse(prefix + "list", AdminInviteRequestsListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
    }

    @Ignore
    @Test
    public void adminTeams() throws Exception {
        String prefix = "admin.teams.";
        {
            AdminTeamsAdminsListResponse obj = parse(prefix + "admins.list", AdminTeamsAdminsListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getResponseMetadata(), "getMessages", "getWarnings");
        }
        {
            AdminTeamsCreateResponse obj = parse(prefix + "create", AdminTeamsCreateResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getResponseMetadata());
        }
        {
            AdminTeamsListResponse obj = parse(prefix + "list", AdminTeamsListResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getResponseMetadata", "getWarning");
            if (obj.getResponseMetadata() != null) {
                verifyIfAllGettersReturnNonNullRecursively(obj.getResponseMetadata(),
                        "getMessages", "getWarnings");
            }
        }
        {
            AdminTeamsOwnersListResponse obj = parse(prefix + "owners.list", AdminTeamsOwnersListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getResponseMetadata(), "getMessages", "getWarnings");
        }
        {
            AdminTeamsSettingsInfoResponse obj = parse(prefix + "settings.info", AdminTeamsSettingsInfoResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getTeam(), "getDiscoverable", "getLobSalesHomeEnabled");
        }
        {
            AdminTeamsSettingsSetDefaultChannelsResponse obj = parse(prefix + "settings.setDefaultChannels",
                    AdminTeamsSettingsSetDefaultChannelsResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getResponseMetadata());
        }
        {
            AdminTeamsSettingsSetDescriptionResponse obj = parse(prefix + "settings.setDescription",
                    AdminTeamsSettingsSetDescriptionResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminTeamsSettingsSetDiscoverabilityResponse obj = parse(prefix + "settings.setDiscoverability",
                    AdminTeamsSettingsSetDiscoverabilityResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
            // verifyIfAllGettersReturnNonNullRecursively(obj.getResponseMetadata());
        }
        {
            AdminTeamsSettingsSetIconResponse obj = parse(prefix + "settings.setIcon",
                    AdminTeamsSettingsSetIconResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getResponseMetadata());
        }
        {
            AdminTeamsSettingsSetNameResponse obj = parse(prefix + "settings.setName",
                    AdminTeamsSettingsSetNameResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
    }

    @Test
    public void adminUsers() throws Exception {
        String prefix = "admin.users.";
        {
            AdminUsersAssignResponse obj = parse(prefix + "assign", AdminUsersAssignResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
        {
            AdminUsersInviteResponse obj = parse(prefix + "invite", AdminUsersInviteResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminUsersListResponse obj = parse(prefix + "list", AdminUsersListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminUsersRemoveResponse obj = parse(prefix + "remove", AdminUsersRemoveResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
        {
            AdminUsersSessionResetResponse obj = parse(prefix + "session.reset", AdminUsersSessionResetResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminUsersSessionResetBulkResponse obj = parse(prefix + "session.resetBulk", AdminUsersSessionResetBulkResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminUsersSetAdminResponse obj = parse(prefix + "setAdmin", AdminUsersSetAdminResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
        {
            AdminUsersSetExpirationResponse obj = parse(prefix + "setExpiration", AdminUsersSetExpirationResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
        {
            AdminUsersSetOwnerResponse obj = parse(prefix + "setOwner", AdminUsersSetOwnerResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
        {
            AdminUsersSetRegularResponse obj = parse(prefix + "setRegular", AdminUsersSetRegularResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
        {
            AdminUsersSessionListResponse obj = parse(prefix + "session.list", AdminUsersSessionListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminUsersSessionGetSettingsResponse obj = parse(prefix + "session.getSettings", AdminUsersSessionGetSettingsResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminUsersSessionSetSettingsResponse obj = parse(prefix + "session.setSettings", AdminUsersSessionSetSettingsResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminUsersSessionClearSettingsResponse obj = parse(prefix + "session.clearSettings", AdminUsersSessionClearSettingsResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
    }

    @Test
    public void adminEmoji() throws Exception {
        String prefix = "admin.emoji.";
        {
            AdminEmojiAddResponse obj = parse(prefix + "add", AdminEmojiAddResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getResponseMetadata(), "getMessages");
        }
        {
            AdminEmojiAddAliasResponse obj = parse(prefix + "addAlias", AdminEmojiAddAliasResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getResponseMetadata(), "getMessages");
        }
        {
            AdminEmojiListResponse obj = parse(prefix + "list", AdminEmojiListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            AdminEmojiRemoveResponse obj = parse(prefix + "remove", AdminEmojiRemoveResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getResponseMetadata(), "getMessages");
        }
        {
            AdminEmojiRenameResponse obj = parse(prefix + "rename", AdminEmojiRenameResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getResponseMetadata(), "getMessages");
        }
    }

    @Test
    public void adminUsergroups() throws Exception {
        String prefix = "admin.usergroups.";
        {
            AdminUsergroupsAddTeamsResponse obj = parse(prefix + "addTeams", AdminUsergroupsAddTeamsResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
        {
            AdminUsergroupsAddChannelsResponse obj = parse(prefix + "addChannels", AdminUsergroupsAddChannelsResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata", "getInvalidChannels");
        }
        {
            AdminUsergroupsListChannelsResponse obj = parse(prefix + "listChannels", AdminUsergroupsListChannelsResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
        {
            AdminUsergroupsRemoveChannelsResponse obj = parse(prefix + "removeChannels", AdminUsergroupsRemoveChannelsResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
    }

    @Test
    public void adminFunctions() throws Exception {
        String prefix = "admin.functions.";
        {
            AdminFunctionsListResponse obj = parse(prefix + "list", AdminFunctionsListResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
        {
            AdminFunctionsPermissionsLookupResponse obj = parse(prefix + "permissions.lookup", AdminFunctionsPermissionsLookupResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
        {
            AdminFunctionsPermissionsSetResponse obj = parse(prefix + "permissions.set", AdminFunctionsPermissionsSetResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
    }

    @Test
    public void adminWorkflows() throws Exception {
        String prefix = "admin.workflows.";
        {
            AdminWorkflowsSearchResponse obj = parse(prefix + "search", AdminWorkflowsSearchResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
        {
            AdminWorkflowsCollaboratorsAddResponse obj = parse(prefix + "collaborators.add", AdminWorkflowsCollaboratorsAddResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
        {
            AdminWorkflowsCollaboratorsRemoveResponse obj = parse(prefix + "collaborators.add", AdminWorkflowsCollaboratorsRemoveResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
        {
            AdminWorkflowsPermissionsLookupResponse obj = parse(prefix + "permissions.lookup", AdminWorkflowsPermissionsLookupResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
        {
            AdminWorkflowsUnpublishResponse obj = parse(prefix + "unpublish", AdminWorkflowsUnpublishResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
        }
    }
}
