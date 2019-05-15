package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.SubteamCreatedEvent;
import test_locally.unit.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class SubteamCreatedEventTest {

    @Test
    public void typeName() {
        assertThat(SubteamCreatedEvent.TYPE_NAME, is("subteam_created"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"subteam_created\",\n" +
                "    \"subteam\": {\n" +
                "        \"id\": \"S0615G0KT\",\n" +
                "        \"team_id\": \"T060RNRCH\",\n" +
                "        \"is_usergroup\": true,\n" +
                "        \"name\": \"Marketing Team\",\n" +
                "        \"description\": \"Marketing gurus, PR experts and product advocates.\",\n" +
                "        \"handle\": \"marketing-team\",\n" +
                "        \"is_external\": false,\n" +
                "        \"date_create\": 1446746793,\n" +
                "        \"date_update\": 1446746793,\n" +
                "        \"date_delete\": 0,\n" +
                "        \"auto_type\": null,\n" +
                "        \"created_by\": \"U060RNRCZ\",\n" +
                "        \"updated_by\": \"U060RNRCZ\",\n" +
                "        \"deleted_by\": null,\n" +
                "        \"prefs\": {\n" +
                "            \"channels\": [\n" +
                "            ],\n" +
                "            \"groups\": [\n" +
                "            ]\n" +
                "        },\n" +
                "        \"user_count\": \"0\"\n" +
                "    }\n" +
                "}";
        SubteamCreatedEvent event = GsonFactory.createSnakeCase().fromJson(json, SubteamCreatedEvent.class);
        assertThat(event.getType(), is("subteam_created"));
        assertThat(event.getSubteam().getId(), is("S0615G0KT"));
        assertThat(event.getSubteam().isUsergroup(), is(true));
        assertThat(event.getSubteam().isExternal(), is(false));
        assertThat(event.getSubteam().getHandle(), is("marketing-team"));
        assertThat(event.getSubteam().getDateCreate(), is(1446746793));
        assertThat(event.getSubteam().getAutoType(), is(nullValue()));
        assertThat(event.getSubteam().getPrefs().getChannels(), is(notNullValue()));
        assertThat(event.getSubteam().getPrefs().getGroups(), is(notNullValue()));
        assertThat(event.getSubteam().getUserCount(), is("0"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        SubteamCreatedEvent event = new SubteamCreatedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"subteam_created\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}