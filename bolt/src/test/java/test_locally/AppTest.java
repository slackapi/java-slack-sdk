package test_locally;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.OAuthStateService;
import com.slack.api.bolt.service.builtin.DefaultOAuthCallbackService;
import com.slack.api.bolt.service.builtin.FileInstallationService;
import com.slack.api.bolt.service.builtin.FileOAuthStateService;
import com.slack.api.bolt.service.builtin.oauth.OAuthAccessErrorHandler;
import com.slack.api.bolt.service.builtin.oauth.OAuthSuccessHandler;
import com.slack.api.bolt.service.builtin.oauth.OAuthV2AccessErrorHandler;
import com.slack.api.bolt.service.builtin.oauth.OAuthV2SuccessHandler;
import com.slack.api.bolt.service.builtin.oauth.default_impl.OAuthDefaultExceptionHandler;
import com.slack.api.bolt.service.builtin.oauth.default_impl.OAuthDefaultStateErrorHandler;
import com.slack.api.util.thread.DaemonThreadExecutorServiceFactory;
import com.slack.api.util.thread.ExecutorServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.AuthTestMockServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@Slf4j
public class AppTest {

    AuthTestMockServer server = new AuthTestMockServer();
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
    public void buildAuthorizeUrl_v1() {
        AppConfig config = AppConfig.builder()
                .signingSecret("secret")
                .clientId("123")
                .scope("commands,chat:write")
                .classicAppPermissionsEnabled(true)
                .build();
        App app = new App(config);
        String url = app.buildAuthorizeUrl("state-value");
        assertEquals("https://slack.com/oauth/authorize?client_id=123&scope=commands%2Cchat%3Awrite&state=state-value", url);
    }

    @Test
    public void buildAuthorizeUrl_v1_with_redirect_uri() {
        AppConfig config = AppConfig.builder()
                .signingSecret("secret")
                .clientId("123")
                .scope("commands,chat:write")
                .classicAppPermissionsEnabled(true)
                .redirectUri("https://my.app/oauth/callback")
                .build();
        App app = new App(config);
        String url = app.buildAuthorizeUrl("state-value");
        assertEquals("https://slack.com/oauth/authorize?client_id=123&scope=commands%2Cchat%3Awrite&state=state-value&redirect_uri=https%3A%2F%2Fmy.app%2Foauth%2Fcallback", url);
    }

    @Test
    public void buildAuthorizeUrl_v2() {
        AppConfig config = AppConfig.builder()
                .signingSecret("secret")
                .clientId("123")
                .scope("commands,chat:write")
                .userScope("search:read")
                .build();
        App app = new App(config);
        String url = app.buildAuthorizeUrl("state-value");
        assertEquals("https://slack.com/oauth/v2/authorize?client_id=123&scope=commands%2Cchat%3Awrite&user_scope=search%3Aread&state=state-value", url);
    }

    @Test
    public void buildAuthorizeUrl_v2_with_redirect_uri() {
        AppConfig config = AppConfig.builder()
                .signingSecret("secret")
                .clientId("123")
                .scope("commands,chat:write")
                .userScope("search:read")
                .redirectUri("https://my.app/oauth/callback")
                .build();
        App app = new App(config);
        String url = app.buildAuthorizeUrl("state-value");
        assertEquals("https://slack.com/oauth/v2/authorize?client_id=123&scope=commands%2Cchat%3Awrite&user_scope=search%3Aread&state=state-value&redirect_uri=https%3A%2F%2Fmy.app%2Foauth%2Fcallback", url);
    }

    @Test
    public void buildAuthorizeUrl_null() {
        App app = new App();
        String url = app.buildAuthorizeUrl("state-value");
        assertNull(url);
    }

    @Test
    public void buildAuthorizeUrl_bot_scope() {
        App app = new App(AppConfig.builder()
                .signingSecret("secret")
                .clientId("111.222")
                .clientSecret("secret")
                .scope("commands,chat:write")
                .build());
        String generatedUrl = app.buildAuthorizeUrl("state-value");
        assertEquals("https://slack.com/oauth/v2/authorize" +
                "?client_id=111.222&scope=commands%2Cchat%3Awrite&user_scope=&state=state-value", generatedUrl);
    }

