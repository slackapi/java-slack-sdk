package test_locally.middleware;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.events.payload.MemberJoinedChannelPayload;
import com.slack.api.app_backend.events.payload.MessagePayload;
import com.slack.api.app_backend.events.payload.UserChangePayload;
import com.slack.api.bolt.middleware.MiddlewareChain;
import com.slack.api.bolt.middleware.builtin.IgnoringSelfEvents;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.EventRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.MethodsClient;
import com.slack.api.model.User;
import com.slack.api.model.event.MemberJoinedChannelEvent;
import com.slack.api.model.event.MessageEvent;
import com.slack.api.model.event.UserChangeEvent;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static util.MockSlackApi.InvalidToken;
import static util.MockSlackApi.ValidToken;

public class IgnoringSelfEventsTest {

    final MiddlewareChain chain = req -> Response.error(404);

    @Test
    public void ignored() throws Exception {
        IgnoringSelfEvents middleware = new IgnoringSelfEvents(SlackConfig.DEFAULT) {
            @Override
            public String findAndSaveBotUserId(MethodsClient client, String botId) {
                return "U123BOT";
            }
        };
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
        EventRequest req = new EventRequest(GsonFactory.createSnakeCase().toJson(payload), headers);
        req.getContext().setBotUserId("U123BOT");
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(200L, result.getStatusCode().longValue());
    }

    @Test
    public void not_ignored() throws Exception {
        IgnoringSelfEvents middleware = new IgnoringSelfEvents(SlackConfig.DEFAULT) {
            @Override
            public String findAndSaveBotUserId(MethodsClient client, String botId) {
                return "U123BOT";
            }
        };
        Map<String, List<String>> rawHeaders = new HashMap<>();
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        MemberJoinedChannelPayload payload = new MemberJoinedChannelPayload();
        payload.setType("member_joined_channel");
        payload.setTeamId("T123");
        MemberJoinedChannelEvent event = new MemberJoinedChannelEvent();
        event.setUser("U123BOT");
        payload.setEvent(event);
        EventRequest req = new EventRequest(GsonFactory.createSnakeCase().toJson(payload), headers);
        req.getContext().setBotUserId("U123BOT");
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(404L, result.getStatusCode().longValue());
    }

    @Test
    public void ignored_withUserObject() throws Exception {
        IgnoringSelfEvents middleware = new IgnoringSelfEvents(SlackConfig.DEFAULT) {
            @Override
            public String findAndSaveBotUserId(MethodsClient client, String botId) {
                return "U123BOT";
            }
        };
        Map<String, List<String>> rawHeaders = new HashMap<>();
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        UserChangePayload payload = new UserChangePayload();
        payload.setTeamId("T123");
        UserChangeEvent event = new UserChangeEvent();
        User user = new User();
        user.setId("U123BOT");
        event.setUser(user);
        payload.setEvent(event);
        EventRequest req = new EventRequest(GsonFactory.createSnakeCase().toJson(payload), headers);
        req.getContext().setBotUserId("U123BOT");
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(200L, result.getStatusCode().longValue());
    }

    @Test
    public void not_ignored_no_botUserId() throws Exception {
        IgnoringSelfEvents middleware = new IgnoringSelfEvents(SlackConfig.DEFAULT) {
            @Override
            public String findAndSaveBotUserId(MethodsClient client, String botId) {
                return "U123BOT";
            }
        };
        Map<String, List<String>> rawHeaders = new HashMap<>();
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        MemberJoinedChannelPayload payload = new MemberJoinedChannelPayload();
        payload.setType("member_joined_channel");
        payload.setTeamId("T123");
        MemberJoinedChannelEvent event = new MemberJoinedChannelEvent();
        event.setUser("U123BOT");
        payload.setEvent(event);
        EventRequest req = new EventRequest(GsonFactory.createSnakeCase().toJson(payload), headers);
        req.getContext().setBotUserId(null); // intentionally null here
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(404L, result.getStatusCode().longValue());
    }

    @Test
    public void eventTypesNotToMiss() {
        IgnoringSelfEvents middleware = new IgnoringSelfEvents(SlackConfig.DEFAULT);
        assertNotNull(middleware.getEventTypesNotToMiss());
        middleware.setEventTypesNotToMiss(Collections.emptyList());
    }

    MockSlackApiServer slackApiServer = new MockSlackApiServer();

    @Test
    public void findBotUserId() throws Exception {
        slackApiServer.start();
        try {
            SlackConfig config = new SlackConfig();
            Slack slack = Slack.getInstance(config);
            config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
            IgnoringSelfEvents middleware = new IgnoringSelfEvents(config);
            String nullUserId = middleware.findAndSaveBotUserId(slack.methods(InvalidToken), "botId");
            assertNull(nullUserId);
            String userId = middleware.findAndSaveBotUserId(slack.methods(ValidToken), "botId");
            assertEquals("U00000000", userId);
            String cachedUserId = middleware.findAndSaveBotUserId(slack.methods(ValidToken), "botId");
            assertEquals("U00000000", cachedUserId);
        } finally {
            slackApiServer.stop();
        }
    }
}
