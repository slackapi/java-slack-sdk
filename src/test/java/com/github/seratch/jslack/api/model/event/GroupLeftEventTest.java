package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GroupLeftEventTest {

    @Test
    public void typeName() {
        assertThat(GroupLeftEvent.TYPE_NAME, is("group_left"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"group_left\",\n" +
                "    \"channel\": \"G02ELGNBH\"\n" +
                "}";
        GroupLeftEvent event = GsonFactory.createSnakeCase().fromJson(json, GroupLeftEvent.class);
        assertThat(event.getType(), is("group_left"));
        assertThat(event.getChannel(), is("G02ELGNBH"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        GroupLeftEvent event = new GroupLeftEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"group_left\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}