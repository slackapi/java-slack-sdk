package test_locally;

import com.slack.api.SlackConfig;
import com.slack.api.bolt.aws_lambda.response.ApiGatewayResponse;
import com.slack.api.util.json.GsonFactory;
import kotlin.text.Charsets;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.*;
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

    @Test
    public void builder_binaryBody() {
        ApiGatewayResponse response = ApiGatewayResponse.builder()
                .binaryBody("{\"foo\":\"bar\"}".getBytes(Charsets.UTF_8))
                .build();
        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        assertEquals("eyJmb28iOiJiYXIifQ==", response.getBody());
        assertTrue(response.isIsBase64Encoded());
    }

    @Test
    public void builder_objectBody() {
        ApiGatewayResponse response = ApiGatewayResponse.builder()
                .objectBody(new Object())
                .build();
        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        assertEquals("{}", response.getBody());
        assertFalse(response.isIsBase64Encoded());
    }

}
