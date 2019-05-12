package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.FileDeletedEvent;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FileDeletedEventTest {

    @Test
    public void typeName() {
        assertThat(FileDeletedEvent.TYPE_NAME, is("file_deleted"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"file_deleted\",\n" +
                "    \"file_id\": \"F2147483862\",\n" +
                "    \"event_ts\": \"1361482916.000004\"\n" +
                "}";
        FileDeletedEvent event = GsonFactory.createSnakeCase().fromJson(json, FileDeletedEvent.class);
        assertThat(event.getType(), is("file_deleted"));
        assertThat(event.getFileId(), is("F2147483862"));
        assertThat(event.getEventTs(), is("1361482916.000004"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        FileDeletedEvent event = new FileDeletedEvent();
        event.setFileId("123");
        event.setEventTs("234");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"file_deleted\",\"file_id\":\"123\",\"event_ts\":\"234\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}