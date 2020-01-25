package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.ChannelHistoryChangedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChannelHistoryChangedEventTest {

    @Test
    public void typeName() {
        assertThat(ChannelHistoryChangedEvent.TYPE_NAME, is("channel_history_changed"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"channel_history_changed\",\n" +
                "    \"latest\": \"1358877455.000010\",\n" +
                "    \"ts\": \"1361482916.000003\",\n" +
                "    \"event_ts\": \"1361482916.000004\"\n" +
                "}";
        ChannelHistoryChangedEvent event = GsonFactory.createSnakeCase().fromJson(json, ChannelHistoryChangedEvent.class);
        assertThat(event.getType(), is("channel_history_changed"));
        assertThat(event.getLatest(), is("1358877455.000010"));
        assertThat(event.getEventTs(), is("1361482916.000004"));
        assertThat(event.getTs(), is("1361482916.000003"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ChannelHistoryChangedEvent event = new ChannelHistoryChangedEvent();
        event.setTs("ts");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"channel_history_changed\",\"ts\":\"ts\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
