package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TeamRenameEventTest {

    @Test
    public void typeName() {
        assertThat(TeamRenameEvent.TYPE_NAME, is("team_rename"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"team_rename\",\n" +
                "    \"name\": \"New Team Name Inc.\"\n" +
                "}";
        TeamRenameEvent event = GsonFactory.createSnakeCase().fromJson(json, TeamRenameEvent.class);
        assertThat(event.getType(), is("team_rename"));
        assertThat(event.getName(), is("New Team Name Inc."));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        TeamRenameEvent event = new TeamRenameEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"team_rename\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}