    @Test
    public void buildAuthorizeUrl_bot_and_user_scope() {
        App app = new App(AppConfig.builder()
                .signingSecret("secret")
                .clientId("111.222")
                .clientSecret("secret")
                .scope("commands,chat:write")
                .userScope("search:read,chat:write")
                .build());
        String generatedUrl = app.buildAuthorizeUrl("state-value");
        assertEquals("https://slack.com/oauth/v2/authorize" +
                "?client_id=111.222&scope=commands%2Cchat%3Awrite&user_scope=search%3Aread%2Cchat%3Awrite&state=state-value", generatedUrl);
    }

    @Test
    public void buildAuthorizeUrl_user_scope() {
        App app = new App(AppConfig.builder()
                .signingSecret("secret")
                .clientId("111.222")
                .clientSecret("secret")
                .userScope("search:read,chat:write")
                .build());
        String generatedUrl = app.buildAuthorizeUrl("state-value");
        assertEquals("https://slack.com/oauth/v2/authorize?client_id=111.222&scope=&user_scope=search%3Aread%2Cchat%3Awrite&state=state-value", generatedUrl);
    }

    @Test
    public void buildAuthorizeUrl_OpenID_Connect() {
        App app = new App(AppConfig.builder()
                .openIDConnectEnabled(true)
                .signingSecret("secret")
                .clientId("111.222")
                .userScope("openid,email")
                .build());
        String generatedUrl = app.buildAuthorizeUrl("state-value");
        assertEquals("https://slack.com/openid/connect/authorize?client_id=111.222&response_type=code&scope=openid%2Cemail&state=state-value", generatedUrl);
    }

    @Test
    public void buildAuthorizeUrl_OpenID_Connect_invalid() {
        App app = new App(AppConfig.builder()
                .openIDConnectEnabled(true)
                .signingSecret("secret")
                .clientId("111.222")
                .scope("openid,email")
                .build());
        String generatedUrl = app.buildAuthorizeUrl("state-value");
        assertNull(generatedUrl);
    }

    @Test
    public void buildAuthorizeUrl_bot_scope_classic() {
        App app = new App(AppConfig.builder()
                .classicAppPermissionsEnabled(true)
                .signingSecret("secret")
                .clientId("111.222")
                .clientSecret("secret")
                .scope("commands,chat:write")
                .build());
        String generatedUrl = app.buildAuthorizeUrl("state-value");
        // use_scope= does not exist in the Slack OAuth v1
        assertEquals("https://slack.com/oauth/authorize" +
                "?client_id=111.222&scope=commands%2Cchat%3Awrite&state=state-value", generatedUrl);
    }

    @Test
    public void buildAuthorizeUrl_bot_and_user_scope_classic() {
        App app = new App(AppConfig.builder()
                .classicAppPermissionsEnabled(true)
                .signingSecret("secret")
                .clientId("111.222")
                .clientSecret("secret")
                .scope("commands,chat:write")
                .userScope("search:read,chat:write")
                .build());
        String generatedUrl = app.buildAuthorizeUrl("state-value");
        // use_scope= does not exist in the Slack OAuth v1
        assertEquals("https://slack.com/oauth/authorize" +
                "?client_id=111.222&scope=commands%2Cchat%3Awrite&state=state-value", generatedUrl);
    }

    @Test
    public void buildAuthorizeUrl_user_scope_classic() {
        App app = new App(AppConfig.builder()
                .classicAppPermissionsEnabled(true)
                .signingSecret("secret")
                .clientId("111.222")
                .clientSecret("secret")
                .userScope("search:read,chat:write")
                .build());
        String generatedUrl = app.buildAuthorizeUrl("state-value");
        // use_scope= does not exist in the Slack OAuth v1
        assertEquals("https://slack.com/oauth/authorize?client_id=111.222&scope=&state=state-value", generatedUrl);
    }

    @Test
    public void status() {
        App app = new App(AppConfig.builder()
                .signingSecret("secret")
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .slack(slack)
                .build());
        assertThat(app.status(), is(App.Status.Stopped));
    }

    @Test
    public void builder_status() {
        App app = new App(AppConfig.builder()
                .signingSecret("secret")
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .slack(slack)
                .build());
        assertNotNull(app.status());
        assertThat(app.status(), is(App.Status.Stopped));

        app = app.toBuilder().build();
        assertThat(app.status(), is(App.Status.Stopped));

        app = app.toOAuthCallbackApp();
        assertThat(app.status(), is(App.Status.Stopped));

        app = app.toOAuthStartApp();
        assertThat(app.status(), is(App.Status.Stopped));
    }

