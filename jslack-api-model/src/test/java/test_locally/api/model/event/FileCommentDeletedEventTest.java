package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.FileCommentDeletedEvent;
import test_locally.unit.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FileCommentDeletedEventTest {

    @Test
    public void typeName() {
        assertThat(FileCommentDeletedEvent.TYPE_NAME, is("file_comment_deleted"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"file_comment_deleted\",\n" +
                "    \"comment\": \"Fc67890\",\n" +
                "    \"file_id\": \"F2147483862\",\n" +
                "    \"file\": {\n" +
                "        \"id\": \"F2147483862\"\n" +
                "    }\n" +
                "}";
        FileCommentDeletedEvent event = GsonFactory.createSnakeCase().fromJson(json, FileCommentDeletedEvent.class);
        assertThat(event.getType(), is("file_comment_deleted"));
        assertThat(event.getComment(), is("Fc67890"));
        assertThat(event.getFileId(), is("F2147483862"));
        assertThat(event.getFile().getId(), is("F2147483862"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        FileCommentDeletedEvent event = new FileCommentDeletedEvent();
        event.setFileId("123");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"file_comment_deleted\",\"file_id\":\"123\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}