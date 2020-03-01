package test_locally;

import com.slack.api.bolt.aws_lambda.request.PayloadExtractor;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PayloadExtractorTest {

    @Test
    public void extractPayloadJsonAsString_null() {
        String result = new PayloadExtractor().extractPayloadJsonAsString(null);
        assertNull(result);
    }

    @Test
    public void extractPayloadJsonAsString_no_payload() {
        String result = new PayloadExtractor().extractPayloadJsonAsString("something=great");
        assertNull(result);
    }

    @Test
    public void extractPayloadJsonAsString() throws IOException {
        List<String> lines = Files.readAllLines(new File("../json-logs/samples/aws/ApiGatewayRequest.json").toPath());
        String json = lines.stream().collect(Collectors.joining("\n"));
        String request = "foo=bar&payload=" + URLEncoder.encode(json, "UTF-8") + "&baz=123";
        String result = new PayloadExtractor().extractPayloadJsonAsString(request);
        assertEquals(json, result);
    }
}
