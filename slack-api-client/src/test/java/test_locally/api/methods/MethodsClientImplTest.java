package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.Methods;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.api.ApiTestResponse;
import com.slack.api.methods.response.conversations.ConversationsInfoResponse;
import com.slack.api.util.http.listener.HttpResponseListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApi;
import util.MockSlackApiServer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static util.MockSlackApi.ValidToken;

public class MethodsClientImplTest {

    private volatile String responseBody;

    MockSlackApiServer slackApiServer = new MockSlackApiServer(new MockSlackApi() {
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            if (responseBody == null) {
                super.doPost(request, response);
            } else {
                response.setStatus(200);
                response.setContentType("application/json");
                response.setHeader("X-Slack-Req-Id", "null-response-test");
                response.getWriter().write(responseBody);
            }
        }
    });

    @Before
    public void setup() throws Exception {
        slackApiServer.start();
    }

    @After
    public void teamDown() throws Exception {
        slackApiServer.stop();
    }

    @Test
    public void postFormAndParseResponse() throws IOException, SlackApiException {
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
        MethodsClient client = Slack.getInstance(config).methods();
        ApiTestResponse response = client.postFormAndParseResponse(
                form -> form.add("foo", "bar"),
                Methods.API_TEST,
                ApiTestResponse.class
        );
        assertTrue(response.isOk());
    }

    @Test(expected = IOException.class)
    public void postFormAndParseResponse_IOException() throws IOException, SlackApiException {
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix().replaceFirst(":\\d+/api/$", ":7777/api/")); // invalid
        MethodsClient client = Slack.getInstance(config).methods();
        client.postFormAndParseResponse(
                form -> form.add("foo", "bar"),
                Methods.API_TEST,
                ApiTestResponse.class
        );
    }

    @Test
    public void postFormWithAuthorizationHeaderAndParseResponse() throws IOException, SlackApiException {
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
        MethodsClient client = Slack.getInstance(config).methods();
        ConversationsInfoResponse response = client.postFormWithAuthorizationHeaderAndParseResponse(
                form -> form.add("foo", "bar"),
                client.getEndpointUrlPrefix() + Methods.CONVERSATIONS_INFO,
                "Bearer " + ValidToken,
                ConversationsInfoResponse.class
        );
        assertTrue(response.isOk());
        assertNotNull(response.getChannel());
    }

    @Test(expected = IOException.class)
    public void postFormWithAuthorizationHeaderAndParseResponse_IOException() throws IOException, SlackApiException {
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix().replaceFirst(":\\d+/api/$", ":7777/api/")); // invalid
        MethodsClient client = Slack.getInstance(config).methods();
        client.postFormWithAuthorizationHeaderAndParseResponse(
                form -> form.add("foo", "bar"),
                client.getEndpointUrlPrefix() + Methods.CONVERSATIONS_INFO,
                "Bearer " + ValidToken,
                ConversationsInfoResponse.class
        );
    }

    @Test(expected = SlackApiException.class)
    public void postFormWithAuthorizationHeaderAndParseResponse_SlackApiException() throws IOException, SlackApiException {
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix() + "invalid-path"); // invalid
        MethodsClient client = Slack.getInstance(config).methods();
        client.postFormWithAuthorizationHeaderAndParseResponse(
                form -> form.add("foo", "bar"),
                client.getEndpointUrlPrefix() + Methods.CONVERSATIONS_INFO,
                "Bearer " + ValidToken,
                ConversationsInfoResponse.class
        );
    }

    @Test
    public void postFormWithTokenAndParseResponse() throws IOException, SlackApiException {
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
        MethodsClient client = Slack.getInstance(config).methods();
        ConversationsInfoResponse response = client.postFormWithTokenAndParseResponse(
                form -> form.add("foo", "bar"),
                Methods.CONVERSATIONS_INFO,
                ValidToken,
                ConversationsInfoResponse.class
        );
        assertTrue(response.isOk());
        assertNotNull(response.getChannel());
    }

    @Test(expected = IOException.class)
    public void postFormWithTokenAndParseResponse_IOException() throws IOException, SlackApiException {
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix().replaceFirst(":\\d+/api/$", ":7777/api/")); // invalid
        MethodsClient client = Slack.getInstance(config).methods();
        client.postFormWithTokenAndParseResponse(
                form -> form.add("foo", "bar"),
                Methods.CONVERSATIONS_INFO,
                ValidToken,
                ConversationsInfoResponse.class
        );
    }

    @Test(expected = SlackApiException.class)
    public void postFormWithTokenAndParseResponse_SlackApiException() throws IOException, SlackApiException {
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix() + "invalid-path"); // invalid
        MethodsClient client = Slack.getInstance(config).methods();
        client.postFormWithTokenAndParseResponse(
                form -> form.add("foo", "bar"),
                Methods.CONVERSATIONS_INFO,
                ValidToken,
                ConversationsInfoResponse.class
        );
    }

    @Test
    public void rejectsEmptyResponse() {
        assertNullResponses("");
    }

    @Test
    public void rejectsWhitespaceResponse() {
        assertNullResponses(" \n\t");
    }

    @Test
    public void rejectsJsonNullResponse() {
        assertNullResponses("null");
    }

    private void assertNullResponses(String body) {
        responseBody = body;
        List<HttpResponseListener.State> responses = new ArrayList<>();
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
        config.getMethodsConfig().setStatsEnabled(false);
        config.getHttpClientResponseHandlers().add(new HttpResponseListener() {
            @Override
            public void accept(State state) {
                responses.add(state);
            }
        });
        Slack slack = Slack.getInstance(config);

        SlackApiException withoutTeamId = assertThrows(SlackApiException.class,
                () -> slack.methods().apiTest(r -> r));
        assertInvalidResponse(withoutTeamId, body, responses, false);

        SlackApiException withTeamId = assertThrows(SlackApiException.class,
                () -> slack.methods(ValidToken, "T123").usergroupsList(r -> r));
        assertInvalidResponse(withTeamId, body, responses, false);

        SlackApiException multipart = assertThrows(SlackApiException.class,
                () -> slack.methods(ValidToken, "T123").filesUpload(r ->
                        r.fileData("test".getBytes(StandardCharsets.UTF_8)).filename("test.txt")));
        assertInvalidResponse(multipart, body, responses, true);

        SlackApiException analytics = assertThrows(SlackApiException.class,
                () -> slack.methods(ValidToken, "T123")
                        .adminAnalyticsGetFile(r -> r.date("2020-10-20").type("member")));
        assertInvalidResponse(analytics, body, responses, false);
    }

    private void assertInvalidResponse(SlackApiException failure, String body,
                                       List<HttpResponseListener.State> responses, boolean binaryRequest) {
        assertEquals(200, failure.getResponse().code());
        assertEquals(body, failure.getResponseBody());
        assertEquals("null-response-test", failure.getResponse().header("X-Slack-Req-Id"));
        assertNull(failure.getError());
        assertEquals(1, responses.size());
        assertEquals(body, responses.get(0).getParsedResponseBody());
        assertEquals(binaryRequest, responses.get(0).isRequestBodyBinary());
        responses.clear();
    }
}
