package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.bots.BotsInfoRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersListRequest;
import com.github.seratch.jslack.api.methods.response.bots.BotsInfoResponse;
import com.github.seratch.jslack.api.model.User;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class bots_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());

    @Test
    public void botsInfoError() throws IOException, SlackApiException {
        BotsInfoResponse response = slack.methods().botsInfo(BotsInfoRequest.builder().build());
        assertThat(response.getError(), is(notNullValue()));
        assertThat(response.isOk(), is(false));
    }

    @Test
    public void botsInfo() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

        List<User> users = slack.methods().usersList(UsersListRequest.builder().token(token).build()).getMembers();
        User user = users.stream()
                .filter(u -> u.isBot() && !"USLACKBOT".equals(u.getId()))
                .findFirst()
                .get();
        String bot = user.getProfile().getBotId();

        BotsInfoResponse response = slack.methods().botsInfo(BotsInfoRequest.builder().token(token).bot(bot).build());
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getBot(), is(notNullValue()));
    }

}
