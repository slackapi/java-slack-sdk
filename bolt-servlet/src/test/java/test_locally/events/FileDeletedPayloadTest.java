package test_locally.events;

import com.google.gson.Gson;
import com.slack.api.app_backend.events.payload.FileDeletedPayload;
import com.slack.api.util.json.GsonFactory;
import config.SlackTestConfig;
import org.junit.Test;
import util.FileReader;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class FileDeletedPayloadTest {

    FileReader fileReader = new FileReader("../json-logs/samples/events");
    SlackTestConfig config = SlackTestConfig.getInstance();
    Gson gson = GsonFactory.createSnakeCase(config.getConfig());

    @Test
    public void test() throws IOException {
        String json = fileReader.readWholeAsString("/" + FileDeletedPayload.class.getSimpleName() + ".json");
        FileDeletedPayload payload = gson.fromJson(json, FileDeletedPayload.class);
        assertNotNull(payload);
    }
}
