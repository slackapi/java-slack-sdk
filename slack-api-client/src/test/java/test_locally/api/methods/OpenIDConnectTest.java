package test_locally.api.methods;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectUserInfoResponse;
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

public class OpenIDConnectTest {

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
        assertThat(slack.methods(ValidToken).openIDConnectToken(r ->
                r.clientId("abc").clientSecret("xyz").code("xxx").redirectUri("https://www.example.com"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).openIDConnectUserInfo(r -> r)
                .isOk(), is(true));
    }

    static String token_response_json = "{\n" +
            "    \"ok\": true,\n" +
            "    \"access_token\": \"xoxp-1234\",\n" +
            "    \"token_type\": \"Bearer\",\n" +
            "    \"id_token\": \"eyJhbGcMjY5OTA2MzcWNrLmNvbVwvdGVhbV9p...\"\n" +
            "}";

    @Test
    public void token() {
        SlackTestConfig testConfig = SlackTestConfig.getInstance();
        Gson gson = GsonFactory.createSnakeCase(testConfig.getConfig());
        OpenIDConnectTokenResponse response = gson.fromJson(token_response_json, OpenIDConnectTokenResponse.class);
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.getAccessToken(), is("xoxp-1234"));
        assertThat(response.getTokenType(), is("Bearer"));
        assertThat(response.getIdToken(), is("eyJhbGcMjY5OTA2MzcWNrLmNvbVwvdGVhbV9p..."));
    }

    static String token_rotation_json = "{\n" +
            "    \"ok\": true,\n" +
            "    \"access_token\": \"xoxe.xoxp-1-xxx\",\n" +
            "    \"token_type\": \"Bearer\",\n" +
            "    \"refresh_token\": \"xoxe-1-xxx\",\n" +
            "    \"expires_in\": 1234,\n" +
            "    \"id_token\": \"eyJhbGcMjY5OTA2MzcWNrLmNvbVwvdGVhbV9p...\"\n" +
            "}";

    @Test
    public void token_rotation() {
        SlackTestConfig testConfig = SlackTestConfig.getInstance();
        Gson gson = GsonFactory.createSnakeCase(testConfig.getConfig());
        OpenIDConnectTokenResponse response = gson.fromJson(token_rotation_json, OpenIDConnectTokenResponse.class);
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.getAccessToken(), is("xoxe.xoxp-1-xxx"));
        assertThat(response.getTokenType(), is("Bearer"));
        assertThat(response.getRefreshToken(), is("xoxe-1-xxx"));
        assertThat(response.getExpiresIn(), is(1234));
        assertThat(response.getIdToken(), is("eyJhbGcMjY5OTA2MzcWNrLmNvbVwvdGVhbV9p..."));
    }

    String user_info_json = "{\n" +
            "  \"ok\": true,\n" +
            "  \"sub\": \"W1234567890\",\n" +
            "  \"https://slack.com/user_id\": \"W1234567890\",\n" +
            "  \"https://slack.com/team_id\": \"T1234567890\",\n" +
            "  \"https://slack.com/enterprise_id\": \"E1234567890\",\n" +
            "  \"email\": \"foos@example.com\",\n" +
            "  \"email_verified\": true,\n" +
            "  \"date_email_verified\": 1626075692,\n" +
            "  \"name\": \"Kazuhiro Sera\",\n" +
            "  \"picture\": \"https://avatars.slack-edge.com/xxx_512.jpg\",\n" +
            "  \"given_name\": \"Kazuhiro\",\n" +
            "  \"family_name\": \"Sera\",\n" +
            "  \"locale\": \"ja-JP\",\n" +
            "  \"https://slack.com/team_name\": \"Testing Workspace\",\n" +
            "  \"https://slack.com/team_domain\": \"testing-workspace\",\n" +
            "  \"https://slack.com/enterprise_name\": \"Sandbox Org\",\n" +
            "  \"https://slack.com/enterprise_domain\": \"sandbox-org\",\n" +
            "  \"https://slack.com/user_image_24\": \"https://avatars.slack-edge.com/xxx_24.jpg\",\n" +
            "  \"https://slack.com/user_image_32\": \"https://avatars.slack-edge.com/xxx_32.jpg\",\n" +
            "  \"https://slack.com/user_image_48\": \"https://avatars.slack-edge.com/xxx_48.jpg\",\n" +
            "  \"https://slack.com/user_image_72\": \"https://avatars.slack-edge.com/xxx_72.jpg\",\n" +
            "  \"https://slack.com/user_image_192\": \"https://avatars.slack-edge.com/xxx_192.jpg\",\n" +
            "  \"https://slack.com/user_image_512\": \"https://avatars.slack-edge.com/xxx_512.jpg\",\n" +
            "  \"https://slack.com/user_image_1024\": \"https://avatars.slack-edge.com/xxx_1024.jpg\",\n" +
            "  \"https://slack.com/team_image_34\": \"https://avatars.slack-edge.com/xxx_34.jpg\",\n" +
            "  \"https://slack.com/team_image_44\": \"https://avatars.slack-edge.com/xxx_44.jpg\",\n" +
            "  \"https://slack.com/team_image_68\": \"https://avatars.slack-edge.com/xxx_68.jpg\",\n" +
            "  \"https://slack.com/team_image_88\": \"https://avatars.slack-edge.com/xxx_88.jpg\",\n" +
            "  \"https://slack.com/team_image_102\": \"https://avatars.slack-edge.com/xxx_102.jpg\",\n" +
            "  \"https://slack.com/team_image_132\": \"https://avatars.slack-edge.com/xxx_132.jpg\",\n" +
            "  \"https://slack.com/team_image_230\": \"https://avatars.slack-edge.com/xxx_230.jpg\"\n" +
            "}";

    @Test
    public void userInfo() {
        SlackTestConfig testConfig = SlackTestConfig.getInstance();
        Gson gson = GsonFactory.createSnakeCase(testConfig.getConfig());
        OpenIDConnectUserInfoResponse response = gson.fromJson(user_info_json, OpenIDConnectUserInfoResponse.class);
        assertThat(response.getError(), is(nullValue()));
    }

}
