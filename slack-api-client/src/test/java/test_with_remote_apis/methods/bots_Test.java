package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.bots.BotsInfoResponse;
import com.slack.api.model.User;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class bots_Test {

    static String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static SlackConfig invalidConfig = new SlackConfig();

    static {
        invalidConfig.setMethodsEndpointUrlPrefix("https://127.0.0.1:9999/");
    }

    Slack brokenSlack = Slack.getInstance(invalidConfig);

    private static String cachedBotId = null;

    @BeforeClass
    public static void loadBotId() throws IOException, SlackApiException {
        if (cachedBotId == null) {
            List<User> users = slack.methods(botToken).usersList(req -> req).getMembers();
            User user = null;
            for (User u : users) {
                if (u.isBot() && !"USLACKBOT".equals(u.getId())) {
                    user = u;
                    break;
                }
            }
            cachedBotId = user.getProfile().getBotId();
        }
    }

    String findBotId() throws IOException, SlackApiException {
        if (cachedBotId == null) {
            loadBotId();
        }
        return cachedBotId;
    }

    @Test
    public void botsInfoError() throws IOException, SlackApiException {
        BotsInfoResponse response = slack.methods().botsInfo(req -> req);
        assertThat(response.getError(), is(notNullValue()));
        assertThat(response.isOk(), is(false));
    }

    @Test
    public void botsInfo() throws IOException, SlackApiException {
        String botId = findBotId();
        BotsInfoResponse response = slack.methods(botToken).botsInfo(req -> req.bot(botId));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getBot(), is(notNullValue()));
    }

    @Test
    public void botsInfo_async() throws Exception {
        String botId = findBotId();
        BotsInfoResponse response = slack.methodsAsync(botToken).botsInfo(req -> req.bot(botId)).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getBot(), is(notNullValue()));
    }

    @Test(expected = ExecutionException.class)
    public void botsInfo_async_error() throws Exception {
        String botId = findBotId();
        brokenSlack.methodsAsync(botToken).botsInfo(req -> req.bot(botId)).get();
    }

}