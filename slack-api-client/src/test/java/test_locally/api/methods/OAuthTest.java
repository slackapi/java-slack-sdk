package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class OAuthTest {

    MockSlackApiServer server = new MockSlackApiServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    @Before
    public void setup() throws Exception {
        server.start();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void test() throws Exception {
        assertThat(slack.methods(ValidToken).oauthAccess(r ->
                r.clientId("abc").clientSecret("xyz").code("xxx").redirectUri("https://www.example.com"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).oauthToken(r ->
                r.clientId("abc").clientSecret("xyz").code("xxx").redirectUri("https://www.example.com"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).oauthV2Access(r ->
                r.clientId("abc").clientSecret("xyz").code("xxx").redirectUri("https://www.example.com"))
                .isOk(), is(true));
    }

    @Test
    public void issue_474() throws Exception {
        assertThat(slack.methods(ValidToken).oauthAccess(r -> r.clientId("abc").clientSecret("xyz").code("xxx")).isOk(), is(true));
        assertThat(slack.methods(ValidToken).oauthToken(r -> r.clientId("abc").clientSecret("xyz").code("xxx")).isOk(), is(true));
        assertThat(slack.methods(ValidToken).oauthV2Access(r -> r.clientId("abc").clientSecret("xyz").code("xxx")).isOk(), is(true));
    }

    @Test
    public void test_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).oauthAccess(r ->
                r.clientId("abc").clientSecret("xyz").code("xxx").redirectUri("https://www.example.com"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).oauthToken(r ->
                r.clientId("abc").clientSecret("xyz").code("xxx").redirectUri("https://www.example.com"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).oauthV2Access(r ->
                r.clientId("abc").clientSecret("xyz").code("xxx").redirectUri("https://www.example.com"))
                .get().isOk(), is(true));
    }

}
