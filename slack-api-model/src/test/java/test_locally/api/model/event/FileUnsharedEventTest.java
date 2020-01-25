package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.FileUnsharedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileUnsharedEventTest {

    @Test
    public void typeName() {
        assertThat(FileUnsharedEvent.TYPE_NAME, is("file_unshared"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"file_unshared\",\n" +
                "    \"file_id\": \"F2147483862\",\n" +
                "    \"file\": {\n" +
                "        \"id\": \"F2147483862\"\n" +
                "    }\n" +
                "}";
        FileUnsharedEvent event = GsonFactory.createSnakeCase().fromJson(json, FileUnsharedEvent.class);
        assertThat(event.getType(), is("file_unshared"));
        assertThat(event.getFileId(), is("F2147483862"));
        assertThat(event.getFile().getId(), is("F2147483862"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        FileUnsharedEvent event = new FileUnsharedEvent();
        event.setFileId("123");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"file_unshared\",\"file_id\":\"123\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