    @Test
    public void builder_config() {
        App app = new App(AppConfig.builder()
                .signingSecret("secret")
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .slack(slack)
                .build());
        assertNotNull(app.config());

        app = app.toBuilder().build();
        assertNotNull(app.config());

        app = app.toOAuthCallbackApp();
        assertNotNull(app.config());

        app = app.toOAuthStartApp();
        assertNotNull(app.config());
    }

    @Test
    public void initializer_called() {
        App app = new App(AppConfig.builder()
                .signingSecret("secret")
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .slack(slack)
                .build());
        final AtomicBoolean called = new AtomicBoolean(false);
        assertThat(called.get(), is(false));

        app.initializer("foo", (theApp) -> {
            called.set(true);
        });
        app.initialize();
        assertThat(called.get(), is(true));
    }

    @Test
    public void initializer_start() {
        App app = new App(AppConfig.builder()
                .signingSecret("secret")
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .slack(slack)
                .build());
        final AtomicBoolean called = new AtomicBoolean(false);
        assertThat(called.get(), is(false));

        app.initializer("foo", (theApp) -> {
            called.set(true);
        });
        app.start();
    }

    @Test
    public void initializer_same_key() {
        App app = new App(AppConfig.builder()
                .signingSecret("secret")
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .slack(slack)
                .build());
        final AtomicBoolean called = new AtomicBoolean(false);
        assertThat(called.get(), is(false));

        app.initializer("foo", (theApp) -> {
            called.set(true);
        });
        app.initializer("foo", (theApp) -> {
        });
        app.initialize();
        assertThat(called.get(), is(false));
    }

    @Test
    public void oauthMethods() {
        AppConfig config = new AppConfig();
        App app = new App();
        app.asOAuthApp(true);
        InstallationService installationService = new FileInstallationService(config);
        app.service(installationService);
        OAuthStateService stateService = new FileOAuthStateService(config);
        app.service(stateService);
        app.service(new DefaultOAuthCallbackService(
                config, stateService, null, null, null, null, null, null, null));

        app.oauthCallback((OAuthSuccessHandler) (request, response, oauthAccess) -> response);
        app.oauthCallback((OAuthV2SuccessHandler) (request, response, oauthAccess) -> response);

        app.oauthCallbackAccessError((OAuthAccessErrorHandler) (request, response, apiResponse) -> response);
        app.oauthCallbackAccessError((OAuthV2AccessErrorHandler) (request, response, apiResponse) -> response);

        app.oauthCallbackError((request, response) -> response);
        app.oauthCallbackException(new OAuthDefaultExceptionHandler(config) {
        });
        app.oauthCallbackStateError(new OAuthDefaultStateErrorHandler(config) {
        });
    }

    @Test
    public void clientConfig() {
        SlackConfig slackConfig = new SlackConfig();
        slackConfig.setMethodsEndpointUrlPrefix("http://localhost:8080/old");
        Slack slack = Slack.getInstance(slackConfig);
        AppConfig appConfig = AppConfig.builder().slack(slack).build();
        App app = new App(appConfig);

        assertThat(app.slack().getConfig().getMethodsEndpointUrlPrefix(),
                is("http://localhost:8080/old"));

        slackConfig.setMethodsEndpointUrlPrefix("http://localhost:8080/new");
        Slack newSlack = Slack.getInstance(slackConfig);
        appConfig.setSlack(newSlack);
        assertThat(app.slack().getConfig().getMethodsEndpointUrlPrefix(),
                is("http://localhost:8080/new"));
    }

    @Test
    public void customExecutorService() {
        final AtomicBoolean called = new AtomicBoolean(false);
        ExecutorServiceProvider executorServiceProvider = new ExecutorServiceProvider() {
            @Override
            public ExecutorService createThreadPoolExecutor(String threadGroupName, int poolSize) {
                called.set(true);
                return DaemonThreadExecutorServiceFactory.createDaemonThreadPoolExecutor(
                        threadGroupName, poolSize);
            }

            @Override
            public ScheduledExecutorService createThreadScheduledExecutor(String threadGroupName) {
                return DaemonThreadExecutorServiceFactory.createDaemonThreadScheduledExecutor(threadGroupName);
            }
        };
        App app = new App(AppConfig.builder()
                .signingSecret("secret")
                .singleTeamBotToken("token")
                .executorServiceProvider(executorServiceProvider)
                .build());
        assertThat(app.config().getExecutorServiceProvider(), is(executorServiceProvider));
        assertThat(called.get(), is(true));
    }

}
