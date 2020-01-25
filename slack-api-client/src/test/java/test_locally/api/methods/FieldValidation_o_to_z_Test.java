package test_locally.api.methods;

import com.github.seratch.jslack.api.methods.response.channels.UsersLookupByEmailResponse;
import com.github.seratch.jslack.api.methods.response.oauth.OAuthAccessResponse;
import com.github.seratch.jslack.api.methods.response.oauth.OAuthTokenResponse;
import com.github.seratch.jslack.api.methods.response.oauth.OAuthV2AccessResponse;
import com.github.seratch.jslack.api.methods.response.pins.PinsAddResponse;
import com.github.seratch.jslack.api.methods.response.pins.PinsListResponse;
import com.github.seratch.jslack.api.methods.response.pins.PinsRemoveResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsAddResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsGetResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsListResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsRemoveResponse;
import com.github.seratch.jslack.api.methods.response.reminders.RemindersAddResponse;
import com.github.seratch.jslack.api.methods.response.reminders.RemindersCompleteResponse;
import com.github.seratch.jslack.api.methods.response.reminders.RemindersDeleteResponse;
import com.github.seratch.jslack.api.methods.response.reminders.RemindersInfoResponse;
import com.github.seratch.jslack.api.methods.response.rtm.RTMConnectResponse;
import com.github.seratch.jslack.api.methods.response.rtm.RTMStartResponse;
import com.github.seratch.jslack.api.methods.response.search.SearchAllResponse;
import com.github.seratch.jslack.api.methods.response.search.SearchFilesResponse;
import com.github.seratch.jslack.api.methods.response.search.SearchMessagesResponse;
import com.github.seratch.jslack.api.methods.response.stars.StarsAddResponse;
import com.github.seratch.jslack.api.methods.response.stars.StarsListResponse;
import com.github.seratch.jslack.api.methods.response.stars.StarsRemoveResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamAccessLogsResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamBillableInfoResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamInfoResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamIntegrationLogsResponse;
import com.github.seratch.jslack.api.methods.response.team.profile.TeamProfileGetResponse;
import com.github.seratch.jslack.api.methods.response.usergroups.*;
import com.github.seratch.jslack.api.methods.response.usergroups.users.UsergroupUsersListResponse;
import com.github.seratch.jslack.api.methods.response.usergroups.users.UsergroupUsersUpdateResponse;
import com.github.seratch.jslack.api.methods.response.users.*;
import com.github.seratch.jslack.api.methods.response.users.profile.UsersProfileGetResponse;
import com.github.seratch.jslack.api.methods.response.users.profile.UsersProfileSetResponse;
import com.github.seratch.jslack.api.methods.response.views.ViewsOpenResponse;
import com.github.seratch.jslack.api.methods.response.views.ViewsPublishResponse;
import com.github.seratch.jslack.api.methods.response.views.ViewsPushResponse;
import com.github.seratch.jslack.api.methods.response.views.ViewsUpdateResponse;
import com.slack.api.model.Usergroup;
import com.github.seratch.jslack.common.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import test_locally.api.util.FileReader;

import java.io.IOException;

import static test_locally.api.util.FieldVerification.verifyIfAllGettersReturnNonNull;
import static test_locally.api.util.FieldVerification.verifyIfAllGettersReturnNonNullRecursively;

@Slf4j
public class FieldValidation_o_to_z_Test {

    private FileReader reader = new FileReader("../json-logs/samples/api/");

    private <T> T parse(String path, Class<T> clazz) throws IOException {
        return GsonFactory.createSnakeCase().fromJson(reader.readWholeAsString(path + ".json"), clazz);
    }

