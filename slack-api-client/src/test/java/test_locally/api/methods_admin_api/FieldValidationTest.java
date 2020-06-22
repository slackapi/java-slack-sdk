package test_locally.api.methods_admin_api;

import com.slack.api.methods.response.admin.apps.*;
import com.slack.api.methods.response.admin.conversations.AdminConversationsSetTeamsResponse;
import com.slack.api.methods.response.admin.conversations.whitelist.AdminConversationsWhitelistAddResponse;
import com.slack.api.methods.response.admin.conversations.whitelist.AdminConversationsWhitelistListGroupsLinkedToChannelResponse;
import com.slack.api.methods.response.admin.conversations.whitelist.AdminConversationsWhitelistRemoveResponse;
import com.slack.api.methods.response.admin.emoji.*;
import com.slack.api.methods.response.admin.invite_requests.*;
import com.slack.api.methods.response.admin.teams.AdminTeamsAdminsListResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsCreateResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsListResponse;
import com.slack.api.methods.response.admin.teams.owners.AdminTeamsOwnersListResponse;
import com.slack.api.methods.response.admin.teams.settings.*;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsAddChannelsResponse;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsListChannelsResponse;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsRemoveChannelsResponse;
import com.slack.api.methods.response.admin.users.*;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
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
            AdminAppsRequestsListResponse obj = parse(prefix + "requests.list", AdminAppsRequestsListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getResponseMetadata(), "getMessages", "getWarnings");
            verifyIfAllGettersReturnNonNullRecursively(obj.getAppRequests());
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
    }

    @Test
    public void adminConversations() throws Exception {
        {
            AdminConversationsSetTeamsResponse obj = parse("admin.conversations.setTeams", AdminConversationsSetTeamsResponse.class);
            verifyIfAllGettersReturnNonNull(obj,
                    "getWarning",
                    "getResponseMetadata"
            );
        }
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
                verifyIfAllGettersReturnNonNullRecursively(obj.getResponseMetadata(), "getMessages");
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
            verifyIfAllGettersReturnNonNullRecursively(obj.getTeam());
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
}
