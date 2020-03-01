package test_locally;

import com.amazonaws.services.lambda.runtime.Context;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.aws_lambda.SlackApiLambdaHandler;
import com.slack.api.bolt.aws_lambda.request.ApiGatewayRequest;
import com.slack.api.bolt.aws_lambda.request.RequestContext;
import com.slack.api.bolt.aws_lambda.response.ApiGatewayResponse;
import org.junit.Test;
import util.AuthTestMockServer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static util.ObjectInitializer.initProperties;

public class SlackApiLambdaHandlerTest {

    String signingSecret = "secret";

    @Test
    public void invalidRequest() {
        App app = new App(AppConfig.builder()
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .signingSecret(signingSecret)
                .build()
        );
        SlackApiLambdaHandler handler = new SlackApiLambdaHandler(app) {
            @Override
            protected boolean isWarmupRequest(ApiGatewayRequest awsReq) {
                return false;
            }
        };
        ApiGatewayRequest req = new ApiGatewayRequest();
        initProperties(req);
        req.setRequestContext(initProperties(new RequestContext()));
        req.setBody("payload={}");

        Context context = mock(Context.class);
        ApiGatewayResponse response = handler.handleRequest(req, context);
        assertEquals(400, response.getStatusCode());
    }

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
            "    \"action_id\":\"take-action\",\n" +
            "    \"text\":{\n" +
            "      \"type\":\"plain_text\",\n" +
            "      \"text\":\"Let's Go!\",\n" +
            "      \"emoji\":true\n" +
            "    },\n" +
            "    \"action_ts\":\"1571318425.267782\"\n" +
            "  }]\n" +
            "}";

    @Test
    public void warmup() {
        App app = new App(AppConfig.builder()
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .signingSecret(signingSecret)
                .build()
        );
        SlackApiLambdaHandler handler = new SlackApiLambdaHandler(app) {
            @Override
            protected boolean isWarmupRequest(ApiGatewayRequest awsReq) {
                return awsReq.getBody().equals("warmup");
            }
        };
        ApiGatewayRequest req = new ApiGatewayRequest();
        initProperties(req);
        req.setRequestContext(initProperties(new RequestContext()));
        req.setBody("warmup");
        Context context = mock(Context.class);
        ApiGatewayResponse response = handler.handleRequest(req, context);
        assertNull(response);
    }

    @Test
    public void signature_error() throws UnsupportedEncodingException {
        App app = new App(AppConfig.builder()
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .signingSecret(signingSecret)
                .build()
        );
        SlackApiLambdaHandler handler = new SlackApiLambdaHandler(app) {
            @Override
            protected boolean isWarmupRequest(ApiGatewayRequest awsReq) {
                return false;
            }
        };
        ApiGatewayRequest req = new ApiGatewayRequest();
        initProperties(req);
        req.setRequestContext(initProperties(new RequestContext()));
        req.setBody("payload=" + URLEncoder.encode(blockActionsPayload, "UTF-8"));
        Context context = mock(Context.class);
        ApiGatewayResponse response = handler.handleRequest(req, context);
        assertEquals(401, response.getStatusCode());
    }

    @Test
    public void valid_but_unhandled() throws Exception {
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

            // NOTE: no handlers in the App

            ApiGatewayRequest req = new ApiGatewayRequest();
            initProperties(req);
            Map<String, String> headers = new HashMap<>();
            String timestamp = String.valueOf((System.currentTimeMillis() / 1000));
            String requestBody = "payload=" + URLEncoder.encode(blockActionsPayload, "UTF-8");
            SlackSignature.Generator signatureGenerator = new SlackSignature.Generator(signingSecret);
            headers.put(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp);
            headers.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, signatureGenerator.generate(timestamp, requestBody));
            req.setHeaders(headers);
            req.setRequestContext(initProperties(new RequestContext()));
            req.setBody(requestBody);

            Context context = mock(Context.class);

            SlackApiLambdaHandler handler = new SlackApiLambdaHandler(app) {
                @Override
                protected boolean isWarmupRequest(ApiGatewayRequest awsReq) {
                    return false;
                }
            };
            ApiGatewayResponse response = handler.handleRequest(req, context);

            assertEquals(404, response.getStatusCode());

        } finally {
            slackApiServer.stop();
        }
    }

    @Test
    public void valid_but_handled() throws Exception {
        AuthTestMockServer slackApiServer = new AuthTestMockServer();
        slackApiServer.start();

        try {
            String signingSecret = "secret";
            SlackConfig slackConfig = new SlackConfig();
            slackConfig.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
            Slack slack = Slack.getInstance(slackConfig);
            App app = new App(AppConfig.builder().slack(slack)
                    .singleTeamBotToken(AuthTestMockServer.ValidToken)
                    .signingSecret(signingSecret)
                    .build()
            );

            AtomicBoolean called = new AtomicBoolean(false);
            app.blockAction("take-action", (req, ctx) -> {
                called.set(true);
                return ctx.ack();
            });

            ApiGatewayRequest req = new ApiGatewayRequest();
            initProperties(req);
            Map<String, String> headers = new HashMap<>();
            String timestamp = String.valueOf((System.currentTimeMillis() / 1000));
            String requestBody = "payload=" + URLEncoder.encode(blockActionsPayload, "UTF-8");
            SlackSignature.Generator signatureGenerator = new SlackSignature.Generator(signingSecret);
            headers.put(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp);
            headers.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, signatureGenerator.generate(timestamp, requestBody));
            req.setHeaders(headers);
            req.setRequestContext(initProperties(new RequestContext()));
            req.setBody(requestBody);

            Context context = mock(Context.class);

            SlackApiLambdaHandler handler = new SlackApiLambdaHandler(app) {
                @Override
                protected boolean isWarmupRequest(ApiGatewayRequest awsReq) {
                    return false;
                }
            };
            ApiGatewayResponse response = handler.handleRequest(req, context);

            assertEquals(200, response.getStatusCode());
            assertTrue(called.get());

        } finally {
            slackApiServer.stop();
        }
    }
}
