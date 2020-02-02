package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;
import com.slack.api.methods.response.oauth.OAuthTokenResponse;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;
import util.ObjectInitializer;
import util.sample_json_generation.ObjectToJsonDumper;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class oauth_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    // TODO: valid test; currently just checking the API access
    @Test
    public void access() throws IOException, SlackApiException {
        {
            OAuthAccessResponse response = slack.methods().oauthAccess(r -> r
                    .clientId("3485157640.XXXX")
                    .clientSecret("XXXXX")
                    .code("")
                    .redirectUri("http://seratch.net/foo"));
            assertThat(response.getError(), is("invalid_code"));
            assertThat(response.isOk(), is(false));
        }
    }

    @Test
    public void accessV2() throws IOException, SlackApiException {
        {
            OAuthV2AccessResponse response = slack.methods().oauthV2Access(r -> r
                    .clientId("3485157640.XXXX")
                    .clientSecret("XXXXX")
                    .code("")
                    .redirectUri("http://seratch.net/foo"));
            assertThat(response.getError(), is("invalid_code"));
            assertThat(response.isOk(), is(false));
        }
    }

    // TODO: valid test; currently just checking the API access
    @Test
    public void token() throws IOException, SlackApiException {
        {
            OAuthTokenResponse response = slack.methods().oauthToken(r -> r
                    .clientId("3485157640.XXXX")
                    .clientSecret("XXXXX")
                    .code("")
                    .redirectUri("http://seratch.net/foo"));
            assertThat(response.getError(), is("invalid_code"));
            assertThat(response.isOk(), is(false));
        }
    }

    @Test
    public void dumpFullJson() throws IOException {
        ObjectToJsonDumper dumper = new ObjectToJsonDumper("../json-logs/samples/api");
        OAuthAccessResponse response = new OAuthAccessResponse();
        response.setBot(new OAuthAccessResponse.Bot());
        OAuthAccessResponse.Scopes scopes = new OAuthAccessResponse.Scopes();
        scopes.setAppHome(Arrays.asList(""));
        scopes.setTeam(Arrays.asList(""));
        scopes.setChannel(Arrays.asList(""));
        scopes.setGroup(Arrays.asList(""));
        scopes.setIm(Arrays.asList(""));
        scopes.setMpim(Arrays.asList(""));
        scopes.setUser(Arrays.asList(""));
        response.setScopes(scopes);
        response.setAuthorizingUser(new OAuthAccessResponse.AuthorizingUser());
        response.setIncomingWebhook(new OAuthAccessResponse.IncomingWebhook());
        response.setInstallerUser(new OAuthAccessResponse.InstallerUser());
        ObjectInitializer.initProperties(response);
        dumper.dump("oauth.access", response);
    }

    @Test
    public void dumpFullJsonV2() throws IOException {
        ObjectToJsonDumper dumper = new ObjectToJsonDumper("../json-logs/samples/api");
        OAuthV2AccessResponse response = new OAuthV2AccessResponse();
        response.setAuthedUser(new OAuthV2AccessResponse.AuthedUser());
        response.setIncomingWebhook(new OAuthV2AccessResponse.IncomingWebhook());
        response.setEnterprise(new OAuthV2AccessResponse.Enterprise());
        response.setTeam(new OAuthV2AccessResponse.Team());
        ObjectInitializer.initProperties(response);
        dumper.dump("oauth.v2.access", response);
    }

}
