package test_locally.app;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.request.builtin.EventRequest;
import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.model.event.FunctionExecutedEvent;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.AuthTestMockServer;
import util.MockSlackApiServer;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

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

    String eventPayload = "{\n" +
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

    String actionPayload = "{\n" +
            "  \"type\": \"block_actions\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T03E94MJU\",\n" +
            "    \"domain\": \"seratch\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U03E94MK0\",\n" +
            "    \"name\": \"seratch\",\n" +
            "    \"team_id\": \"T03E94MJU\"\n" +
            "  },\n" +
            "  \"channel\": {\n" +
            "    \"id\": \"D065ZJQQQAE\",\n" +
            "    \"name\": \"directmessage\"\n" +
            "  },\n" +
            "  \"message\": {\n" +
            "    \"bot_id\": \"B065SV9Q70W\",\n" +
            "    \"type\": \"message\",\n" +
            "    \"text\": \"hey!\",\n" +
            "    \"user\": \"U066C7XNE6M\",\n" +
            "    \"ts\": \"1700615389.639759\",\n" +
            "    \"app_id\": \"A065ZJM410S\",\n" +
            "    \"blocks\": [\n" +
            "      {\n" +
            "        \"type\": \"actions\",\n" +
            "        \"block_id\": \"b\",\n" +
            "        \"elements\": [\n" +
            "          {\n" +
            "            \"type\": \"button\",\n" +
            "            \"action_id\": \"remote-function-button-success\",\n" +
            "            \"text\": {\n" +
            "              \"type\": \"plain_text\",\n" +
            "              \"text\": \"block_actions success\",\n" +
            "              \"emoji\": true\n" +
            "            },\n" +
            "            \"value\": \"clicked\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"button\",\n" +
            "            \"action_id\": \"remote-function-button-error\",\n" +
            "            \"text\": {\n" +
            "              \"type\": \"plain_text\",\n" +
            "              \"text\": \"block_actions error\",\n" +
            "              \"emoji\": true\n" +
            "            },\n" +
            "            \"value\": \"clicked\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"button\",\n" +
            "            \"action_id\": \"remote-function-modal\",\n" +
            "            \"text\": {\n" +
            "              \"type\": \"plain_text\",\n" +
            "              \"text\": \"modal view\",\n" +
            "              \"emoji\": true\n" +
            "            },\n" +
            "            \"value\": \"clicked\"\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ],\n" +
            "    \"team\": \"T03E94MJU\"\n" +
            "  },\n" +
            "  \"container\": {\n" +
            "    \"type\": \"message\",\n" +
            "    \"message_ts\": \"1700615389.639759\",\n" +
            "    \"channel_id\": \"D065ZJQQQAE\",\n" +
            "    \"is_ephemeral\": false\n" +
            "  },\n" +
            "  \"actions\": [\n" +
            "    {\n" +
            "      \"block_id\": \"b\",\n" +
            "      \"action_id\": \"remote-function-modal\",\n" +
            "      \"type\": \"button\",\n" +
            "      \"text\": {\n" +
            "        \"type\": \"plain_text\",\n" +
            "        \"text\": \"modal view\",\n" +
            "        \"emoji\": true\n" +
            "      },\n" +
            "      \"value\": \"clicked\",\n" +
            "      \"action_ts\": \"1700615393.797628\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"api_app_id\": \"A065ZJM410S\",\n" +
            "  \"state\": {\n" +
            "    \"values\": {}\n" +
            "  },\n" +
            "  \"bot_access_token\": \"xwfp-this-is-valid\",\n" +
            "  \"function_data\": {\n" +
            "    \"execution_id\": \"Fx066WEAA7BN\",\n" +
            "    \"function\": {\n" +
            "      \"callback_id\": \"hello\"\n" +
            "    },\n" +
            "    \"inputs\": {\n" +
            "      \"amount\": 1,\n" +
            "      \"message\": \"hey\",\n" +
            "      \"user_id\": \"U03E94MK0\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"interactivity\": {\n" +
            "    \"interactor\": {\n" +
            "      \"secret\": \"secret\",\n" +
            "      \"id\": \"U03E94MK0\"\n" +
            "    },\n" +
            "    \"interactivity_pointer\": \"111.222.333\"\n" +
            "  }\n" +
            "}\n";

    String viewSubmissionPayload = "{\n" +
            "  \"type\": \"view_submission\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T03E94MJU\",\n" +
            "    \"domain\": \"seratch\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U03E94MK0\",\n" +
            "    \"name\": \"seratch\",\n" +
            "    \"team_id\": \"T03E94MJU\"\n" +
            "  },\n" +
            "  \"view\": {\n" +
            "    \"id\": \"V066MBN4MTQ\",\n" +
            "    \"team_id\": \"T03E94MJU\",\n" +
            "    \"app_id\": \"A065ZJM410S\",\n" +
            "    \"app_installed_team_id\": \"T03E94MJU\",\n" +
            "    \"bot_id\": \"B065SV9Q70W\",\n" +
            "    \"title\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Remote Function test\",\n" +
            "      \"emoji\": false\n" +
            "    },\n" +
            "    \"type\": \"modal\",\n" +
            "    \"blocks\": [\n" +
            "      {\n" +
            "        \"type\": \"input\",\n" +
            "        \"block_id\": \"text-block\",\n" +
            "        \"label\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Text\",\n" +
            "          \"emoji\": true\n" +
            "        },\n" +
            "        \"optional\": false,\n" +
            "        \"dispatch_action\": false,\n" +
            "        \"element\": {\n" +
            "          \"type\": \"plain_text_input\",\n" +
            "          \"action_id\": \"text-action\",\n" +
            "          \"multiline\": true,\n" +
            "          \"dispatch_action_config\": {\n" +
            "            \"trigger_actions_on\": [\n" +
            "              \"on_enter_pressed\"\n" +
            "            ]\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    ],\n" +
            "    \"close\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Close\",\n" +
            "      \"emoji\": false\n" +
            "    },\n" +
            "    \"submit\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Submit\",\n" +
            "      \"emoji\": false\n" +
            "    },\n" +
            "    \"state\": {\n" +
            "      \"values\": {\n" +
            "        \"text-block\": {\n" +
            "          \"text-action\": {\n" +
            "            \"type\": \"plain_text_input\",\n" +
            "            \"value\": \"test\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"hash\": \"1700615394.RxtPRJt3\",\n" +
            "    \"private_metadata\": \"\",\n" +
            "    \"callback_id\": \"remote-function-view\",\n" +
            "    \"root_view_id\": \"V066MBN4MTQ\",\n" +
            "    \"clear_on_close\": false,\n" +
            "    \"notify_on_close\": true,\n" +
            "    \"external_id\": \"\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"A065ZJM410S\",\n" +
            "  \"bot_access_token\": \"xwfp-this-is-valid\",\n" +
            "  \"function_data\": {\n" +
            "    \"execution_id\": \"Fx066WEAA7BN\",\n" +
            "    \"function\": {\n" +
            "      \"callback_id\": \"hello\"\n" +
            "    },\n" +
            "    \"inputs\": {\n" +
            "      \"amount\": 1,\n" +
            "      \"message\": \"hey\",\n" +
            "      \"user_id\": \"U03E94MK0\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"interactivity\": {\n" +
            "    \"interactor\": {\n" +
            "      \"secret\": \"secret\",\n" +
            "      \"id\": \"U03E94MK0\"\n" +
            "    },\n" +
            "    \"interactivity_pointer\": \"111.222.333\"\n" +
            "  }\n" +
            "}\n";

    @Test
    public void all_function_events() throws Exception {
        App app = buildApp();
        AtomicBoolean called = new AtomicBoolean(false);
        app.event(FunctionExecutedEvent.class, (req, ctx) -> {
            called.set(req.getEvent().getFunction().getCallbackId().equals("hello")
                    && req.getEvent().getInputs().get("user_id").asString().equals("U03E94MK0")
                    && req.getEvent().getInputs().get("amount").asInteger().equals(1)
                    && req.getEvent().getBotAccessToken().equals("xwfp-this-is-valid")
            );
            called.set(ctx.client().functionsCompleteSuccess(r -> r
                    // TODO: remove this token passing by enhancing bolt internals
                    .token(req.getEvent().getBotAccessToken())
                    .functionExecutionId(req.getEvent().getFunctionExecutionId())
                    .outputs(new HashMap<>())
            ).getError().equals(""));
            called.set(ctx.client().functionsCompleteError(r -> r
                    // TODO: remove this token passing by enhancing bolt internals
                    .token(req.getEvent().getBotAccessToken())
                    .functionExecutionId(req.getEvent().getFunctionExecutionId())
                    .error("something wrong")
            ).getError().equals(""));
            return ctx.ack();
        });

        Response response = app.run(buildEventRequest());
        assertEquals(200L, response.getStatusCode().longValue());
        assertTrue(called.get());
    }

    @Test
    public void button_clicks() throws Exception {
        App app = buildApp();
        AtomicBoolean called = new AtomicBoolean(false);
        app.blockAction("remote-function-modal", (req, ctx) -> {
            called.set(req.getPayload().getFunctionData().getFunction().getCallbackId().equals("hello")
                    && req.getPayload().getFunctionData().getInputs().get("user_id").asString().equals("U03E94MK0")
                    && req.getPayload().getFunctionData().getInputs().get("amount").asInteger().equals(1)
                    && req.getPayload().getBotAccessToken().equals("xwfp-this-is-valid")
            );
            called.set(ctx.client().functionsCompleteSuccess(r -> r
                    // TODO: remove this token passing by enhancing bolt internals
                    .token(req.getPayload().getBotAccessToken())
                    .functionExecutionId(req.getPayload().getFunctionData().getExecutionId())
                    .outputs(new HashMap<>())
            ).getError().equals(""));
            return ctx.ack();
        });

        Response response = app.run(buildActionRequest());
        assertEquals(200L, response.getStatusCode().longValue());
        assertTrue(called.get());
    }

    @Test
    public void view_submissions() throws Exception {
        App app = buildApp();
        AtomicBoolean called = new AtomicBoolean(false);
        app.viewSubmission("remote-function-view", (req, ctx) -> {
            called.set(req.getPayload().getFunctionData().getFunction().getCallbackId().equals("hello")
                    && req.getPayload().getFunctionData().getInputs().get("user_id").asString().equals("U03E94MK0")
                    && req.getPayload().getFunctionData().getInputs().get("amount").asInteger().equals(1)
                    && req.getPayload().getBotAccessToken().equals("xwfp-this-is-valid")
            );
            called.set(ctx.client().functionsCompleteSuccess(r -> r
                    // TODO: remove this token passing by enhancing bolt internals
                    .token(req.getPayload().getBotAccessToken())
                    .functionExecutionId(req.getPayload().getFunctionData().getExecutionId())
                    .outputs(new HashMap<>())
            ).getError().equals(""));
            return ctx.ack();
        });

        Response response = app.run(buildViewSubmissionRequest());
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

    EventRequest buildEventRequest() {
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(eventPayload, rawHeaders, timestamp);
        return new EventRequest(eventPayload, new RequestHeaders(rawHeaders));
    }

    BlockActionRequest buildActionRequest() {
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String body = "payload=" + URLEncoder.encode(actionPayload);
        setRequestHeaders(body, rawHeaders, timestamp);
        return new BlockActionRequest(body, actionPayload, new RequestHeaders(rawHeaders));
    }

    ViewSubmissionRequest buildViewSubmissionRequest() {
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String body = "payload=" + URLEncoder.encode(viewSubmissionPayload);
        setRequestHeaders(body, rawHeaders, timestamp);
        return new ViewSubmissionRequest(body, viewSubmissionPayload, new RequestHeaders(rawHeaders));
    }
}
