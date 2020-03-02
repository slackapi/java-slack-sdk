package test_locally.service;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.OAuthStateService;
import com.slack.api.bolt.service.builtin.ClientOnlyOAuthStateService;
import com.slack.api.bolt.service.builtin.DefaultOAuthCallbackService;
import com.slack.api.bolt.service.builtin.FileInstallationService;
import com.slack.api.bolt.service.builtin.oauth.*;
import com.slack.api.bolt.service.builtin.oauth.default_impl.*;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DefaultOAuthCallbackServiceTest {

    Slack slack = Slack.getInstance();

    MockSlackApiServer slackApiServer = new MockSlackApiServer();

    AppConfig appConfig(Slack slack) {
        return AppConfig.builder()
                .slack(slack)
                .clientId("123.123")
                .clientSecret("secret")
                .redirectUri("https://www.example.com/callback")
                .build();
    }

    AppConfig classicAppConfig(Slack slack) {
        return AppConfig.builder()
                .slack(slack)
                .clientId("123.123")
                .clientSecret("secret")
                .redirectUri("https://www.example.com/callback")
                .classicAppPermissionsEnabled(true)
                .build();
    }

    InstallationService installationService = new FileInstallationService(AppConfig.builder().build(), "target/files");
    OAuthStateService stateService = new ClientOnlyOAuthStateService();
    OAuthSuccessHandler successHandler = new OAuthDefaultSuccessHandler(installationService);
    OAuthV2SuccessHandler successV2Handler = new OAuthV2DefaultSuccessHandler(installationService);
    OAuthErrorHandler errorHandler = new OAuthDefaultErrorHandler();
    OAuthStateErrorHandler stateErrorHandler = new OAuthDefaultStateErrorHandler();
    OAuthAccessErrorHandler accessErrorHandler = new OAuthDefaultAccessErrorHandler();
    OAuthV2AccessErrorHandler accessV2ErrorHandler = new OAuthV2DefaultAccessErrorHandler();
    OAuthExceptionHandler exceptionHandler = new OAuthDefaultExceptionHandler();

    DefaultOAuthCallbackService service(Slack slack) {
        return new DefaultOAuthCallbackService(
                appConfig(slack),
                stateService,
                successHandler,
                successV2Handler,
                errorHandler,
                stateErrorHandler,
                accessErrorHandler,
                accessV2ErrorHandler,
                exceptionHandler
        );
    }

    DefaultOAuthCallbackService classicAppService(Slack slack) {
        return new DefaultOAuthCallbackService(
                classicAppConfig(slack),
                stateService,
                successHandler,
                successV2Handler,
                errorHandler,
                stateErrorHandler,
                accessErrorHandler,
                accessV2ErrorHandler,
                exceptionHandler
        );
    }

    private Map<String, List<String>> buildMultiValueMap(String key, String firstValue) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put(key, Arrays.asList(firstValue));
        return headers;
    }

    private Map<String, List<String>> buildValidQueryString(String state) {
        Map<String, List<String>> query = buildMultiValueMap("state", state);
        query.put("code", Arrays.asList("valid"));
        return query;
    }

    private VerificationCodePayload buildValidPayload(String state) {
        VerificationCodePayload payload = new VerificationCodePayload();
        payload.setState(state);
        payload.setCode("valid");
        return payload;
    }

    @Test
    public void null_payload() {
        OAuthCallbackRequest request = new OAuthCallbackRequest(null, null, null, null);
        request.getContext().setOauthCancellationUrl("expected");
        Response response = service(slack).handle(request);
        assertNotNull(response);
        assertEquals(302L, response.getStatusCode().longValue());
        assertEquals("expected", response.getHeaders().get("Location").get(0));
    }

    @Test
    public void error() {
        Map<String, List<String>> query = buildMultiValueMap("error", "something_wrong");
        VerificationCodePayload payload = new VerificationCodePayload();
        payload.setError("something_wrong");
        OAuthCallbackRequest request = new OAuthCallbackRequest(query, null, payload, null);
        request.getContext().setOauthCancellationUrl("expected");
        Response response = service(slack).handle(request);
        assertNotNull(response);
        assertEquals(302L, response.getStatusCode().longValue());
        assertEquals("expected", response.getHeaders().get("Location").get(0));
    }

    @Test
    public void invalid_state() {
        Map<String, List<String>> query = buildValidQueryString("foo");
        VerificationCodePayload payload = buildValidPayload("foo");
        Map<String, List<String>> headers = buildMultiValueMap("Cookie", stateService.getSessionCookieName() + "=foo");
        OAuthCallbackRequest request = new OAuthCallbackRequest(query, null, payload, new RequestHeaders(headers));
        request.getContext().setOauthCancellationUrl("expected");
        Response response = service(slack).handle(request);
        assertNotNull(response);
        assertEquals(302L, response.getStatusCode().longValue());
        assertEquals("expected", response.getHeaders().get("Location").get(0));
    }

    @Test
    public void classicApp() throws Exception {
        OAuthCallbackRequest dummy = new OAuthCallbackRequest(null, null, null, null);
        String state = stateService.issueNewState(dummy, Response.builder().build());

        Map<String, List<String>> query = buildValidQueryString(state);
        VerificationCodePayload payload = buildValidPayload(state);
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Cookie", Arrays.asList(stateService.getSessionCookieName() + "=" + state));
        OAuthCallbackRequest request = new OAuthCallbackRequest(query, null, payload, new RequestHeaders(headers));
        request.getContext().setOauthCompletionUrl("expected");

        slackApiServer.start();

        try {
            SlackConfig config = new SlackConfig();
            config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
            Slack mockSlack = Slack.getInstance(config);
            AppConfig appConfig = new AppConfig();
            appConfig.setSlack(mockSlack);
            request.updateContext(appConfig);

            Response response = classicAppService(mockSlack).handle(request);

            assertNotNull(response);
            assertEquals(302L, response.getStatusCode().longValue());
            assertEquals("expected", response.getHeaders().get("Location").get(0));

        } finally {
            slackApiServer.stop();
        }
    }

    @Test
    public void classicApp_error() throws Exception {
        OAuthCallbackRequest dummy = new OAuthCallbackRequest(null, null, null, null);
        String state = stateService.issueNewState(dummy, Response.builder().build());

        Map<String, List<String>> query = buildValidQueryString(state);
        query.put("code", Arrays.asList("invalid"));
        VerificationCodePayload payload = buildValidPayload(state);
        payload.setCode("invalid");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Cookie", Arrays.asList(stateService.getSessionCookieName() + "=" + state));
        OAuthCallbackRequest request = new OAuthCallbackRequest(query, null, payload, new RequestHeaders(headers));
        request.getContext().setOauthCancellationUrl("expected");

        slackApiServer.start();

        try {
            SlackConfig config = new SlackConfig();
            config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
            Slack mockSlack = Slack.getInstance(config);
            AppConfig appConfig = new AppConfig();
            appConfig.setSlack(mockSlack);
            request.updateContext(appConfig);

            Response response = classicAppService(mockSlack).handle(request);

            assertNotNull(response);
            assertEquals(302L, response.getStatusCode().longValue());
            assertEquals("expected", response.getHeaders().get("Location").get(0));

        } finally {
            slackApiServer.stop();
        }
    }

    @Test
    public void app() throws Exception {
        OAuthCallbackRequest dummy = new OAuthCallbackRequest(null, null, null, null);
        String state = stateService.issueNewState(dummy, Response.builder().build());

        Map<String, List<String>> query = buildValidQueryString(state);
        VerificationCodePayload payload = buildValidPayload(state);
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Cookie", Arrays.asList(stateService.getSessionCookieName() + "=" + state));
        OAuthCallbackRequest request = new OAuthCallbackRequest(query, null, payload, new RequestHeaders(headers));
        request.getContext().setOauthCompletionUrl("expected");

        slackApiServer.start();

        try {
            SlackConfig config = new SlackConfig();
            config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
            Slack mockSlack = Slack.getInstance(config);
            AppConfig appConfig = new AppConfig();
            appConfig.setSlack(mockSlack);
            request.updateContext(appConfig);

            Response response = service(mockSlack).handle(request);

            assertNotNull(response);
            assertEquals(302L, response.getStatusCode().longValue());
            assertEquals("expected", response.getHeaders().get("Location").get(0));

        } finally {
            slackApiServer.stop();
        }
    }

    @Test
    public void app_error() throws Exception {
        OAuthCallbackRequest dummy = new OAuthCallbackRequest(null, null, null, null);
        String state = stateService.issueNewState(dummy, Response.builder().build());

        Map<String, List<String>> query = buildValidQueryString(state);
        query.put("code", Arrays.asList("invalid"));
        VerificationCodePayload payload = buildValidPayload(state);
        payload.setCode("invalid");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Cookie", Arrays.asList(stateService.getSessionCookieName() + "=" + state));
        OAuthCallbackRequest request = new OAuthCallbackRequest(query, null, payload, new RequestHeaders(headers));
        request.getContext().setOauthCancellationUrl("expected");

        slackApiServer.start();

        try {
            SlackConfig config = new SlackConfig();
            config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
            Slack mockSlack = Slack.getInstance(config);
            AppConfig appConfig = new AppConfig();
            appConfig.setSlack(mockSlack);
            request.updateContext(appConfig);

            Response response = service(mockSlack).handle(request);

            assertNotNull(response);
            assertEquals(302L, response.getStatusCode().longValue());
            assertEquals("expected", response.getHeaders().get("Location").get(0));

        } finally {
            slackApiServer.stop();
        }
    }
}
