package test_locally.app;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.model.builtin.DefaultBot;
import com.slack.api.bolt.model.builtin.DefaultInstaller;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.EventRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApi;
import util.MockSlackApiServer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

@Slf4j
public class EventUninstallationTest {

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

    final String secret = "foo-bar-baz";
    final SlackSignature.Generator generator = new SlackSignature.Generator(secret);

    void setRequestHeaders(String requestBody, Map<String, List<String>> rawHeaders, String timestamp) {
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, Arrays.asList(timestamp));
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, Arrays.asList(generator.generate(timestamp, requestBody)));
    }

    String appUninstalledEvent = "{\n" +
            "    \"token\": \"fixed-value\",\n" +
            "    \"team_id\": \"T111\",\n" +
            "    \"enterprise_id\": \"E111\",\n" +
            "    \"api_app_id\": \"A111\",\n" +
            "    \"event\": {\n" +
            "        \"type\": \"app_uninstalled\",\n" +
            "        \"event_ts\": \"1614847360.124850\"\n" +
            "    },\n" +
            "    \"type\": \"event_callback\",\n" +
            "    \"event_id\": \"Ev111\",\n" +
            "    \"event_time\": 1614847360\n" +
            "}";

    @Test
    public void appUninstalled() throws Exception {
        AtomicBoolean botDeleted = new AtomicBoolean(false);
        AtomicBoolean userDeleted = new AtomicBoolean(false);
        AtomicBoolean allDeletion = new AtomicBoolean(false);
        App app = buildApp(botDeleted, userDeleted, allDeletion);
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(appUninstalledEvent, rawHeaders, timestamp);

        EventRequest req = new EventRequest(appUninstalledEvent, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());

        Thread.sleep(100L);
        assertFalse(botDeleted.get());
        assertFalse(userDeleted.get());
        assertTrue(allDeletion.get());
    }

    String userTokenRevokedEvent = "{\n" +
            "    \"token\": \"fixed-value\",\n" +
            "    \"team_id\": \"T111\",\n" +
            "    \"enterprise_id\": \"E111\",\n" +
            "    \"api_app_id\": \"A111\",\n" +
            "    \"event\": {\n" +
            "        \"type\": \"tokens_revoked\",\n" +
            "        \"tokens\": {\n" +
            "            \"oauth\": [\n" +
            "                \"W111\"\n" +
            "            ],\n" +
            "            \"bot\": []\n" +
            "        },\n" +
            "        \"event_ts\": \"1614847360.131900\"\n" +
            "    },\n" +
            "    \"type\": \"event_callback\",\n" +
            "    \"event_id\": \"Ev111\",\n" +
            "    \"event_time\": 1614847360\n" +
            "}";

    @Test
    public void tokensRevoked_users() throws Exception {
        AtomicBoolean botDeleted = new AtomicBoolean(false);
        AtomicBoolean userDeleted = new AtomicBoolean(false);
        AtomicBoolean allDeletion = new AtomicBoolean(false);
        App app = buildApp(botDeleted, userDeleted, allDeletion);
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(userTokenRevokedEvent, rawHeaders, timestamp);

        EventRequest req = new EventRequest(userTokenRevokedEvent, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());

        Thread.sleep(100L);
        assertFalse(botDeleted.get());
        assertTrue(userDeleted.get());
        assertFalse(allDeletion.get());
    }

    String botTokenRevokedEvent = "{\n" +
            "    \"token\": \"fixed-value\",\n" +
            "    \"team_id\": \"T111\",\n" +
            "    \"enterprise_id\": \"E111\",\n" +
            "    \"api_app_id\": \"A111\",\n" +
            "    \"event\": {\n" +
            "        \"type\": \"tokens_revoked\",\n" +
            "        \"tokens\": {\n" +
            "            \"oauth\": [],\n" +
            "            \"bot\": [\n" +
            "                \"W111\"\n" +
            "            ]\n" +
            "        },\n" +
            "        \"event_ts\": \"1614847360.131900\"\n" +
            "    },\n" +
            "    \"type\": \"event_callback\",\n" +
            "    \"event_id\": \"Ev111\",\n" +
            "    \"event_time\": 1614847360\n" +
            "}";

    @Test
    public void tokensRevoked_bots() throws Exception {
        AtomicBoolean botDeleted = new AtomicBoolean(false);
        AtomicBoolean userDeleted = new AtomicBoolean(false);
        AtomicBoolean allDeletion = new AtomicBoolean(false);
        App app = buildApp(botDeleted, userDeleted, allDeletion);
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(botTokenRevokedEvent, rawHeaders, timestamp);

        EventRequest req = new EventRequest(botTokenRevokedEvent, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());

        Thread.sleep(100L);
        assertTrue(botDeleted.get());
        assertFalse(userDeleted.get());
        assertFalse(allDeletion.get());
    }

    App buildApp(AtomicBoolean botDeletion, AtomicBoolean userDeletion, AtomicBoolean allDeletion) {
        App app = new App(AppConfig.builder()
                .signingSecret(secret)
                .clientId("111.222")
                .clientSecret("secret")
                .singleTeamBotToken(null)
                .slack(slack)
                .build());
        app.service(new InstallationService() {
            @Override
            public boolean isHistoricalDataEnabled() {
                return false;
            }

            @Override
            public void setHistoricalDataEnabled(boolean isHistoricalDataEnabled) {

            }

            @Override
            public void saveInstallerAndBot(Installer installer) throws Exception {

            }

            @Override
            public void deleteBot(Bot bot) throws Exception {
                botDeletion.set(true);
            }

            @Override
            public void deleteInstaller(Installer installer) throws Exception {
                userDeletion.set(true);
            }

            @Override
            public Bot findBot(String enterpriseId, String teamId) {
                DefaultBot bot = new DefaultBot();
                bot.setBotAccessToken(MockSlackApi.ValidToken);
                return bot;
            }

            @Override
            public Installer findInstaller(String enterpriseId, String teamId, String userId) {
                DefaultInstaller installer = new DefaultInstaller();
                installer.setInstallerUserAccessToken(MockSlackApi.ValidToken);
                return installer;
            }

            @Override
            public void deleteAll(String enterpriseId, String teamId) {
                allDeletion.set(true);
            }
        });
        // enable tokens_revoked & app_uninstalled event handlers
        app.enableTokenRevocationHandlers();

        return app;
    }

}
