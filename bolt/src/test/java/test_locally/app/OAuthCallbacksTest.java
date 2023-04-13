package test_locally.app;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.model.builtin.DefaultInstaller;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.builtin.ClientOnlyOAuthStateService;
import com.slack.api.bolt.service.builtin.oauth.view.OAuthRedirectUriPageRenderer;
import com.slack.api.bolt.service.builtin.oauth.view.default_impl.OAuthDefaultRedirectUriPageRenderer;
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

    @Test
    public void testDefaultRenderer_renderSuccessPage() {
        OAuthRedirectUriPageRenderer renderer = new OAuthDefaultRedirectUriPageRenderer();
        {
            Installer installer = new DefaultInstaller();
            installer.setAppId("A123");
            installer.setTeamId("T123");
            String page = renderer.renderSuccessPage(installer, "https://www.example.com/?foo=bar&baz=123");
            assertThat(page.contains("https://www.example.com/?foo=bar&baz=123"), is(false));
            assertThat(page.contains("<meta http-equiv=\"refresh\" content=\"0; URL=https://www.example.com/?foo=bar&amp;baz=123\">"), is(true));
            assertThat(page.contains("<a href=\"https://www.example.com/?foo=bar&amp;baz=123\">here</a>"), is(true));
            assertThat(page.contains("<a href=\"https://app.slack.com/client/T123\" target=\"_blank\">"), is(true));
        }
        {
            Installer installer = new DefaultInstaller();
            installer.setAppId(null);
            installer.setTeamId(null);
            String page = renderer.renderSuccessPage(installer, null);
            assertThat(page.contains("<meta http-equiv=\"refresh\" content=\"0; URL=slack://open\">"), is(true));
            assertThat(page.contains("<a href=\"slack://open\">here</a>"), is(true));
            assertThat(page.contains("<a href=\"https://slack.com/\" target=\"_blank\">"), is(true));
        }
        {
            Installer installer = new DefaultInstaller();
            installer.setAppId("A123");
            installer.setTeamId("T123");
            String page = renderer.renderSuccessPage(installer, null);
            assertThat(page.contains("slack://app?team=T123&id=A123"), is(false));
            assertThat(page.contains("<meta http-equiv=\"refresh\" content=\"0; URL=slack://app?team=T123&amp;id=A123\">"), is(true));
            assertThat(page.contains("<a href=\"slack://app?team=T123&amp;id=A123\">here</a>"), is(true));
            assertThat(page.contains("<a href=\"https://app.slack.com/client/T123\" target=\"_blank\">"), is(true));
        }
        {
            Installer installer = new DefaultInstaller();
            installer.setAppId("A123");
            installer.setTeamId("T123");
            installer.setEnterpriseId("E123");
            installer.setIsEnterpriseInstall(true);
            installer.setEnterpriseUrl("https://test.enterprise.slack.com/");
            String page = renderer.renderSuccessPage(installer, null);
            assertThat(page.contains("<meta http-equiv=\"refresh\" content=\"0; URL=https://test.enterprise.slack.com/manage/organization/apps/profile/A123/workspaces/add\">"), is(true));
            assertThat(page.contains("<a href=\"https://test.enterprise.slack.com/manage/organization/apps/profile/A123/workspaces/add\">here</a>"), is(true));
            assertThat(page.contains("<a href=\"https://app.slack.com/client/T123\" target=\"_blank\">"), is(true));
        }
    }

    @Test
    public void testDefaultRenderer_renderFailurePage() {
        OAuthRedirectUriPageRenderer renderer = new OAuthDefaultRedirectUriPageRenderer();
        {
            String page = renderer.renderFailurePage("/slack/install", "access_denied");
            assertThat(page.contains("<a href=\"/slack/install\">here</a> or contact the app owner (reason: access_denied)"), is(true));
        }
        {
            String page = renderer.renderFailurePage("/slack/install&team=T123&foo=bar", "<b>test</b>");
            assertThat(page.contains("/slack/install&team=T123&foo=bar"), is(false));
            assertThat(page.contains("<b>test</b>"), is(false));
            assertThat(page.contains("<a href=\"/slack/install&amp;team=T123&amp;foo=bar\">here</a> or contact the app owner (reason: &lt;b&gt;test&lt;/b&gt;)"), is(true));
        }
    }
}
