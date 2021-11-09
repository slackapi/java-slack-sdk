package test_locally.app;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.builtin.ClientOnlyOAuthStateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class OAuthCallbacksTest {

    MockSlackApiServer server = new MockSlackApiServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    String fixedStateValue = "generated-state-value";
    ClientOnlyOAuthStateService stateService = new ClientOnlyOAuthStateService() {
        @Override
        public String issueNewState(Request request, Response response) {
            return fixedStateValue;
        }
    };

    InstallationService brokenInstallationService = new InstallationService() {
        @Override
        public boolean isHistoricalDataEnabled() {
            return false;
        }

        @Override
        public void setHistoricalDataEnabled(boolean isHistoricalDataEnabled) {
        }

        @Override
        public void saveInstallerAndBot(Installer installer) {
            throw new RuntimeException("Something is wrong!");
        }

        @Override
        public void deleteBot(Bot bot) {
        }

        @Override
        public void deleteInstaller(Installer installer) {
        }

        @Override
        public Bot findBot(String enterpriseId, String teamId) {
            return null;
        }

        @Override
        public Installer findInstaller(String enterpriseId, String teamId, String userId) {
            return null;
        }
    };

    @Before
    public void setup() throws Exception {
        server.start();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    OAuthCallbackRequest buildOAuthCallbackRequest(String code) {
        // query string data in /slack/oauth_redirect access
        Map<String, List<String>> queryString = new HashMap<>();
        queryString.put("state", Arrays.asList(fixedStateValue));
        queryString.put("code", Arrays.asList(code));

        // request headers in /slack/oauth_redirect access
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Cookie", Arrays.asList(stateService.getSessionCookieName() + "=" + fixedStateValue));

        return new OAuthCallbackRequest(
                queryString,
                "",
                VerificationCodePayload.from(queryString),
                new RequestHeaders(headers)
        );
    }

    @Test
    public void oauthPersistenceCallback() throws Exception {
        App app = new App(AppConfig.builder()
                .slack(slack)
                .signingSecret("secret")
                .clientId("111.222")
                .clientSecret("secret")
                .scope("commands,chat:write")
                .build()
        ).asOAuthApp(true);
        app.service(stateService);
        app.initialize();

        // Testing this here
        Integer expectedStatusCode = 201;
        app.oauthPersistenceCallback(args -> args.getResponse().setStatusCode(expectedStatusCode));
        app.oauthPersistenceErrorCallback(args -> args.getResponse().setStatusCode(404));

        OAuthCallbackRequest req = buildOAuthCallbackRequest("valid");
        Response response = app.run(req);
        assertThat(response.getStatusCode(), is(expectedStatusCode));
    }

    @Test
    public void oauthPersistenceErrorCallback() throws Exception {
        App app = new App(AppConfig.builder()
                .slack(slack)
                .signingSecret("secret")
                .clientId("111.222")
                .clientSecret("secret")
                .scope("commands,chat:write")
                .build()
        ).asOAuthApp(true);
        app.service(stateService);
        app.service(brokenInstallationService);
        app.initialize();

        Integer expectedStatusCode = 202;
        app.oauthPersistenceCallback(args -> args.getResponse().setStatusCode(201));
        // Testing this here
        app.oauthPersistenceErrorCallback(args -> args.getResponse().setStatusCode(expectedStatusCode));

        OAuthCallbackRequest req = buildOAuthCallbackRequest("valid");
        Response response = app.run(req);
        assertThat(response.getStatusCode(), is(expectedStatusCode));
    }
}
