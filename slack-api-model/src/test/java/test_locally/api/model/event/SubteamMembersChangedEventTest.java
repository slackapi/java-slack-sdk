package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.SubteamMembersChangedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SubteamMembersChangedEventTest {

    @Test
    public void typeName() {
        assertThat(SubteamMembersChangedEvent.TYPE_NAME, is("subteam_members_changed"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"subteam_members_changed\",\n" +
                "    \"subteam_id\": \"S0614TZR7\",\n" +
                "    \"team_id\": \"T060RNRCH\",\n" +
                "    \"date_previous_update\": 1446670362,\n" +
                "    \"date_update\": 1492906952,\n" +
                "    \"added_users\": [\n" +
                "       \"U060RNRCZ\",\n" +
                "       \"U060ULRC0\",\n" +
                "       \"U061309JM\"\n" +
                "    ],\n" +
                "    \"added_users_count\": \"3\",\n" +
                "    \"removed_users\": [\n" +
                "       \"U06129G2V\"\n" +
                "    ],\n" +
                "    \"removed_users_count\": \"1\"\n" +
                "}\n";
        SubteamMembersChangedEvent event = GsonFactory.createSnakeCase().fromJson(json, SubteamMembersChangedEvent.class);
        assertThat(event.getType(), is("subteam_members_changed"));
        assertThat(event.getSubteamId(), is("S0614TZR7"));
        assertThat(event.getTeamId(), is("T060RNRCH"));
        assertThat(event.getDatePreviousUpdate(), is(1446670362));
        assertThat(event.getDateUpdate(), is(1492906952));
        assertThat(event.getAddedUsers().size(), is(3));
        assertThat(event.getAddedUsersCount(), is(3));
        assertThat(event.getRemovedUsers().size(), is(1));
        assertThat(event.getRemovedUsersCount(), is(1));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        SubteamMembersChangedEvent event = new SubteamMembersChangedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"subteam_members_changed\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
