package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.FileSharedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FileSharedEventTest {

    @Test
    public void typeName() {
        assertThat(FileSharedEvent.TYPE_NAME, is("file_shared"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"file_shared\",\n" +
                "    \"file_id\": \"F2147483862\",\n" +
                "    \"file\": {\n" +
                "        \"id\": \"F2147483862\"\n" +
                "    }\n" +
                "}";
        FileSharedEvent event = GsonFactory.createSnakeCase().fromJson(json, FileSharedEvent.class);
        assertThat(event.getType(), is("file_shared"));
        assertThat(event.getFileId(), is("F2147483862"));
        assertThat(event.getFile().getId(), is("F2147483862"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        FileSharedEvent event = new FileSharedEvent();
        event.setFileId("123");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"file_shared\",\"file_id\":\"123\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}