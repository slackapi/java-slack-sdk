package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.PinAddedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class PinAddedEventTest {

    @Test
    public void typeName() {
        assertThat(PinAddedEvent.TYPE_NAME, is("pin_added"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"pin_added\",\n" +
                "    \"user\": \"U024BE7LH\",\n" +
                "    \"channel_id\": \"C02ELGNBH\",\n" +
                "    \"item\": {\n" +
                "    },\n" +
                "    \"event_ts\": \"1360782804.083113\"\n" +
                "}";
        PinAddedEvent event = GsonFactory.createSnakeCase().fromJson(json, PinAddedEvent.class);
        assertThat(event.getType(), is("pin_added"));
        assertThat(event.getUser(), is("U024BE7LH"));
        assertThat(event.getChannelId(), is("C02ELGNBH"));
        assertThat(event.getItem(), is(notNullValue()));
        assertThat(event.getEventTs(), is("1360782804.083113"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        PinAddedEvent event = new PinAddedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"pin_added\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
