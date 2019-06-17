package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.response.oauth.OAuthAccessResponse;
import com.github.seratch.jslack.api.methods.response.oauth.OAuthTokenResponse;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.ObjectInitializer;
import util.sample_json_generation.ObjectToJsonDumper;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class oauth_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());

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

}