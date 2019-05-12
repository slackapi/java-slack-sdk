package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.FilePublicEvent;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FilePublicEventTest {

    @Test
    public void typeName() {
        assertThat(FilePublicEvent.TYPE_NAME, is("file_public"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"file_public\",\n" +
                "    \"file_id\": \"F2147483862\",\n" +
                "    \"file\": {\n" +
                "        \"id\": \"F2147483862\"\n" +
                "    }\n" +
                "}";
        FilePublicEvent event = GsonFactory.createSnakeCase().fromJson(json, FilePublicEvent.class);
        assertThat(event.getType(), is("file_public"));
        assertThat(event.getFileId(), is("F2147483862"));
        assertThat(event.getFile().getId(), is("F2147483862"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        FilePublicEvent event = new FilePublicEvent();
        event.setFileId("123");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"file_public\",\"file_id\":\"123\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}