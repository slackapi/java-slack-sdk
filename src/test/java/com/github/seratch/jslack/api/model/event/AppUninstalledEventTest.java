package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AppUninstalledEventTest {

    @Test
    public void typeName() {
        assertThat(AppUninstalledEvent.TYPE_NAME, is("app_uninstalled"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"app_uninstalled\"\n" +
                "}";
        AppUninstalledEvent event = GsonFactory.createSnakeCase().fromJson(json, AppUninstalledEvent.class);
        assertThat(event.getType(), is("app_uninstalled"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        AppUninstalledEvent event = new AppUninstalledEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"app_uninstalled\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}