package test_locally.api.methods;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;
import com.slack.api.util.json.GsonFactory;
import config.SlackTestConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
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

    String oauth_v2_access_token_rotation_json = "{\n" +
            "  \"ok\": true,\n" +
            "  \"app_id\": \"A111\",\n" +
            "  \"authed_user\": {\n" +
            "    \"id\": \"W111\",\n" +
            "    \"scope\": \"search:read\",\n" +
            "    \"access_token\": \"xoxe.xoxp-1-xxx\",\n" +
            "    \"token_type\": \"user\",\n" +
            "    \"refresh_token\": \"xoxe-1-xxx\",\n" +
            "    \"expires_in\": 43200\n" +
            "  },\n" +
            "  \"scope\": \"app_mentions:read,chat:write,commands\",\n" +
            "  \"token_type\": \"bot\",\n" +
            "  \"access_token\": \"xoxe.xoxb-1-yyy\",\n" +
            "  \"bot_user_id\": \"UB111\",\n" +
            "  \"refresh_token\": \"xoxe-1-yyy\",\n" +
            "  \"expires_in\": 43201,\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T111\",\n" +
            "    \"name\": \"Testing Workspace\"\n" +
            "  },\n" +
            "  \"enterprise\": {\n" +
            "    \"id\": \"E111\",\n" +
            "    \"name\": \"Sandbox Org\"\n" +
            "  },\n" +
            "  \"is_enterprise_install\": false\n" +
            "}";

    @Test
    public void token_rotation() {
        SlackTestConfig testConfig = SlackTestConfig.getInstance();
        Gson gson = GsonFactory.createSnakeCase(testConfig.getConfig());
        OAuthV2AccessResponse response = gson.fromJson(oauth_v2_access_token_rotation_json, OAuthV2AccessResponse.class);
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.getRefreshToken(), is("xoxe-1-yyy"));
        assertThat(response.getExpiresIn(), is(43201));
        assertThat(response.getAuthedUser().getRefreshToken(), is("xoxe-1-xxx"));
        assertThat(response.getAuthedUser().getExpiresIn(), is(43200));
    }

}
