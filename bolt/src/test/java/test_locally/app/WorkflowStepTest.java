package test_locally.app;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.middleware.builtin.WorkflowStep;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.WorkflowStepEditRequest;
import com.slack.api.bolt.request.builtin.WorkflowStepSaveRequest;
import com.slack.api.bolt.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.AuthTestMockServer;

import java.net.URLEncoder;
import java.util.*;

import static org.junit.Assert.assertEquals;

@Slf4j
public class WorkflowStepTest {

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

    String editPayload = "{\n" +
            "  \"type\": \"workflow_step_edit\",\n" +
            "  \"token\": \"verification-token\",\n" +
            "  \"action_ts\": \"1601541356.268786\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T1234567\",\n" +
            "    \"domain\": \"subdomain\",\n" +
            "    \"enterprise_id\": \"E12345678\",\n" +
            "    \"enterprise_name\": \"Org Name\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U1234567\",\n" +
            "    \"username\": \"primary-owner\",\n" +
            "    \"team_id\": \"T1234567\"\n" +
            "  },\n" +
            "  \"callback_id\": \"step1\",\n" +
            "  \"trigger_id\": \"111.222.xxx\",\n" +
            "  \"workflow_step\": {\n" +
            "    \"workflow_id\": \"12345\",\n" +
            "    \"step_id\": \"111-222-333-444-555\",\n" +
            "    \"inputs\": {\n" +
            "      \"taskAuthorEmail\": {\n" +
            "        \"value\": \"seratch@example.com\"\n" +
            "      },\n" +
            "      \"taskDescription\": {\n" +
            "        \"value\": \"This is the task for you!\"\n" +
            "      },\n" +
            "      \"taskName\": {\n" +
            "        \"value\": \"The important task\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"outputs\": [\n" +
            "      {\n" +
            "        \"name\": \"taskName\",\n" +
            "        \"type\": \"text\",\n" +
            "        \"label\": \"Task Name\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"taskDescription\",\n" +
            "        \"type\": \"text\",\n" +
            "        \"label\": \"Task Description\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"taskAuthorEmail\",\n" +
            "        \"type\": \"text\",\n" +
            "        \"label\": \"Task Author Email\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}\n";

    String savePayload = "{\n" +
            "  \"type\": \"view_submission\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T1234567\",\n" +
            "    \"domain\": \"subdomain\",\n" +
            "    \"enterprise_id\": \"E12345678\",\n" +
            "    \"enterprise_name\": \"Org Name\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U1234567\",\n" +
            "    \"username\": \"primary-owner\",\n" +
            "    \"name\": \"primary-owner\",\n" +
            "    \"team_id\": \"T1234567\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"A111\",\n" +
            "  \"token\": \"verification-token\",\n" +
            "  \"trigger_id\": \"111.222.xxx\",\n" +
            "  \"view\": {\n" +
            "    \"id\": \"V111\",\n" +
            "    \"team_id\": \"T1234567\",\n" +
            "    \"type\": \"workflow_step\",\n" +
            "    \"blocks\": [\n" +
            "      {\n" +
            "        \"type\": \"section\",\n" +
            "        \"block_id\": \"intro-section\",\n" +
            "        \"text\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Create a task in one of the listed projects.\"\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"input\",\n" +
            "        \"block_id\": \"task_name_input\",\n" +
            "        \"label\": {\"type\": \"plain_text\", \"text\": \"Task name\"},\n" +
            "        \"optional\": False,\n" +
            "        \"element\": {\n" +
            "          \"type\": \"plain_text_input\",\n" +
            "          \"action_id\": \"task_name\",\n" +
            "          \"placeholder\": {\"type\": \"plain_text\", \"text\": \"Write a task name\"}\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"input\",\n" +
            "        \"block_id\": \"task_description_input\",\n" +
            "        \"label\": {\"type\": \"plain_text\", \"text\": \"Task description\"},\n" +
            "        \"optional\": False,\n" +
            "        \"element\": {\n" +
            "          \"type\": \"plain_text_input\",\n" +
            "          \"action_id\": \"task_description\",\n" +
            "          \"placeholder\": {\n" +
            "            \"type\": \"plain_text\",\n" +
            "            \"text\": \"Write a description for your task\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"input\",\n" +
            "        \"block_id\": \"task_author_input\",\n" +
            "        \"label\": {\"type\": \"plain_text\", \"text\": \"Task author\"},\n" +
            "        \"optional\": False,\n" +
            "        \"element\": {\n" +
            "          \"type\": \"plain_text_input\",\n" +
            "          \"action_id\": \"task_author\",\n" +
            "          \"placeholder\": {\"type\": \"plain_text\", \"text\": \"Write a task name\"}\n" +
            "        }\n" +
            "      }\n" +
            "    ],\n" +
            "    \"private_metadata\": \"\",\n" +
            "    \"callback_id\": \"step1\",\n" +
            "    \"state\": {\n" +
            "      \"values\": {\n" +
            "        \"task_name_input\": {\n" +
            "          \"task_name\": {\n" +
            "            \"type\": \"plain_text_input\",\n" +
            "            \"value\": \"The important task\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"task_description_input\": {\n" +
            "          \"task_description\": {\n" +
            "            \"type\": \"plain_text_input\",\n" +
            "            \"value\": \"This is the task for you!\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"task_author_input\": {\n" +
            "          \"task_author\": {\n" +
            "            \"type\": \"plain_text_input\",\n" +
            "            \"value\": \"seratch@example.com\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"hash\": \"111.zzz\",\n" +
            "    \"submit_disabled\": False,\n" +
            "    \"app_id\": \"A111\",\n" +
            "    \"external_id\": \"\",\n" +
            "    \"app_installed_team_id\": \"T1234567\",\n" +
            "    \"bot_id\": \"B12345678\"\n" +
            "  },\n" +
            "  \"response_urls\": [],\n" +
            "  \"workflow_step\": {\n" +
            "    \"workflow_step_edit_id\": \"111.222.zzz\",\n" +
            "    \"workflow_id\": \"12345\",\n" +
            "    \"step_id\": \"111-222-333-444-555\"\n" +
            "  }\n" +
            "}";

