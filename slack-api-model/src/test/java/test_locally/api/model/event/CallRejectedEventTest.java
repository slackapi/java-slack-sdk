package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.CallRejectedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CallRejectedEventTest {

    @Test
    public void typeName() {
        assertThat(CallRejectedEvent.TYPE_NAME, is("call_rejected"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "  \"type\": \"call_rejected\",\n" +
                "  \"call_id\": \"RL731AVEF\",\n" +
                "  \"user_id\": \"ULJS1TYR5\",\n" +
                "  \"channel_id\": \"DL5JN9K0T\",\n" +
                "  \"external_unique_id\": \"123-456-7890\"\n" +
                "}";
        CallRejectedEvent event = GsonFactory.createSnakeCase().fromJson(json, CallRejectedEvent.class);
        assertThat(event.getType(), is("call_rejected"));
        assertThat(event.getCallId(), is("RL731AVEF"));
        assertThat(event.getUserId(), is("ULJS1TYR5"));
        assertThat(event.getChannelId(), is("DL5JN9K0T"));
        assertThat(event.getExternalUniqueId(), is("123-456-7890"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        CallRejectedEvent event = new CallRejectedEvent();
        event.setCallId("RL731AVEF");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"call_rejected\",\"call_id\":\"RL731AVEF\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
