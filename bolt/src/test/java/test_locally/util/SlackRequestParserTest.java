package test_locally.util;

import com.google.gson.Gson;
import com.slack.api.app_backend.interactive_components.payload.AttachmentActionPayload;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.app_backend.interactive_components.payload.BlockSuggestionPayload;
import com.slack.api.app_backend.interactive_components.payload.MessageActionPayload;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.util.SlackRequestParser;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import static org.junit.Assert.assertNotNull;

public class SlackRequestParserTest {

    Gson gson = GsonFactory.createSnakeCase();
    SlackRequestParser parser = new SlackRequestParser(AppConfig.builder().build());

    @Test
    public void testAttachmentActionPayload() throws UnsupportedEncodingException {
        AttachmentActionPayload payload = new AttachmentActionPayload();
        AttachmentActionPayload.Team team = new AttachmentActionPayload.Team();
        team.setId("T123");
        payload.setTeam(team);
        AttachmentActionPayload.User user = new AttachmentActionPayload.User();
        user.setId("U123");
        payload.setUser(user);
        SlackRequestParser.HttpRequest request = SlackRequestParser.HttpRequest.builder()
                .requestBody("payload=" + URLEncoder.encode(gson.toJson(payload), "UTF-8"))
                .headers(new RequestHeaders(new HashMap<>()))
                .build();

        Request<?> slackRequest = parser.parse(request);
        assertNotNull(slackRequest);
    }

    @Test
    public void testBlockActionPayload() throws UnsupportedEncodingException {
        BlockActionPayload payload = new BlockActionPayload();
        BlockActionPayload.Team team = new BlockActionPayload.Team();
        team.setId("T123");
        payload.setTeam(team);
        BlockActionPayload.User user = new BlockActionPayload.User();
        user.setId("U123");
        payload.setUser(user);
        SlackRequestParser.HttpRequest request = SlackRequestParser.HttpRequest.builder()
                .requestBody("payload=" + URLEncoder.encode(gson.toJson(payload), "UTF-8"))
                .headers(new RequestHeaders(new HashMap<>()))
                .build();

        Request<?> slackRequest = parser.parse(request);
        assertNotNull(slackRequest);
    }

    @Test
    public void testBlockActionSuggestion() throws UnsupportedEncodingException {
        BlockSuggestionPayload payload = new BlockSuggestionPayload();
        BlockSuggestionPayload.Team team = new BlockSuggestionPayload.Team();
        team.setId("T123");
        payload.setTeam(team);
        BlockSuggestionPayload.User user = new BlockSuggestionPayload.User();
        user.setId("U123");
        payload.setUser(user);
        SlackRequestParser.HttpRequest request = SlackRequestParser.HttpRequest.builder()
                .requestBody("payload=" + URLEncoder.encode(gson.toJson(payload), "UTF-8"))
                .headers(new RequestHeaders(new HashMap<>()))
                .build();

        Request<?> slackRequest = parser.parse(request);
        assertNotNull(slackRequest);
    }

    @Test
    public void testMessageActionPayload() throws UnsupportedEncodingException {
        MessageActionPayload payload = new MessageActionPayload();
        MessageActionPayload.Team team = new MessageActionPayload.Team();
        team.setId("T123");
        payload.setTeam(team);
        MessageActionPayload.User user = new MessageActionPayload.User();
        user.setId("U123");
        payload.setUser(user);
        SlackRequestParser.HttpRequest request = SlackRequestParser.HttpRequest.builder()
                .requestBody("payload=" + URLEncoder.encode(gson.toJson(payload), "UTF-8"))
                .headers(new RequestHeaders(new HashMap<>()))
                .build();

        Request<?> slackRequest = parser.parse(request);
        assertNotNull(slackRequest);
    }
}
