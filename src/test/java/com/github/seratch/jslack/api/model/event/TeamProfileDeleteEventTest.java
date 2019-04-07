package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TeamProfileDeleteEventTest {

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"team_profile_delete\",\n" +
                "    \"profile\": {\n" +
                "        \"fields\": [\n" +
                "            {\n" +
                "                \"id\": \"Xf06054AAA\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}\n";
        TeamProfileDeleteEvent event = GsonFactory.createSnakeCase().fromJson(json, TeamProfileDeleteEvent.class);
        assertThat(event.getType(), is("team_profile_delete"));
        assertThat(event.getProfile(), is(notNullValue()));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        TeamProfileDeleteEvent event = new TeamProfileDeleteEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"team_profile_delete\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}