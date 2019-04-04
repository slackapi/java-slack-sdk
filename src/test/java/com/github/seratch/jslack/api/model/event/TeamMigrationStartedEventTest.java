package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TeamMigrationStartedEventTest {

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"team_migration_started\"\n" +
                "}";
        TeamMigrationStartedEvent event = GsonFactory.createSnakeCase().fromJson(json, TeamMigrationStartedEvent.class);
        assertThat(event.getType(), is("team_migration_started"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        TeamMigrationStartedEvent event = new TeamMigrationStartedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"team_migration_started\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}