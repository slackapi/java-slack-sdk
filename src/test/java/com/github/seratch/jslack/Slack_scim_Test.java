package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.scim.request.UsersDeleteRequest;
import com.github.seratch.jslack.api.scim.response.UsersDeleteResponse;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class Slack_scim_Test {

    Slack slack = Slack.getInstance();
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void deleteUserTest() throws IOException {
        final String userId = "1";
        try {
            UsersDeleteResponse response = slack.scim().delete(
                    UsersDeleteRequest.builder()
                            .token(token)
                            .id(userId)
                            .build());

            // testing with an SCIM activated account
            assertThat(response.isOk(), is(true));

        } catch (SlackApiException apiError) {
            assertThat(apiError.getResponseBody(), is(notNullValue()));
            assertThat(apiError.getError(), is(nullValue()));
        }
    }

}
