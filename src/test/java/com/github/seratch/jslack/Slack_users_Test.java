package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.users.*;
import com.github.seratch.jslack.api.methods.response.channels.UsersLookupByEmailResponse;
import com.github.seratch.jslack.api.methods.response.users.*;
import com.github.seratch.jslack.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Slf4j
public class Slack_users_Test {

    Slack slack = Slack.getInstance();

    @Test
    public void users() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

        {
            UsersSetPresenceResponse response = slack.methods().usersSetPresence(
                    UsersSetPresenceRequest.builder().token(token).presence("away").build());
            assertThat(response.isOk(), is(true));
        }

        {
            UsersSetActiveResponse response = slack.methods().usersSetActive(
                    UsersSetActiveRequest.builder().token(token).build());
            assertThat(response.isOk(), is(true));
        }

        {
            UsersIdentityResponse response = slack.methods().usersIdentity(UsersIdentityRequest.builder().token(token).build());
            // TODO: test preparation?
            // {"ok":false,"error":"missing_scope","needed":"identity.basic","provided":"identify,read,post,client,apps,admin"}
            assertThat(response.isOk(), is(false));
        }

        UsersListResponse usersListResponse = slack.methods().usersList(UsersListRequest.builder()
        	        	.token(token)
        	        	.limit(2)
        	        	.presence(true)
        	        	.build());
        List<User> users = usersListResponse.getMembers();
        String userId = users.get(0).getId();

        {
            assertThat(usersListResponse.isOk(), is(true));
            assertThat(usersListResponse.getResponseMetadata(), is(notNullValue()));
            
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
            UsersInfoResponse response = slack.methods().usersInfo(UsersInfoRequest.builder().token(token).user(userId).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getUser(), is(notNullValue()));
        }

        {
            UsersGetPresenceResponse response = slack.methods().usersGetPresence(
                    UsersGetPresenceRequest.builder().token(token).user(userId).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getPresence(), is(notNullValue()));
        }

        {
            UsersConversationsResponse response = slack.methods().usersConversations(UsersConversationsRequest.builder()
                    .token(token)
                    .user(userId).build());
            assertThat(response.isOk(), is(true));
        }

        {
            UsersDeletePhotoResponse response = slack.methods().usersDeletePhoto(
                    UsersDeletePhotoRequest.builder().token(token).build());
            assertThat(response.isOk(), is(true));
        }

        File image = new File("src/test/resources/user_photo.jpg");
        {
            UsersSetPhotoResponse response = slack.methods().usersSetPhoto(UsersSetPhotoRequest.builder()
                    .token(token)
                    .image(image)
                    .build());
            assertThat(response.isOk(), is(true));
        }
    }

    @Test
    public void lookupByEMailSupported() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        UsersListResponse usersListResponse = slack.methods().usersList(UsersListRequest.builder()
                .token(token)
                .presence(true)
                .build());

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

        assertTrue(response.isOk());
        assertEquals(randomUserWhoHasEmail.getId(), response.getUser().getId());
    }
}