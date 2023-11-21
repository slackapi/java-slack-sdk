package test_locally.app;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.EventRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.model.event.FunctionExecutedEvent;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.AuthTestMockServer;
import util.MockSlackApiServer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
public class RemoteFunctionTest {

    MockSlackApiServer server = new MockSlackApiServer();
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

    final Gson gson = GsonFactory.createSnakeCase();
    final String secret = "foo-bar-baz";
    final SlackSignature.Generator generator = new SlackSignature.Generator(secret);

    String payload = "{\n" +
            "  \"token\": \"xxx\",\n" +
            "  \"team_id\": \"T03E94MJU\",\n" +
            "  \"api_app_id\": \"A065ZJM410S\",\n" +
            "  \"event\": {\n" +
            "    \"type\": \"function_executed\",\n" +
            "    \"function\": {\n" +
            "      \"id\": \"Fn066C7U22JD\",\n" +
            "      \"callback_id\": \"hello\",\n" +
            "      \"title\": \"Hello\",\n" +
            "      \"description\": \"Hello world!\",\n" +
            "      \"type\": \"app\",\n" +
            "      \"input_parameters\": [\n" +
            "        {\n" +
            "          \"type\": \"number\",\n" +
            "          \"name\": \"amount\",\n" +
            "          \"description\": \"How many do you need?\",\n" +
            "          \"title\": \"Amount\",\n" +
            "          \"is_required\": false,\n" +
            "          \"hint\": \"How many do you need?\",\n" +
            "          \"maximum\": 10,\n" +
            "          \"minimum\": 1\n" +
            "        },\n" +
            "        {\n" +
            "          \"type\": \"slack#/types/user_id\",\n" +
            "          \"name\": \"user_id\",\n" +
            "          \"description\": \"Who to send it\",\n" +
            "          \"title\": \"User\",\n" +
            "          \"is_required\": true,\n" +
            "          \"hint\": \"Select a user in the workspace\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"type\": \"string\",\n" +
            "          \"name\": \"message\",\n" +
            "          \"description\": \"Whatever you want to tell\",\n" +
            "          \"title\": \"Message\",\n" +
            "          \"is_required\": false,\n" +
            "          \"hint\": \"up to 100 characters\",\n" +
            "          \"maxLength\": 100,\n" +
            "          \"minLength\": 1\n" +
            "        }\n" +
            "      ],\n" +
            "      \"output_parameters\": [\n" +
            "        {\n" +
            "          \"type\": \"number\",\n" +
            "          \"name\": \"amount\",\n" +
            "          \"description\": \"How many do you need?\",\n" +
            "          \"title\": \"Amount\",\n" +
            "          \"is_required\": false,\n" +
            "          \"hint\": \"How many do you need?\",\n" +
            "          \"maximum\": 10,\n" +
            "          \"minimum\": 1\n" +
            "        },\n" +
            "        {\n" +
            "          \"type\": \"slack#/types/user_id\",\n" +
            "          \"name\": \"user_id\",\n" +
            "          \"description\": \"Who to send it\",\n" +
            "          \"title\": \"User\",\n" +
            "          \"is_required\": true,\n" +
            "          \"hint\": \"Select a user in the workspace\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"type\": \"string\",\n" +
            "          \"name\": \"message\",\n" +
            "          \"description\": \"Whatever you want to tell\",\n" +
            "          \"title\": \"Message\",\n" +
            "          \"is_required\": false,\n" +
            "          \"hint\": \"up to 100 characters\",\n" +
            "          \"maxLength\": 100,\n" +
            "          \"minLength\": 1\n" +
            "        }\n" +
            "      ],\n" +
            "      \"app_id\": \"A065ZJM410S\",\n" +
            "      \"date_created\": 1700110468,\n" +
            "      \"date_updated\": 1700110470,\n" +
            "      \"date_deleted\": 0,\n" +
            "      \"form_enabled\": false\n" +
            "    },\n" +
            "    \"inputs\": {\n" +
            "      \"amount\": 1,\n" +
            "      \"message\": \"hey\",\n" +
            "      \"user_id\": \"U03E94MK0\"\n" +
            "    },\n" +
            "    \"function_execution_id\": \"Fx066G2XBP0E\",\n" +
            "    \"workflow_execution_id\": \"Wx066862SLRM\",\n" +
            "    \"event_ts\": \"1700554202.283041\",\n" +
            "    \"bot_access_token\": \"xwfp-this-is-valid\"\n" +
            "  },\n" +
            "  \"type\": \"event_callback\",\n" +
            "  \"event_id\": \"Ev067BMBHK16\",\n" +
            "  \"event_time\": 1700554202\n" +
            "}\n";

