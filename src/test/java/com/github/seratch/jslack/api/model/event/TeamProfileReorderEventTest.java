package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TeamProfileReorderEventTest {

    @Test
    public void typeName() {
        assertThat(TeamProfileReorderEvent.TYPE_NAME, is("team_profile_reorder"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"team_profile_reorder\",\n" +
                "    \"profile\": {\n" +
                "        \"fields\": [\n" +
                "            {\n" +
                "                \"id\": \"Xf06054AAA\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}\n";
        TeamProfileReorderEvent event = GsonFactory.createSnakeCase().fromJson(json, TeamProfileReorderEvent.class);
        assertThat(event.getType(), is("team_profile_reorder"));
        assertThat(event.getProfile(), is(notNullValue()));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        TeamProfileReorderEvent event = new TeamProfileReorderEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"team_profile_reorder\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}