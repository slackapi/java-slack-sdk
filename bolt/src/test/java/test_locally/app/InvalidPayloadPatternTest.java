package test_locally.app;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.app_backend.interactive_components.response.Option;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.util.SlackRequestParser;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.AuthTestMockServer;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static org.junit.Assert.assertEquals;

@Slf4j
public class InvalidPayloadPatternTest {

    AuthTestMockServer server = new AuthTestMockServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);
    private static final String APPLICATION_JSON = MimeTypes.Type.APPLICATION_JSON.getContentTypeField().getValue();
    private static final String CONTENT_TYPE = HttpHeader.CONTENT_TYPE.toString();

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

    App buildApp() {
        return new App(AppConfig.builder()
                .signingSecret(secret)
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .slack(slack)
                .build());
    }

    void setJSONDataRequestHeaders(String requestBody, Map<String, List<String>> rawHeaders, String timestamp) {
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, Arrays.asList(timestamp));
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, Arrays.asList(generator.generate(timestamp, requestBody)));
        rawHeaders.put(CONTENT_TYPE, Arrays.asList(APPLICATION_JSON));
    }

    @Test
    public void validPayload() throws Exception {
        App app = buildApp();
        SlackRequestParser parser = new SlackRequestParser(app.config());
        String validPayload = "{\"type\":\"block_suggestion\",\"team\":{\"id\":\"T123\",\"domain\":\"test-test\"},\"user\":{\"id\":\"U123\",\"username\":\"test-test\",\"name\":\"test-test\",\"team_id\":\"T123\"},\"container\":{\"type\":\"message\",\"message_ts\":\"1583636071.000500\",\"channel_id\":\"D123\",\"is_ephemeral\":true},\"api_app_id\":\"AU776HF50\",\"token\":\"legacy-fixed-value\",\"action_id\":\"select-action\",\"block_id\":\"UJMC\",\"value\":\"a\",\"channel\":{\"id\":\"D123\",\"name\":\"directmessage\"}}";
        String requestBody = "payload=" + URLEncoder.encode(validPayload, "UTF-8");
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, Arrays.asList(timestamp));
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, Arrays.asList(generator.generate(timestamp, requestBody)));
        Request<?> request = parser.parse(SlackRequestParser.HttpRequest.builder().headers(new RequestHeaders(rawHeaders)).requestBody(requestBody).build());

        Response response = app.run(request);
        assertEquals(404L, response.getStatusCode().longValue());

        app.blockSuggestion("select-action", (req, ctx) -> {
            List<Option> options = Arrays.asList(Option.builder().text(plainText("label")).value("v").build());
            return ctx.ack(r -> r.options(options));
        });
        response = app.run(request);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void nonsense() throws Exception {
        App app = buildApp();
        SlackRequestParser parser = new SlackRequestParser(app.config());
        String requestBody = "{\n" +
                "  \"token\": \"fixed-value\",\n" +
                "  \"challenge\": \"challenge-value\",\n" +
                "  \"type\": \"nonsense\"\n" +
                "}";
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setJSONDataRequestHeaders(requestBody, rawHeaders, timestamp);
        Request<?> request = parser.parse(SlackRequestParser.HttpRequest.builder().headers(new RequestHeaders(rawHeaders)).requestBody(requestBody).build());

        Response response = app.run(request);
        assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatusCode().intValue());
        assertEquals("Invalid Request", response.getBody());
    }

    @Test
    public void url_verification_valid() throws Exception {
        App app = buildApp();
        SlackRequestParser parser = new SlackRequestParser(app.config());
        String requestBody = "{\n" +
                "  \"token\": \"fixed-value\",\n" +
                "  \"challenge\": \"challenge-value\",\n" +
                "  \"type\": \"url_verification\"\n" +
                "}";
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setJSONDataRequestHeaders(requestBody, rawHeaders, timestamp);
        Request<?> request = parser.parse(SlackRequestParser.HttpRequest.builder().headers(new RequestHeaders(rawHeaders)).requestBody(requestBody).build());

        Response response = app.run(request);
        assertEquals(HttpStatus.OK_200, response.getStatusCode().intValue());
        assertEquals("challenge-value", response.getBody());
    }

    @Test
    public void url_verification_invalid_signature() throws Exception {
        App app = buildApp();
        SlackRequestParser parser = new SlackRequestParser(app.config());
        String requestBody = "{\n" +
                "  \"token\": \"fixed-value\",\n" +
                "  \"challenge\": \"challenge-value\",\n" +
                "  \"type\": \"url_verification\"\n" +
                "}";
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setJSONDataRequestHeaders(requestBody, rawHeaders, timestamp);
        SlackSignature.Generator differentGenerator = new SlackSignature.Generator("a different app's secret");
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, Arrays.asList(differentGenerator.generate(timestamp, requestBody)));
        Request<?> request = parser.parse(SlackRequestParser.HttpRequest.builder().headers(new RequestHeaders(rawHeaders)).requestBody(requestBody).build());

        Response response = app.run(request);
        assertEquals(HttpStatus.UNAUTHORIZED_401, response.getStatusCode().intValue());
        assertEquals("{\"error\":\"invalid request\"}", response.getBody());
    }

}
