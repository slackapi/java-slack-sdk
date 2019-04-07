package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class FileCommentEditedEventTest {

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"file_comment_edited\",\n" +
                "    \"comment\": {},\n" +
                "    \"file_id\": \"F2147483862\",\n" +
                "    \"file\": {\n" +
                "        \"id\": \"F2147483862\"\n" +
                "    }\n" +
                "}";
        FileCommentEditedEvent event = GsonFactory.createSnakeCase().fromJson(json, FileCommentEditedEvent.class);
        assertThat(event.getType(), is("file_comment_edited"));
        assertThat(event.getComment(), is(notNullValue()));
        assertThat(event.getFileId(), is("F2147483862"));
        assertThat(event.getFile().getId(), is("F2147483862"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        FileCommentEditedEvent event = new FileCommentEditedEvent();
        event.setFileId("123");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"file_comment_edited\",\"file_id\":\"123\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}