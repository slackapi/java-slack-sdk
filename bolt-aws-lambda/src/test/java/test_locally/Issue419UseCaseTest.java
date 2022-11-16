package test_locally;

import com.amazonaws.services.lambda.runtime.Context;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.app_backend.events.payload.MessagePayload;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.aws_lambda.SlackApiLambdaHandler;
import com.slack.api.bolt.aws_lambda.request.ApiGatewayRequest;
import com.slack.api.bolt.aws_lambda.request.RequestContext;
import com.slack.api.bolt.aws_lambda.response.ApiGatewayResponse;
import com.slack.api.bolt.response.Response;
import com.slack.api.model.event.MessageEvent;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;
import util.AuthTestMockServer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static util.ObjectInitializer.initProperties;

public class Issue419UseCaseTest {

    static class SampleHandler extends SlackApiLambdaHandler {
        AtomicInteger messageCount = new AtomicInteger(0);

        public SampleHandler(App app) {
            super(app);
        }

        @Override
        protected boolean isWarmupRequest(ApiGatewayRequest awsReq) {
            return false;
        }

        public void enableEventSubscription() {
            App app = app();
            app.event(MessageEvent.class, (event, ctx) -> {
                messageCount.incrementAndGet();
                return ctx.ack();
            });
        }
    }

    String signingSecret = "secret";

    @Test
    public void enableAdditionalListenersRuntime_issue_419() throws Exception {
        AuthTestMockServer slackApiServer = new AuthTestMockServer();
        slackApiServer.start();

        try {
            SlackConfig slackConfig = new SlackConfig();
            slackConfig.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
            Slack slack = Slack.getInstance(slackConfig);
            App app = new App(AppConfig.builder().slack(slack)
                    .singleTeamBotToken(AuthTestMockServer.ValidToken)
                    .signingSecret(signingSecret)
                    .build()
            );

            AtomicBoolean called = new AtomicBoolean(false);
            app.blockAction("start-event-subscription", (req, ctx) -> {
                called.set(true);
                // return ctx.ack();
                Map<String, List<String>> headers = new HashMap<>();
                headers.put("additional-header", Arrays.asList("foo"));
                return Response.builder().statusCode(200).headers(headers).build();
            });

            SampleHandler handler = new SampleHandler(app);

            Context context = mock(Context.class);

            // block_actions
            ApiGatewayRequest req = buildBlockActionsEvent();
            ApiGatewayResponse response = handler.handleRequest(req, context);

            assertEquals(200, response.getStatusCode());
            assertTrue(called.get());

            // message event
            ApiGatewayRequest message1 = buildMessageEvent();
            ApiGatewayResponse message1Response = handler.handleRequest(message1, context);
            assertEquals(404, message1Response.getStatusCode());
            assertEquals(0, handler.messageCount.get());

            // enable other listeners
            handler.enableEventSubscription();

            // message event
            ApiGatewayRequest message2 = buildMessageEvent();
            ApiGatewayResponse message2Response = handler.handleRequest(message2, context);
            assertEquals(200, message2Response.getStatusCode());
            assertEquals(1, handler.messageCount.get());

        } finally {
            slackApiServer.stop();
        }
    }

    ApiGatewayRequest buildBlockActionsEvent() throws UnsupportedEncodingException {
        ApiGatewayRequest req = new ApiGatewayRequest();
        initProperties(req);
        Map<String, String> headers = new HashMap<>();
        String timestamp = String.valueOf((System.currentTimeMillis() / 1000));
        String blockActionsPayload = "{\n" +
                "  \"type\": \"block_actions\",\n" +
                "  \"team\": {\n" +
                "    \"id\": \"\"\n" +
                "  },\n" +
                "  \"user\": {\n" +
                "    \"id\": \"\"\n" +
                "  },\n" +
                "  \"api_app_id\": \"\",\n" +
                "  \"token\": \"\",\n" +
                "  \"container\": {\n" +
                "  },\n" +
                "  \"trigger_id\": \"\",\n" +
                "  \"channel\": {\n" +
                "    \"id\": \"\"\n" +
                "  },\n" +
                "  \"message\": {\n" +
                "    \"type\": \"\",\n" +
                "    \"subtype\": \"\",\n" +
                "    \"team\": \"\",\n" +
                "    \"channel\": \"\",\n" +
                "    \"user\": \"\",\n" +
                "    \"username\": \"\",\n" +
                "    \"text\": \"\",\n" +
                "    \"blocks\": []\n" +
                "  }," +
                "  \"actions\":[{\n" +
                "    \"type\":\"button\",\n" +
                "    \"block_id\":\"two-block\",\n" +
                "    \"action_id\":\"start-event-subscription\",\n" +
                "    \"text\":{\n" +
                "      \"type\":\"plain_text\",\n" +
                "      \"text\":\"Let's Go!\",\n" +
                "      \"emoji\":true\n" +
                "    },\n" +
                "    \"action_ts\":\"1571318425.267782\"\n" +
                "  }]\n" +
                "}";
        String requestBody = "payload=" + URLEncoder.encode(blockActionsPayload, "UTF-8");
        SlackSignature.Generator signatureGenerator = new SlackSignature.Generator(signingSecret);
        headers.put(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp);
        headers.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, signatureGenerator.generate(timestamp, requestBody));
        req.setHeaders(headers);
        req.setRequestContext(initProperties(new RequestContext()));
        req.setBody(requestBody);
        return req;
    }

    ApiGatewayRequest buildMessageEvent() {
        ApiGatewayRequest req = new ApiGatewayRequest();
        initProperties(req);
        Map<String, String> headers = new HashMap<>();
        String timestamp = String.valueOf((System.currentTimeMillis() / 1000));
        MessagePayload payload = new MessagePayload();
        payload.setType("event_callback");
        payload.setTeamId("T123");
        MessageEvent message = new MessageEvent();
        message.setText("Hello");
        message.setTs("123.123");
        message.setTeam("T123");
        message.setUser("U123");
        payload.setEvent(message);
        String requestBody = GsonFactory.createSnakeCase().toJson(payload);
        SlackSignature.Generator signatureGenerator = new SlackSignature.Generator(signingSecret);
        headers.put(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp);
        headers.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, signatureGenerator.generate(timestamp, requestBody));
        req.setHeaders(headers);
        req.setRequestContext(initProperties(new RequestContext()));
        req.setBody(requestBody);
        return req;
    }

}