    @Test
    public void multipleSteps() throws Exception {
        App app = buildApp();
        WorkflowStep step1 = WorkflowStep.builder()
                .callbackId("step1")
                .edit((req, ctx) -> ctx.ack())
                .save((req, ctx) -> {
                    ctx.update(Collections.emptyMap(), Collections.emptyList());
                    return ctx.ack();
                })
                .execute((req, ctx) -> ctx.ack())
                .build();
        WorkflowStep step2 = WorkflowStep.builder()
                .callbackId("step2")
                .edit((req, ctx) -> ctx.ack())
                .save((req, ctx) -> ctx.ack())
                .execute((req, ctx) -> ctx.ack())
                .build();

        app.step(step1);
        app.step(step2);

        String requestBody = "payload=" + URLEncoder.encode(editPayload, "UTF-8");
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        Response response = app.run(new WorkflowStepEditRequest(requestBody, editPayload, new RequestHeaders(rawHeaders)));
        assertEquals(200L, response.getStatusCode().longValue());

        requestBody = "payload=" + URLEncoder.encode(savePayload, "UTF-8");
        rawHeaders = new HashMap<>();
        timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        response = app.run(new WorkflowStepSaveRequest(requestBody, savePayload, new RequestHeaders(rawHeaders)));
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void multipleSteps_not_found() throws Exception {
        App app = buildApp();
        WorkflowStep step2 = WorkflowStep.builder()
                .callbackId("step2")
                .edit((req, ctx) -> ctx.ack())
                .save((req, ctx) -> {
                    ctx.update(Collections.emptyMap(), Collections.emptyList());
                    return ctx.ack();
                })
                .execute((req, ctx) -> ctx.ack())
                .build();
        WorkflowStep step3 = WorkflowStep.builder()
                .callbackId("step3")
                .edit((req, ctx) -> ctx.ack())
                .save((req, ctx) -> ctx.ack())
                .execute((req, ctx) -> ctx.ack())
                .build();

        app.step(step2);
        app.step(step3);

        String requestBody = "payload=" + URLEncoder.encode(editPayload, "UTF-8");
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        Response response = app.run(new WorkflowStepEditRequest(requestBody, editPayload, new RequestHeaders(rawHeaders)));
        assertEquals(404L, response.getStatusCode().longValue());

        requestBody = "payload=" + URLEncoder.encode(savePayload, "UTF-8");
        rawHeaders = new HashMap<>();
        timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        response = app.run(new WorkflowStepSaveRequest(requestBody, savePayload, new RequestHeaders(rawHeaders)));
        assertEquals(404L, response.getStatusCode().longValue());

        WorkflowStep step1 = WorkflowStep.builder()
                .callbackId("step1")
                .edit((req, ctx) -> ctx.ack())
                .save((req, ctx) -> {
                    ctx.update(Collections.emptyMap(), Collections.emptyList());
                    return ctx.ack();
                })
                .execute((req, ctx) -> ctx.ack())
                .build();
        app.step(step1);

        requestBody = "payload=" + URLEncoder.encode(editPayload, "UTF-8");
        rawHeaders = new HashMap<>();
        timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        response = app.run(new WorkflowStepEditRequest(requestBody, editPayload, new RequestHeaders(rawHeaders)));
        assertEquals(200L, response.getStatusCode().longValue());

        requestBody = "payload=" + URLEncoder.encode(savePayload, "UTF-8");
        rawHeaders = new HashMap<>();
        timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        response = app.run(new WorkflowStepSaveRequest(requestBody, savePayload, new RequestHeaders(rawHeaders)));
        assertEquals(200L, response.getStatusCode().longValue());
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
