package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.scim.response.UsersDeleteResponse;
import config.Constants;
import config.SlackTestConfig;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class scim_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void deleteUserTest() throws IOException {
        final String userId = "1";
        try {
            UsersDeleteResponse response = slack.scim().delete(r -> r
                    .token(token)
                    .id(userId));

            // testing with an SCIM activated account
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));

        } catch (SlackApiException apiError) {
            assertThat(apiError.getResponseBody(), is(notNullValue()));
            assertThat(apiError.getError(), is(notNullValue()));
        }
    }

}
