package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.users.UsersLookupByEmailRequest;
import com.slack.api.methods.request.users.UsersSetActiveRequest;
import com.slack.api.methods.response.users.*;
import com.slack.api.model.User;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

@Slf4j
public class users_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
    String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);
    String enterpriseGridTeamAdminUserToken = System.getenv(
            Constants.SLACK_SDK_TEST_GRID_WORKSPACE_ADMIN_USER_TOKEN);

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void showUsers_bot() throws IOException, SlackApiException {
        UsersListResponse users = slack.methods(botToken).usersList(r -> r.includeLocale(true).limit(10));
        assertThat(users.getError(), is(nullValue()));

        UsersInfoResponse usersInfo = slack.methods(botToken)
                .usersInfo(r -> r.user(users.getMembers().get(0).getId()).includeLocale(true));
        assertThat(usersInfo.getError(), is(nullValue()));
        assertThat(usersInfo.getUser().getLocale(), is(notNullValue()));
    }

    @Test
    public void showUsers_user() throws IOException, SlackApiException {
        UsersListResponse users = slack.methods(userToken).usersList(r -> r.includeLocale(true).limit(10));
        assertThat(users.getError(), is(nullValue()));

        UsersInfoResponse usersInfo = slack.methods(userToken)
                .usersInfo(r -> r.user(users.getMembers().get(0).getId()).includeLocale(true));
        assertThat(usersInfo.getError(), is(nullValue()));
        assertThat(usersInfo.getUser().getLocale(), is(notNullValue()));
    }

    @Test
    public void showUsers_async() throws Exception {
        UsersListResponse users = slack.methodsAsync(botToken).usersList(r -> r.includeLocale(true).limit(10)).get();
        assertThat(users.getError(), is(nullValue()));

        UsersInfoResponse usersInfo = slack.methodsAsync(botToken)
                .usersInfo(r -> r.user(users.getMembers().get(0).getId()).includeLocale(true))
                .get();
        assertThat(usersInfo.getError(), is(nullValue()));
        assertThat(usersInfo.getUser().getLocale(), is(notNullValue()));
    }

    @Test
    public void enterpriseGrid() throws IOException, SlackApiException {
        if (enterpriseGridTeamAdminUserToken != null) {
            UsersListResponse users = slack.methods(enterpriseGridTeamAdminUserToken)
                    .usersList(r -> r.includeLocale(true).limit(10));
            assertThat(users.getError(), is(nullValue()));

            UsersInfoResponse usersInfo = slack.methods(enterpriseGridTeamAdminUserToken)
                    .usersInfo(r -> r.user(users.getMembers().get(0).getId()).includeLocale(true));
            assertThat(usersInfo.getError(), is(nullValue()));
            assertThat(usersInfo.getUser().getLocale(), is(notNullValue()));
        }
    }

    @Test
    public void usersScenarios_bot() throws IOException, SlackApiException {
        {
            UsersSetPresenceResponse response = slack.methods().usersSetPresence(r -> r.token(botToken).presence("away"));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            UsersSetActiveResponse response = slack.methods().usersSetActive(
                    UsersSetActiveRequest.builder().token(botToken).build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            // we don't store the result by this API call
            // see also: test_locally.sample_json_generation.MethodsResponseDumpTest
            Slack slack = Slack.getInstance();
            UsersIdentityResponse response = slack.methods().usersIdentity(r -> r.token(botToken));
            assertThat(response.getError(), is("not_allowed_token_type"));
            assertThat(response.isOk(), is(false));
        }

        UsersListResponse usersListResponse = slack.methods().usersList(r -> r
                .token(botToken)
                .limit(2)
                .presence(true));
        List<User> users = usersListResponse.getMembers();
        String userId = users.get(0).getId();

        {
            assertThat(usersListResponse.getError(), is(nullValue()));
            assertThat(usersListResponse.isOk(), is(true));

            assertThat(users, is(notNullValue()));
            User user = users.get(0);
            assertThat(user.getId(), is(notNullValue()));
            assertThat(user.getName(), is(notNullValue()));
            assertThat(user.getRealName(), is(notNullValue()));

            // As of 2018/07, these APIs are no longer supported
            // assertThat(user.getProfile().getFirstName(), is(nullValue()));
            // assertThat(user.getProfile().getLastName(), is(nullValue()));
            assertThat(user.getProfile().getDisplayName(), is(notNullValue()));
            assertThat(user.getProfile().getDisplayNameNormalized(), is(notNullValue()));
            assertThat(user.getProfile().getRealName(), is(notNullValue()));
            assertThat(user.getProfile().getRealNameNormalized(), is(notNullValue()));

            assertThat(user.getProfile().getImage24(), is(notNullValue()));
            assertThat(user.getProfile().getImage32(), is(notNullValue()));
            assertThat(user.getProfile().getImage48(), is(notNullValue()));
            assertThat(user.getProfile().getImage72(), is(notNullValue()));
            assertThat(user.getProfile().getImage192(), is(notNullValue()));
            assertThat(user.getProfile().getImage512(), is(notNullValue()));
        }

        {
            UsersInfoResponse response = slack.methods().usersInfo(r -> r.token(botToken).user(userId));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getUser(), is(notNullValue()));
        }

        {
            // we don't store the result by this API call
            // see also: test_locally.sample_json_generation.MethodsResponseDumpTest
            Slack slack = Slack.getInstance();
            UsersGetPresenceResponse response = slack.methods().usersGetPresence(r -> r.token(botToken).user(userId));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getPresence(), is(notNullValue()));
        }

        {
            UsersConversationsResponse response = slack.methods().usersConversations(r -> r
                    .token(botToken)
                    .user(userId));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            UsersDeletePhotoResponse response = slack.methods().usersDeletePhoto(r -> r.token(botToken));
            assertThat(response.getError(), is("not_allowed_token_type"));
        }

        File image = new File("src/test/resources/user_photo.jpg");
        {
            UsersSetPhotoResponse response = slack.methods().usersSetPhoto(r -> r
                    .token(botToken)
                    .image(image));
            assertThat(response.getError(), is("not_allowed_token_type"));
        }
    }

    @Test
    public void usersScenarios_user() throws IOException, SlackApiException {
        {
            UsersSetPresenceResponse response = slack.methods().usersSetPresence(r -> r.token(userToken).presence("away"));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            UsersSetActiveResponse response = slack.methods().usersSetActive(
                    UsersSetActiveRequest.builder().token(userToken).build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            // we don't store the result by this API call
            // see also: test_locally.sample_json_generation.MethodsResponseDumpTest
            Slack slack = Slack.getInstance();
            UsersIdentityResponse response = slack.methods().usersIdentity(r -> r.token(userToken));
            // TODO: test preparation?
            // {"ok":false,"error":"missing_scope","needed":"identity.basic","provided":"identify,read,post,client,apps,admin"}
            assertThat(response.getError(), is("missing_scope"));
            assertThat(response.isOk(), is(false));
        }

        UsersListResponse usersListResponse = slack.methods().usersList(r -> r
                .token(userToken)
                .limit(2)
                .presence(true));
        List<User> users = usersListResponse.getMembers();
        String userId = users.get(0).getId();

        {
            assertThat(usersListResponse.getError(), is(nullValue()));
            assertThat(usersListResponse.isOk(), is(true));

            assertThat(users, is(notNullValue()));
            User user = users.get(0);
            assertThat(user.getId(), is(notNullValue()));
            assertThat(user.getName(), is(notNullValue()));
            assertThat(user.getRealName(), is(notNullValue()));

            // As of 2018/07, these APIs are no longer supported
            // assertThat(user.getProfile().getFirstName(), is(nullValue()));
            // assertThat(user.getProfile().getLastName(), is(nullValue()));
            assertThat(user.getProfile().getDisplayName(), is(notNullValue()));
            assertThat(user.getProfile().getDisplayNameNormalized(), is(notNullValue()));
            assertThat(user.getProfile().getRealName(), is(notNullValue()));
            assertThat(user.getProfile().getRealNameNormalized(), is(notNullValue()));

            assertThat(user.getProfile().getImage24(), is(notNullValue()));
            assertThat(user.getProfile().getImage32(), is(notNullValue()));
            assertThat(user.getProfile().getImage48(), is(notNullValue()));
            assertThat(user.getProfile().getImage72(), is(notNullValue()));
            assertThat(user.getProfile().getImage192(), is(notNullValue()));
            assertThat(user.getProfile().getImage512(), is(notNullValue()));
        }

        {
            UsersInfoResponse response = slack.methods().usersInfo(r -> r.token(userToken).user(userId));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getUser(), is(notNullValue()));
        }

        {
            // we don't store the result by this API call
            // see also: test_locally.sample_json_generation.MethodsResponseDumpTest
            Slack slack = Slack.getInstance();
            UsersGetPresenceResponse response = slack.methods().usersGetPresence(r -> r.token(userToken).user(userId));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getPresence(), is(notNullValue()));
        }

        {
            UsersConversationsResponse response = slack.methods().usersConversations(r -> r
                    .token(userToken)
                    .user(userId));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            UsersDeletePhotoResponse response = slack.methods().usersDeletePhoto(r -> r.token(userToken));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        File image = new File("src/test/resources/user_photo.jpg");
        {
            UsersSetPhotoResponse response = slack.methods().usersSetPhoto(r -> r
                    .token(userToken)
                    .image(image));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
    }

    @Test
    public void lookupByEmailSupported_bot() throws IOException, SlackApiException {
        UsersListResponse usersListResponse = slack.methods().usersList(r -> r
                .token(botToken)
                .presence(true));

        List<User> users = usersListResponse.getMembers();
        User randomUserWhoHasEmail = null;
        for (User user : users) {
            if (user.getProfile() != null && user.getProfile().getEmail() != null) {
                randomUserWhoHasEmail = user;
                break;
            }
        }
        if (randomUserWhoHasEmail == null) {
            throw new IllegalStateException("Create a non-bot user for this test case in advance.");
        }

        UsersLookupByEmailResponse response = slack.methods().usersLookupByEmail(UsersLookupByEmailRequest.builder()
                .token(botToken)
                .email(randomUserWhoHasEmail.getProfile().getEmail())
                .build());

        assertThat(response.getError(), is(nullValue()));
        assertTrue(response.isOk());
        assertEquals(randomUserWhoHasEmail.getId(), response.getUser().getId());
    }

    @Test
    public void lookupByEmailSupported_user() throws IOException, SlackApiException {
        UsersListResponse usersListResponse = slack.methods().usersList(r -> r
                .token(userToken)
                .presence(true));

        List<User> users = usersListResponse.getMembers();
        User randomUserWhoHasEmail = null;
        for (User user : users) {
            if (user.getProfile() != null && user.getProfile().getEmail() != null) {
                randomUserWhoHasEmail = user;
                break;
            }
        }
        if (randomUserWhoHasEmail == null) {
            throw new IllegalStateException("Create a non-bot user for this test case in advance.");
        }

        UsersLookupByEmailResponse response = slack.methods().usersLookupByEmail(UsersLookupByEmailRequest.builder()
                .token(userToken)
                .email(randomUserWhoHasEmail.getProfile().getEmail())
                .build());

        assertThat(response.getError(), is(nullValue()));
        assertTrue(response.isOk());
        assertEquals(randomUserWhoHasEmail.getId(), response.getUser().getId());
    }
}
