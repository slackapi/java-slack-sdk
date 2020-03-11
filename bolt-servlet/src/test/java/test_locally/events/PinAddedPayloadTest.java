package test_locally.events;

import com.google.gson.Gson;
import com.slack.api.app_backend.events.payload.PinAddedPayload;
import com.slack.api.util.json.GsonFactory;
import config.SlackTestConfig;
import org.junit.Test;
import util.FileReader;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class PinAddedPayloadTest {

    FileReader fileReader = new FileReader("../json-logs/samples/events");
    SlackTestConfig config = SlackTestConfig.getInstance();
    Gson gson = GsonFactory.createSnakeCase(config.getConfig());

    @Test
    public void test() throws IOException {
        String json = fileReader.readWholeAsString("/" + PinAddedPayload.class.getSimpleName() + ".json");
        PinAddedPayload payload = gson.fromJson(json, PinAddedPayload.class);
        assertNotNull(payload);
    }
}
