package test_locally;

import com.slack.api.SlackConfig;
import com.slack.api.bolt.aws_lambda.request.ApiGatewayRequest;
import com.slack.api.bolt.aws_lambda.request.Identity;
import com.slack.api.bolt.aws_lambda.request.RequestContext;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static util.ObjectInitializer.initProperties;

public class ApiGatewayRequestTest {

    @Test
    public void dump() throws IOException {
        ApiGatewayRequest request = new ApiGatewayRequest();
        initProperties(request);
        request.setResource("resource");
        request.setPath("/foo");
        request.setHttpMethod("POST");
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Slack-Request-Timestamp", "1531420618");
        headers.put("X-Slack-Signature", "v0=a2114d57b48eac39b9ad189dd8316235a7b4a8d21a10bd27519666489c69b503");
        request.setHeaders(headers);
        Map<String, String> query = new HashMap<>();
        query.put("code", "123");
        request.setQueryStringParameters(query);
        Map<String, String> stageValues = new HashMap<>();
        stageValues.put("level", "1");
        request.setStageVariables(stageValues);
        RequestContext context = new RequestContext();
        initProperties(context);
        context.setIdentity(initProperties(new Identity()));

        SlackConfig config = new SlackConfig();
        config.setPrettyResponseLoggingEnabled(true);
        String json = GsonFactory.createCamelCase(config).toJson(request);

        Path filePath = new File("../json-logs/samples/aws/ApiGatewayRequest.json").toPath();
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, json.getBytes(UTF_8));
    }

}
