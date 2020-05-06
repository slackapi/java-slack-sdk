package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.SubteamUpdatedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SubteamUpdatedEventTest {

    @Test
    public void typeName() {
        assertThat(SubteamUpdatedEvent.TYPE_NAME, is("subteam_updated"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"subteam_updated\",\n" +
                "    \"subteam\": {\n" +
                "       \"id\": \"S0614TZR7\",\n" +
                "       \"team_id\": \"T060RNRCH\",\n" +
                "       \"is_usergroup\": true,\n" +
                "       \"name\": \"Team Admins\",\n" +
                "       \"description\": \"A group of all Administrators on your team.\",\n" +
                "       \"handle\": \"admins\",\n" +
                "       \"is_external\": false,\n" +
                "       \"date_create\": 1446598059,\n" +
                "       \"date_update\": 1446670362,\n" +
                "       \"date_delete\": 0,\n" +
                "       \"auto_type\": \"admin\",\n" +
                "       \"created_by\": \"USLACKBOT\",\n" +
                "       \"updated_by\": \"U060RNRCZ\",\n" +
                "       \"deleted_by\": null,\n" +
                "       \"prefs\": {\n" +
                "           \"channels\": [\n" +
                "           ],\n" +
                "           \"groups\": [\n" +
                "           ]\n" +
                "       },\n" +
                "       \"users\": [\n" +
                "           \"U060RNRCZ\",\n" +
                "           \"U060ULRC0\",\n" +
                "           \"U06129G2V\",\n" +
                "           \"U061309JM\"\n" +
                "       ],\n" +
                "       \"user_count\": \"4\"\n" +
                "    }\n" +
                "}";
        SubteamUpdatedEvent event = GsonFactory.createSnakeCase().fromJson(json, SubteamUpdatedEvent.class);
        assertThat(event.getType(), is("subteam_updated"));
        assertThat(event.getSubteam().getId(), is("S0614TZR7"));
        assertThat(event.getSubteam().getUserCount(), is(4));
        assertThat(event.getSubteam().getUsers().size(), is(4));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        SubteamUpdatedEvent event = new SubteamUpdatedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"subteam_updated\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
