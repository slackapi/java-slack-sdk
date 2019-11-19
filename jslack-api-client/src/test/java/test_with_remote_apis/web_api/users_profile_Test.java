package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.response.users.profile.UsersProfileGetResponse;
import com.github.seratch.jslack.api.methods.response.users.profile.UsersProfileSetResponse;
import com.github.seratch.jslack.api.model.User;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class users_profile_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());

    @Test
    public void usersProfile() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

        {
            UsersProfileGetResponse response = slack.methods().usersProfileGet(r -> r.token(token));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getProfile(), is(notNullValue()));
        }

        {
            UsersProfileSetResponse response = slack.methods().usersProfileSet(
                    r -> r.token(token).name("skype").value("skype-" + System.currentTimeMillis()));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getProfile(), is(notNullValue()));
        }

        {
            User.Profile profile = new User.Profile();
            profile.setSkype("skype-" + System.currentTimeMillis());
            UsersProfileSetResponse response = slack.methods().usersProfileSet(
                    r -> r.token(token).profile(profile));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getProfile(), is(notNullValue()));
        }
    }

}
