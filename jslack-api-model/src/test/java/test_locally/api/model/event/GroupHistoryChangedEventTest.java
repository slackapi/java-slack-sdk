package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.GroupHistoryChangedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GroupHistoryChangedEventTest {

    @Test
    public void typeName() {
        assertThat(GroupHistoryChangedEvent.TYPE_NAME, is("group_history_changed"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"group_history_changed\",\n" +
                "    \"latest\": \"1358877455.000010\",\n" +
                "    \"ts\": \"1361482916.000003\",\n" +
                "    \"event_ts\": \"1361482916.000004\"\n" +
                "}";
        GroupHistoryChangedEvent event = GsonFactory.createSnakeCase().fromJson(json, GroupHistoryChangedEvent.class);
        assertThat(event.getType(), is("group_history_changed"));
        assertThat(event.getLatest(), is("1358877455.000010"));
        assertThat(event.getTs(), is("1361482916.000003"));
        assertThat(event.getEventTs(), is("1361482916.000004"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        GroupHistoryChangedEvent event = new GroupHistoryChangedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"group_history_changed\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}