    @Test
    public void oauth_access() throws Exception {
        OAuthAccessResponse obj = parse("oauth.access", OAuthAccessResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNullRecursively(obj.getBot());
        verifyIfAllGettersReturnNonNullRecursively(obj.getIncomingWebhook());
    }

    @Test
    public void oauth_v2_access() throws Exception {
        OAuthV2AccessResponse obj = parse("oauth.v2.access", OAuthV2AccessResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNullRecursively(obj.getAuthedUser());
        verifyIfAllGettersReturnNonNullRecursively(obj.getEnterprise());
        verifyIfAllGettersReturnNonNullRecursively(obj.getIncomingWebhook());
        verifyIfAllGettersReturnNonNullRecursively(obj.getTeam());
    }

    @Test
    public void oauth_token() throws Exception {
        OAuthTokenResponse obj = parse("oauth.token", OAuthTokenResponse.class);
        // TODO: better testing
        verifyIfAllGettersReturnNonNull(obj,
                "getWarning",
                "getAccessToken",
                "getScope",
                "getBot",
                "getIncomingWebhook",
                "getTeamId",
                "getTeamName",
                "getUserId"
        );
    }

    @Test
    public void pins_add() throws Exception {
        PinsAddResponse obj = parse("pins.add", PinsAddResponse.class);
        verifyIfAllGettersReturnNonNull(obj, "getWarning", "getResponseMetadata");
    }

    @Test
    public void pins_list() throws Exception {
        PinsListResponse obj = parse("pins.list", PinsListResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNull(obj.getItems().get(0),
                "getMessage",
                "getChannel",
                "getComment"
        );
    }

    @Test
    public void pins_remove() throws Exception {
        PinsRemoveResponse obj = parse("pins.remove", PinsRemoveResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void reactions_add() throws Exception {
        ReactionsAddResponse obj = parse("reactions.add", ReactionsAddResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void reactions_get() throws Exception {
        ReactionsGetResponse obj = parse("reactions.get", ReactionsGetResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNull(obj.getMessage());
    }

    @Test
    public void reactions_list() throws Exception {
        ReactionsListResponse obj = parse("reactions.list", ReactionsListResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNull(obj.getItems().get(0));
    }

    @Test
    public void reactions_remove() throws Exception {
        ReactionsRemoveResponse obj = parse("reactions.remove", ReactionsRemoveResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void reminders_add() throws Exception {
        RemindersAddResponse obj = parse("reminders.add", RemindersAddResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNullRecursively(obj.getReminder());
    }

    @Test
    public void reminders_complete() throws Exception {
        RemindersCompleteResponse obj = parse("reminders.complete", RemindersCompleteResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void reminders_delete() throws Exception {
        RemindersDeleteResponse obj = parse("reminders.delete", RemindersDeleteResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void reminders_info() throws Exception {
        RemindersInfoResponse obj = parse("reminders.info", RemindersInfoResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNullRecursively(obj.getReminder());
    }

    @Test
    public void rtm_connect() throws Exception {
        RTMConnectResponse obj = parse("rtm.connect", RTMConnectResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void rtm_start() throws Exception {
        RTMStartResponse obj = parse("rtm.start", RTMStartResponse.class);
        verifyIfAllGettersReturnNonNull(obj,
                "getWarning",
                "getPrefs"
        );
    }

    @Test
    public void search_all() throws Exception {
        SearchAllResponse obj = parse("search.all", SearchAllResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNullRecursively(obj.getFiles(),
                "getIid",
                "getRefinements",
                // paging
                "getPerPage",
                "getSpill"
        );
        verifyIfAllGettersReturnNonNullRecursively(obj.getMessages(),
                "getIid",
                "getRefinements",
                // paging
                "getPerPage",
                "getSpill"
        );
        verifyIfAllGettersReturnNonNullRecursively(obj.getPosts(),
                "getIid",
                "getRefinements",
                // paging
                "getPerPage",
                "getSpill"
        );
    }

    @Test
    public void search_files() throws Exception {
        SearchFilesResponse obj = parse("search.files", SearchFilesResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNullRecursively(obj.getFiles(),
                "getIid",
                "getRefinements",
                // paging
                "getPerPage",
                "getSpill"
        );
    }

    @Test
    public void search_messages() throws Exception {
        SearchMessagesResponse obj = parse("search.messages", SearchMessagesResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNullRecursively(obj.getMessages(),
                "getIid",
                "getRefinements",
                // paging
                "getPerPage",
                "getSpill"
        );
    }

    @Test
    public void stars_add() throws Exception {
        StarsAddResponse obj = parse("stars.add", StarsAddResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void stars_list() throws Exception {
        StarsListResponse obj = parse("stars.list", StarsListResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNullRecursively(obj.getItems().get(0),
                "getBotId",
                "getSubject",
                "getExternalId",
                "getExternalUrl",
                "getAppId",
                "getAppName",
                "getThumb.+$",
                "getConvertedPdf",
                "getDeanimateGif",
                "getPjpeg",
                "getPlainText",
                "getPreviewPlainText",
                "getInitialComment",
                "getNumStars",
                "getPinnedTo",
                "getPinnedInfo",
                "getReactions",
                "getChannelActionsTs",
                "getChannelActionsCount",
                "getAttachments",
                "getBlocks",
                "getTo",
                "getFrom",
                "getCc",
                "getShares"
        );
    }

    @Test
    public void stars_remove() throws Exception {
        StarsRemoveResponse obj = parse("stars.remove", StarsRemoveResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void team_accessLogs() throws Exception {
        TeamAccessLogsResponse obj = parse("team.accessLogs", TeamAccessLogsResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNullRecursively(obj.getLogins().get(0),
                "getCountry",
                "getRegion",
                "getIsp"
        );
    }

    @Test
    public void team_billableInfo() throws Exception {
        TeamBillableInfoResponse obj = parse("team.billableInfo", TeamBillableInfoResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNullRecursively(obj.getBillableInfo().values().iterator().next());
    }

    @Test
    public void team_info() throws Exception {
        TeamInfoResponse obj = parse("team.info", TeamInfoResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNullRecursively(obj.getTeam(),
                "getEnterpriseId",
                "getEnterpriseName",
                "getDefaultChannels"
        );
    }

    @Test
    public void team_integrationLogs() throws Exception {
        TeamIntegrationLogsResponse obj = parse("team.integrationLogs", TeamIntegrationLogsResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNullRecursively(obj.getLogs().get(0));
    }

    @Test
    public void team_profile_get() throws Exception {
        TeamProfileGetResponse obj = parse("team.profile.get", TeamProfileGetResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNullRecursively(obj.getProfile().getFields().get(0),
                "getOptions",
                "getPossibleValues"
        );
    }

    @Test
    public void usergroups_create() throws Exception {
        UsergroupsCreateResponse obj = parse("usergroups.create", UsergroupsCreateResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNullRecursively(obj.getUsergroup(),
                "getUsers",
                "getAutoType",
                "getDeletedBy",
                "getUserCount"
        );
    }

    @Test
    public void usergroups_disable() throws Exception {
        UsergroupsDisableResponse obj = parse("usergroups.disable", UsergroupsDisableResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNullRecursively(obj.getUsergroup(),
                "getUsers",
                "getAutoType",
                "getDeletedBy",
                "getUserCount"
        );
    }

    void validateUsergroup(Usergroup usergroup) throws Exception {
        verifyIfAllGettersReturnNonNullRecursively(usergroup,
                "getUsers",
                "getAutoType",
                "getDeletedBy",
                "getUserCount"
        );

    }

    @Test
    public void usergroups_enable() throws Exception {
        UsergroupsEnableResponse obj = parse("usergroups.enable", UsergroupsEnableResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        validateUsergroup(obj.getUsergroup());
    }

    @Test
    public void usergroups_list() throws Exception {
        UsergroupsListResponse obj = parse("usergroups.list", UsergroupsListResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        validateUsergroup(obj.getUsergroups().get(0));
    }

    @Test
    public void usergroups_update() throws Exception {
        UsergroupsUpdateResponse obj = parse("usergroups.update", UsergroupsUpdateResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        validateUsergroup(obj.getUsergroup());
    }

    @Test
    public void usergroups_users_list() throws Exception {
        UsergroupUsersListResponse obj = parse("usergroups.users.list", UsergroupUsersListResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void usergroups_users_update() throws Exception {
        UsergroupUsersUpdateResponse obj = parse("usergroups.users.update", UsergroupUsersUpdateResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void users_conversations() throws Exception {
        UsersConversationsResponse obj = parse("users.conversations", UsersConversationsResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void users_deletePhoto() throws Exception {
        UsersDeletePhotoResponse obj = parse("users.deletePhoto", UsersDeletePhotoResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void users_getPresence() throws Exception {
        UsersGetPresenceResponse obj = parse("users.getPresence", UsersGetPresenceResponse.class);
        verifyIfAllGettersReturnNonNull(obj,
                "getWarning",
                "getConnectionCount",
                "getLastActivity"
        );
    }

    @Test
    public void users_identity() throws Exception {
        UsersIdentityResponse obj = parse("users.identity", UsersIdentityResponse.class);
        // TODO: better testing
        verifyIfAllGettersReturnNonNull(obj,
                "getWarning",
                "getUser",
                "getTeam"
        );
    }

    @Test
    public void users_info() throws Exception {
        UsersInfoResponse obj = parse("users.info", UsersInfoResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void users_list() throws Exception {
        UsersListResponse obj = parse("users.list", UsersListResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void users_lookupByEmail() throws Exception {
        UsersLookupByEmailResponse obj = parse("users.lookupByEmail", UsersLookupByEmailResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void users_profile_get() throws Exception {
        UsersProfileGetResponse obj = parse("users.profile.get", UsersProfileGetResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void users_profile_set() throws Exception {
        UsersProfileSetResponse obj = parse("users.profile.set", UsersProfileSetResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void users_setActive() throws Exception {
        UsersSetActiveResponse obj = parse("users.setActive", UsersSetActiveResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void users_setPhoto() throws Exception {
        UsersSetPhotoResponse obj = parse("users.setPhoto", UsersSetPhotoResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void users_setPresence() throws Exception {
        UsersSetPresenceResponse obj = parse("users.setPresence", UsersSetPresenceResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void views_open() throws Exception {
        ViewsOpenResponse obj = parse("views.open", ViewsOpenResponse.class);
        verifyIfAllGettersReturnNonNull(obj,
                "getWarning",
                // TODO: better testing
                "getView"
        );
    }

    @Test
    public void views_publish() throws Exception {
        ViewsPublishResponse obj = parse("views.publish", ViewsPublishResponse.class);
        verifyIfAllGettersReturnNonNull(obj,
                "getWarning",
                "getResponseMetadata",
                // TODO: better testing
                "getView"
        );
    }

    @Test
    public void views_push() throws Exception {
        ViewsPushResponse obj = parse("views.push", ViewsPushResponse.class);
        verifyIfAllGettersReturnNonNull(obj,
                "getWarning",
                // TODO: better testing
                "getView"
        );
    }

    @Test
    public void views_update() throws Exception {
        ViewsUpdateResponse obj = parse("views.update", ViewsUpdateResponse.class);
        verifyIfAllGettersReturnNonNull(obj,
                "getWarning",
                // TODO: better testing
                "getView"
        );
    }

}
