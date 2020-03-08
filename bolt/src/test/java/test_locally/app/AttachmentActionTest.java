package test_locally.app;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.AttachmentActionRequest;
import com.slack.api.bolt.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.AuthTestMockServer;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@Slf4j
public class AttachmentActionTest {

    AuthTestMockServer server = new AuthTestMockServer();
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

    String realPayload = "{\n" +
            "  \"type\": \"interactive_message\",\n" +
            "  \"actions\": [\n" +
            "    {\n" +
            "      \"name\": \"channel_list\",\n" +
            "      \"type\": \"select\",\n" +
            "      \"selected_options\":[\n" +
            "        {\n" +
            "          \"value\": \"C24BTKDQW\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ],\n" +
            "  \"callback_id\": \"pick_channel_for_fun\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T1ABCD2E12\",\n" +
            "    \"domain\": \"hooli-hq\"\n" +
            "  },\n" +
            "  \"channel\": {\n" +
            "    \"id\": \"C9C2VHR7D\",\n" +
            "    \"name\": \"triage-random\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U900MV5U7\",\n" +
            "    \"name\": \"gbelson\"\n" +
            "  },\n" +
            "  \"action_ts\": \"1520966872.245369\",\n" +
            "  \"message_ts\": \"1520965348.000538\",\n" +
            "  \"attachment_id\": \"1\",\n" +
            "  \"token\": \"lbAZE0ckwoSNJcsGWE7sqX5j\",\n" +
            "  \"is_app_unfurl\": false,\n" +
            "  \"original_message\": {\n" +
            "    \"text\": \"\",\n" +
            "    \"username\": \"Belson Bot\",\n" +
            "    \"bot_id\": \"B9DKHFZ1E\",\n" +
            "    \"attachments\":[\n" +
            "      {\n" +
            "        \"callback_id\": \"pick_channel_for_fun\",\n" +
            "        \"text\": \"Choose a channel\",\n" +
            "        \"id\": 1,\n" +
            "        \"color\": \"2b72cb\",\n" +
            "        \"actions\": [\n" +
            "          {\n" +
            "            \"id\": \"1\",\n" +
            "            \"name\": \"channel_list\",\n" +
            "            \"text\": \"Public channels\",\n" +
            "            \"type\": \"select\",\n" +
            "            \"data_source\": \"channels\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"fallback\":\"Choose a channel\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"type\": \"message\",\n" +
            "    \"subtype\": \"bot_message\",\n" +
            "    \"ts\": \"1520965348.000538\"\n" +
            "  },\n" +
            "  \"response_url\": \"https://hooks.slack.com/actions/T1ABCD2E12/330361579271/0dAEyLY19ofpLwxqozy3firz\",\n" +
            "  \"trigger_id\": \"328654886736.72393107734.9a0f78bccc3c64093f4b12fe82ccd51e\"\n" +
            "}";

    @Test
    public void test() throws Exception {
        App app = buildApp();
        app.attachmentAction("pick_channel_for_fun", (req, ctx) -> ctx.ack());

        String requestBody = "payload=" + URLEncoder.encode(realPayload, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        AttachmentActionRequest req = new AttachmentActionRequest(requestBody, realPayload, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void skipped() throws Exception {
        App app = buildApp();
        app.attachmentAction("pick_channel_for_work", (req, ctx) -> ctx.ack());

        String requestBody = "payload=" + URLEncoder.encode(realPayload, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        AttachmentActionRequest req = new AttachmentActionRequest(requestBody, realPayload, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(404L, response.getStatusCode().longValue());
    }

    App buildApp() {
        return new App(AppConfig.builder()
                .signingSecret(secret)
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .slack(slack)
                .build());
    }

    void setRequestHeaders(String requestBody, Map<String, List<String>> rawHeaders, String timestamp) {
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, Arrays.asList(timestamp));
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, Arrays.asList(generator.generate(timestamp, requestBody)));
    }

}
