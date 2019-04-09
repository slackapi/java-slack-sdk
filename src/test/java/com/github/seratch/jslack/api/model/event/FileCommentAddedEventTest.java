package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class FileCommentAddedEventTest {

    @Test
    public void typeName() {
        assertThat(FileCommentAddedEvent.TYPE_NAME, is("file_comment_added"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"file_comment_added\",\n" +
                "    \"comment\": {},\n" +
                "    \"file_id\": \"F2147483862\",\n" +
                "    \"file\": {\n" +
                "        \"id\": \"F2147483862\"\n" +
                "    }\n" +
                "}";
        FileCommentAddedEvent event = GsonFactory.createSnakeCase().fromJson(json, FileCommentAddedEvent.class);
        assertThat(event.getType(), is("file_comment_added"));
        assertThat(event.getComment(), is(notNullValue()));
        assertThat(event.getFileId(), is("F2147483862"));
        assertThat(event.getFile().getId(), is("F2147483862"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        FileCommentAddedEvent event = new FileCommentAddedEvent();
        event.setFileId("123");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"file_comment_added\",\"file_id\":\"123\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}