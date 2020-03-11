package test_locally.events;

import com.google.gson.Gson;
import com.slack.api.app_backend.events.payload.ChannelCreatedPayload;
import com.slack.api.util.json.GsonFactory;
import config.SlackTestConfig;
import org.junit.Test;
import util.FileReader;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class ChannelCreatedPayloadTest {

    FileReader fileReader = new FileReader("../json-logs/samples/events");
    SlackTestConfig config = SlackTestConfig.getInstance();
    Gson gson = GsonFactory.createSnakeCase(config.getConfig());

    @Test
    public void test() throws IOException {
        String json = fileReader.readWholeAsString("/" + ChannelCreatedPayload.class.getSimpleName() + ".json");
        ChannelCreatedPayload payload = gson.fromJson(json, ChannelCreatedPayload.class);
        assertNotNull(payload);
        assertFalse(payload.getEvent().getChannel().isChannel());
        assertFalse(payload.getEvent().getChannel().isShared());
        assertFalse(payload.getEvent().getChannel().isOrgShared());
    }
}
