package test_locally.middleware;

import com.slack.api.RequestConfigurator;
import com.slack.api.app_backend.events.payload.MessagePayload;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.middleware.MiddlewareChain;
import com.slack.api.bolt.middleware.builtin.SingleTeamAuthorization;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.EventRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.builtin.FileInstallationService;
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

public class SingleTeamAuthorizationTest {

    final MiddlewareChain chain = req -> Response.error(200);

    AppConfig config = new AppConfig();
    SingleTeamAuthorization middleware = new SingleTeamAuthorization(config, null);

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

        EventRequest req = new EventRequest(GsonFactory.createSnakeCase().toJson(payload), headers) {
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
    public void valid_plus_user_token() throws Exception {
        AppConfig config = new AppConfig();
        config.setAlwaysRequestUserTokenNeeded(true);
        InstallationService service = new FileInstallationService(config);
        SingleTeamAuthorization middleware = new SingleTeamAuthorization(config, service);

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

        EventRequest req = new EventRequest(GsonFactory.createSnakeCase().toJson(payload), headers) {
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
    public void valid_plus_user_token_without_InstallationService() throws Exception {
        AppConfig config = new AppConfig();
        config.setAlwaysRequestUserTokenNeeded(true);
        SingleTeamAuthorization middleware = new SingleTeamAuthorization(config, null);

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

        EventRequest req = new EventRequest(GsonFactory.createSnakeCase().toJson(payload), headers) {
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

        EventRequest req = new EventRequest(GsonFactory.createSnakeCase().toJson(payload), headers) {
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
}
