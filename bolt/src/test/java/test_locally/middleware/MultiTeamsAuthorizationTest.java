package test_locally.middleware;

import com.google.gson.Gson;
import com.slack.api.RequestConfigurator;
import com.slack.api.app_backend.events.payload.MessagePayload;
import com.slack.api.app_backend.events.payload.UrlVerificationPayload;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.middleware.MiddlewareChain;
import com.slack.api.bolt.middleware.builtin.MultiTeamsAuthorization;
import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.model.builtin.DefaultBot;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.EventRequest;
import com.slack.api.bolt.request.builtin.UrlVerificationRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.model.event.MessageEvent;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MultiTeamsAuthorizationTest {

    final Gson gson = GsonFactory.createSnakeCase();
    final MiddlewareChain chain = req -> Response.error(200);

    InstallationService installationService = new InstallationService() {
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
        }

        @Override
        public void deleteInstaller(Installer installer) throws Exception {
        }

        @Override
        public Bot findBot(String enterpriseId, String teamId) {
            Bot bot = new DefaultBot();
            bot.setTeamId("T123");
            bot.setBotAccessToken("xoxb-123-abc");
            return bot;
        }

        @Override
        public Installer findInstaller(String enterpriseId, String teamId, String userId) {
            return null;
        }
    };

    AppConfig config = new AppConfig();
    MultiTeamsAuthorization middleware = new MultiTeamsAuthorization(config, installationService);

    @Test
    public void valid() throws Exception {
        Map<String, List<String>> rawHeaders = new HashMap<>();
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        MessagePayload payload = new MessagePayload();
        payload.setType("message");
        payload.setTeamId("T123");
        MessageEvent event = new MessageEvent();
        event.setUser("U123BOT");
        event.setTs("123.123");
        event.setText("foo");
        payload.setEvent(event);

        MethodsClient client = mock(MethodsClient.class);
        AuthTestResponse authTestResult = new AuthTestResponse();
        authTestResult.setOk(true);
        when(client.authTest(any(RequestConfigurator.class))).thenReturn(authTestResult);

        EventRequest req = new EventRequest(gson.toJson(payload), headers) {
            @Override
            public EventContext getContext() {
                EventContext context = new EventContext() {
                    @Override
                    public MethodsClient client() {
                        return client;
                    }
                };
                return context;
            }
        };
        req.getContext().setBotToken("xoxb-123");
        req.getContext().setBotUserId("U123BOT");
        req.getContext().setRequestUserId("U123");
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(200L, result.getStatusCode().longValue());
    }

    @Test
    public void invalid() throws Exception {
        Map<String, List<String>> rawHeaders = new HashMap<>();
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        MessagePayload payload = new MessagePayload();
        payload.setType("message");
        payload.setTeamId("T123");
        MessageEvent event = new MessageEvent();
        event.setUser("U123BOT");
        event.setTs("123.123");
        event.setText("foo");
        payload.setEvent(event);

        MethodsClient client = mock(MethodsClient.class);
        AuthTestResponse authTestResult = new AuthTestResponse();
        authTestResult.setOk(false);
        authTestResult.setError("invalid");
        when(client.authTest(any(RequestConfigurator.class))).thenReturn(authTestResult);

        EventRequest req = new EventRequest(gson.toJson(payload), headers) {
            @Override
            public EventContext getContext() {
                EventContext context = new EventContext() {
                    @Override
                    public MethodsClient client() {
                        return client;
                    }
                };
                return context;
            }
        };
        req.getContext().setBotToken("xoxb-123");
        req.getContext().setBotUserId("U123BOT");
        req.getContext().setRequestUserId("U123");
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(401L, result.getStatusCode().longValue());
    }

    @Test
    public void skipped() throws Exception {
        Map<String, List<String>> rawHeaders = new HashMap<>();
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        UrlVerificationPayload payload = new UrlVerificationPayload();
        payload.setChallenge("challenge-value");
        UrlVerificationRequest req = new UrlVerificationRequest(gson.toJson(payload), headers);
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(200L, result.getStatusCode().longValue());
    }
}
