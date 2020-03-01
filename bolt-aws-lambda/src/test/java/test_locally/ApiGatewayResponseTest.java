package test_locally;

import com.slack.api.SlackConfig;
import com.slack.api.bolt.aws_lambda.response.ApiGatewayResponse;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static util.ObjectInitializer.initProperties;

public class ApiGatewayResponseTest {

    @Test
    public void dump() throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        ApiGatewayResponse response = new ApiGatewayResponse(20, "body", headers, false);
        initProperties(response);

        SlackConfig config = new SlackConfig();
        config.setPrettyResponseLoggingEnabled(true);
        String json = GsonFactory.createCamelCase(config).toJson(response);

        Path filePath = new File("../json-logs/samples/aws/ApiGatewayResponse.json").toPath();
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, json.getBytes(UTF_8));
    }

    @Test
    public void build302Response() {
        ApiGatewayResponse response = ApiGatewayResponse.build302Response("https://www.example.com/");
        assertEquals(302, response.getStatusCode());
    }

}
