package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.Methods;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.api.ApiTestResponse;
import com.slack.api.methods.response.conversations.ConversationsInfoResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.io.IOException;

import static org.junit.Assert.*;
import static util.MockSlackApi.ValidToken;

public class MethodsClientImplTest {

    MockSlackApiServer slackApiServer = new MockSlackApiServer();

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
}
