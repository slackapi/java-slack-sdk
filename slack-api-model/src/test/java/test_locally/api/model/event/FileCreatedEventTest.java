package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.FileCreatedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileCreatedEventTest {

    @Test
    public void typeName() {
        assertThat(FileCreatedEvent.TYPE_NAME, is("file_created"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"file_created\",\n" +
                "    \"file_id\": \"F2147483862\",\n" +
                "    \"file\": {\n" +
                "        \"id\": \"F2147483862\"\n" +
                "    }\n" +
                "}";
        FileCreatedEvent event = GsonFactory.createSnakeCase().fromJson(json, FileCreatedEvent.class);
        assertThat(event.getType(), is("file_created"));
        assertThat(event.getFileId(), is("F2147483862"));
        assertThat(event.getFile().getId(), is("F2147483862"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        FileCreatedEvent event = new FileCreatedEvent();
        event.setFileId("123");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"file_created\",\"file_id\":\"123\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
