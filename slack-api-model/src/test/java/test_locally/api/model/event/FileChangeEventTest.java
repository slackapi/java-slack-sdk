package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.FileChangeEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileChangeEventTest {

    @Test
    public void typeName() {
        assertThat(FileChangeEvent.TYPE_NAME, is("file_change"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"file_change\",\n" +
                "    \"file_id\": \"F2147483862\",\n" +
                "    \"file\": {\n" +
                "        \"id\": \"F2147483862\"\n" +
                "    }\n" +
                "}\n";
        FileChangeEvent event = GsonFactory.createSnakeCase().fromJson(json, FileChangeEvent.class);
        assertThat(event.getType(), is("file_change"));
        assertThat(event.getFileId(), is("F2147483862"));
        assertThat(event.getFile().getId(), is("F2147483862"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        FileChangeEvent event = new FileChangeEvent();
        event.setFileId("123");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"file_change\",\"file_id\":\"123\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
