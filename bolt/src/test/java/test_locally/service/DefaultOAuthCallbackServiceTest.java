package test_locally.service;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.request.builtin.OAuthStartRequest;
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

    AppConfig appConfig = new AppConfig();
    InstallationService installationService = new FileInstallationService(appConfig, "target/files");
    OAuthStateService stateService = new ClientOnlyOAuthStateService();
    OAuthSuccessHandler successHandler = new OAuthDefaultSuccessHandler(appConfig, installationService);
    OAuthV2SuccessHandler successV2Handler = new OAuthV2DefaultSuccessHandler(appConfig, installationService);
    OAuthErrorHandler errorHandler = new OAuthDefaultErrorHandler(appConfig);
    OAuthStateErrorHandler stateErrorHandler = new OAuthDefaultStateErrorHandler(appConfig);
    OAuthAccessErrorHandler accessErrorHandler = new OAuthDefaultAccessErrorHandler(appConfig);
    OAuthV2AccessErrorHandler accessV2ErrorHandler = new OAuthV2DefaultAccessErrorHandler(appConfig);
    OAuthExceptionHandler exceptionHandler = new OAuthDefaultExceptionHandler(appConfig);

    DefaultOAuthCallbackService service(Slack slack) {
        return service(appConfig(slack));
    }

    DefaultOAuthCallbackService service(AppConfig config) {
        return new DefaultOAuthCallbackService(
                config,
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
        Response response = service(slack).handle(request);
        assertNotNull(response);
        assertEquals(200L, response.getStatusCode().longValue());
        assertEquals("text/html; charset=utf-8", response.getContentType());
        assertEquals("<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "body {\n" +
                "  padding: 10px 15px;\n" +
                "  font-family: verdana;\n" +
                "  text-align: center;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h2>Oops, Something Went Wrong!</h2>\n" +
                "<p>Please try again from <a href=\"start\">here</a> or contact the app owner (reason: something_wrong)</p>\n" +
                "</body>\n" +
                "</html>", response.getBody());
    }

    @Test
    public void error_with_cancellation_url() {
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
        Map<String, List<String>> query = buildValidQueryString("foo-bar");
        VerificationCodePayload payload = buildValidPayload("foo");
        Map<String, List<String>> headers = buildMultiValueMap("Cookie", stateService.getSessionCookieName() + "=foo");
        OAuthCallbackRequest request = new OAuthCallbackRequest(query, null, payload, new RequestHeaders(headers));
        Response response = service(slack).handle(request);
        assertNotNull(response);
        assertEquals(200L, response.getStatusCode().longValue());
        assertEquals("text/html; charset=utf-8", response.getContentType());
        assertEquals("<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "body {\n" +
                "  padding: 10px 15px;\n" +
                "  font-family: verdana;\n" +
                "  text-align: center;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h2>Oops, Something Went Wrong!</h2>\n" +
                "<p>Please try again from <a href=\"start\">here</a> or contact the app owner (reason: invalid_state)</p>\n" +
                "</body>\n" +
                "</html>", response.getBody());
    }

    @Test
    public void state_validation_disabled() throws Exception {
        // intentionally setting an invalid state value here
        Map<String, List<String>> query = buildValidQueryString("xxx");
        VerificationCodePayload payload = buildValidPayload("xxx");
        Map<String, List<String>> headers = new HashMap<>();
        OAuthCallbackRequest request = new OAuthCallbackRequest(query, null, payload, new RequestHeaders(headers));
        request.getContext().setOauthCompletionUrl("expected");

        slackApiServer.start();

        try {
            SlackConfig config = new SlackConfig();
            config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
            Slack mockSlack = Slack.getInstance(config);
            AppConfig appConfig = appConfig(mockSlack);
            appConfig.setStateValidationEnabled(false); // disabled
            request.updateContext(appConfig);

            Response response = service(appConfig).handle(request);

            assertNotNull(response);
            assertEquals(302L, response.getStatusCode().longValue());
            assertEquals("expected", response.getHeaders().get("Location").get(0));

        } finally {
            slackApiServer.stop();
        }
    }

    @Test
    public void invalid_state_with_cancellation_url() {
        Map<String, List<String>> query = buildValidQueryString("foo-bar");
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
        OAuthStartRequest testReq = new OAuthStartRequest(null, null);
        String state = stateService.issueNewState(testReq, Response.builder().build());

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
        OAuthStartRequest testReq = new OAuthStartRequest(null, null);
        String state = stateService.issueNewState(testReq, Response.builder().build());

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
        OAuthStartRequest testReq = new OAuthStartRequest(null, null);
        String state = stateService.issueNewState(testReq, Response.builder().build());

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
        OAuthStartRequest testReq = new OAuthStartRequest(null, null);
        String state = stateService.issueNewState(testReq, Response.builder().build());

        Map<String, List<String>> query = buildValidQueryString(state);
        query.put("code", Arrays.asList("invalid"));
        VerificationCodePayload payload = buildValidPayload(state);
        payload.setCode("invalid");
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Cookie", Arrays.asList(stateService.getSessionCookieName() + "=" + state));
        OAuthCallbackRequest request = new OAuthCallbackRequest(query, null, payload, new RequestHeaders(headers));

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
            assertEquals(200L, response.getStatusCode().longValue());
            assertEquals("text/html; charset=utf-8", response.getContentType());
            assertEquals("<html>\n" +
                    "<head>\n" +
                    "<style>\n" +
                    "body {\n" +
                    "  padding: 10px 15px;\n" +
                    "  font-family: verdana;\n" +
                    "  text-align: center;\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<h2>Oops, Something Went Wrong!</h2>\n" +
                    "<p>Please try again from <a href=\"start\">here</a> or contact the app owner (reason: something-wrong)</p>\n" +
                    "</body>\n" +
                    "</html>", response.getBody());

        } finally {
            slackApiServer.stop();
        }
    }

    @Test
    public void app_error_with_cancellation_url() throws Exception {
        OAuthStartRequest testReq = new OAuthStartRequest(null, null);
        String state = stateService.issueNewState(testReq, Response.builder().build());

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