    @Test
    public void all_function_events() throws Exception {
        App app = buildApp();
        AtomicBoolean called = new AtomicBoolean(false);
        app.event(FunctionExecutedEvent.class, (req, ctx) -> {
            called.set(req.getEvent().getFunction().getCallbackId().equals("hello")
                    && req.getEvent().getInputs().get("user_id").asString().equals("U03E94MK0")
                    && req.getEvent().getInputs().get("amount").asInteger().equals(1)
                    && ctx.isAttachingFunctionTokenEnabled()
                    && ctx.getFunctionBotAccessToken().equals("xwfp-valid"));
            called.set(ctx.client().functionsCompleteSuccess(r -> r
                    .functionExecutionId(req.getEvent().getFunctionExecutionId())
                    .outputs(new HashMap<>())
            ).getError().equals(""));
            called.set(ctx.client().functionsCompleteError(r -> r
                    .functionExecutionId(req.getEvent().getFunctionExecutionId())
                    .error("something wrong")
            ).getError().equals(""));
            return ctx.ack();
        });

        Response response = app.run(buildRequest());
        assertEquals(200L, response.getStatusCode().longValue());
        assertTrue(called.get());
    }

    @Test
    public void static_callback_id() throws Exception {
        App app = buildApp();
        AtomicBoolean called = new AtomicBoolean(false);
        app.function("hello", (req, ctx) -> {
            called.set(req.getEvent().getFunction().getCallbackId().equals("hello")
                    && req.getEvent().getInputs().get("user_id").asString().equals("U03E94MK0")
                    && req.getEvent().getInputs().get("amount").asInteger().equals(1)
                    && ctx.isAttachingFunctionTokenEnabled()
                    && ctx.getFunctionBotAccessToken().equals("xwfp-valid"));
            called.set(ctx.client().functionsCompleteSuccess(r -> r
                    .functionExecutionId(req.getEvent().getFunctionExecutionId())
                    .outputs(new HashMap<>())
            ).getError().equals(""));
            return ctx.ack();
        });

        Response response = app.run(buildRequest());
        assertEquals(200L, response.getStatusCode().longValue());
        assertTrue(called.get());
    }

    @Test
    public void regexp_callback_id() throws Exception {
        App app = buildApp();
        AtomicBoolean called = new AtomicBoolean(false);
        app.function(Pattern.compile("^he.+"), (req, ctx) -> {
            called.set(req.getEvent().getFunction().getCallbackId().equals("hello")
                    && req.getEvent().getInputs().get("user_id").asString().equals("U03E94MK0")
                    && req.getEvent().getInputs().get("amount").asInteger().equals(1)
                    && ctx.isAttachingFunctionTokenEnabled()
                    && ctx.getFunctionBotAccessToken().equals("xwfp-valid"));
            called.set(ctx.client().functionsCompleteSuccess(r -> r
                    .functionExecutionId(req.getEvent().getFunctionExecutionId())
                    .outputs(new HashMap<>())
            ).getError().equals(""));
            return ctx.ack();
        });

        Response response = app.run(buildRequest());
        assertEquals(200L, response.getStatusCode().longValue());
        assertTrue(called.get());
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

    EventRequest buildRequest() {
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(payload, rawHeaders, timestamp);
        return new EventRequest(payload, new RequestHeaders(rawHeaders));
    }
}
