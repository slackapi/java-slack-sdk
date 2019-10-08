package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.users.UsersLookupByEmailRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersSetActiveRequest;
import com.github.seratch.jslack.api.methods.response.channels.UsersLookupByEmailResponse;
import com.github.seratch.jslack.api.methods.response.users.*;
import com.github.seratch.jslack.api.model.User;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Slf4j
public class users_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());

    @Test
    public void showUsers() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        UsersListResponse users = slack.methods(token).usersList(r -> r.includeLocale(true).limit(100));
        assertThat(users.getError(), is(nullValue()));

        UsersInfoResponse usersInfo = slack.methods(token)
                .usersInfo(r -> r.user(users.getMembers().get(0).getId()).includeLocale(true));
        assertThat(usersInfo.getError(), is(nullValue()));
        assertThat(usersInfo.getUser().getLocale(), is(notNullValue()));

        for (User member : users.getMembers()) {
            log.info("user id: {} , name: {}", member.getId(), member.getName());
        }
    }

    @Test
    public void usersScenarios() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

        {
            UsersSetPresenceResponse response = slack.methods().usersSetPresence(r -> r.token(token).presence("away"));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            UsersSetActiveResponse response = slack.methods().usersSetActive(
                    UsersSetActiveRequest.builder().token(token).build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            UsersIdentityResponse response = slack.methods().usersIdentity(r -> r.token(token));
            // TODO: test preparation?
            // {"ok":false,"error":"missing_scope","needed":"identity.basic","provided":"identify,read,post,client,apps,admin"}
            assertThat(response.getError(), is("missing_scope"));
            assertThat(response.isOk(), is(false));
        }

        UsersListResponse usersListResponse = slack.methods().usersList(r -> r
                .token(token)
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
            UsersInfoResponse response = slack.methods().usersInfo(r -> r.token(token).user(userId));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getUser(), is(notNullValue()));
        }

        {
            UsersGetPresenceResponse response = slack.methods().usersGetPresence(r -> r.token(token).user(userId));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getPresence(), is(notNullValue()));
        }

        {
            UsersConversationsResponse response = slack.methods().usersConversations(r -> r
                    .token(token)
                    .user(userId));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            UsersDeletePhotoResponse response = slack.methods().usersDeletePhoto(r -> r.token(token));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        File image = new File("src/test/resources/user_photo.jpg");
        {
            UsersSetPhotoResponse response = slack.methods().usersSetPhoto(r -> r
                    .token(token)
                    .image(image));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
    }

    @Test
    public void lookupByEMailSupported() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        UsersListResponse usersListResponse = slack.methods().usersList(r -> r
                .token(token)
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
                .token(token)
                .email(randomUserWhoHasEmail.getProfile().getEmail())
                .build());

        assertThat(response.getError(), is(nullValue()));
        assertTrue(response.isOk());
        assertEquals(randomUserWhoHasEmail.getId(), response.getUser().getId());
    }
}