package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.SharedChannelInviteAcceptedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SharedChannelInviteAcceptedEventTest {

    @Test
    public void typeName() {
        assertThat(SharedChannelInviteAcceptedEvent.TYPE_NAME, is("shared_channel_invite_accepted"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "  \"type\": \"shared_channel_invite_accepted\",\n" +
                "  \"approval_required\": false,\n" +
                "  \"invite\": {\n" +
                "    \"id\": \"I028YDERZSQ\",\n" +
                "    \"date_created\": 1626876000,\n" +
                "    \"date_invalid\": 1628085600,\n" +
                "    \"inviting_team\": {\n" +
                "      \"id\": \"T12345678\",\n" +
                "      \"name\": \"Corgis\",\n" +
                "      \"icon\": {},\n" +
                "      \"is_verified\": false,\n" +
                "      \"domain\": \"corgis\",\n" +
                "      \"date_created\": 1480946400\n" +
                "    },\n" +
                "    \"inviting_user\": {\n" +
                "      \"id\": \"U12345678\",\n" +
                "      \"team_id\": \"T12345678\",\n" +
                "      \"name\": \"crus\",\n" +
                "      \"updated\": 1608081902,\n" +
                "      \"profile\": {\n" +
                "        \"real_name\": \"Corgis Rus\",\n" +
                "        \"display_name\": \"Corgis Rus\",\n" +
                "        \"real_name_normalized\": \"Corgis Rus\",\n" +
                "        \"display_name_normalized\": \"Corgis Rus\",\n" +
                "        \"team\": \"T12345678\",\n" +
                "        \"avatar_hash\": \"gcfh83a4c72k\",\n" +
                "        \"email\": \"corgisrus@slack-corp.com\",\n" +
                "        \"image_24\": \"https://placekitten.com/24/24\",\n" +
                "        \"image_32\": \"https://placekitten.com/32/32\",\n" +
                "        \"image_48\": \"https://placekitten.com/48/48\",\n" +
                "        \"image_72\": \"https://placekitten.com/72/72\",\n" +
                "        \"image_192\": \"https://placekitten.com/192/192\",\n" +
                "        \"image_512\": \"https://placekitten.com/512/512\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"recipient_email\": \"golden@doodle.com\",\n" +
                "    \"recipient_user_id\": \"U87654321\"\n" +
                "  },\n" +
                "  \"channel\": {\n" +
                "    \"id\": \"C12345678\",\n" +
                "    \"is_private\": false,\n" +
                "    \"is_im\": false,\n" +
                "    \"name\": \"test-slack-connect\"\n" +
                "  },\n" +
                "  \"teams_in_channel\": [\n" +
                "    {\n" +
                "      \"id\": \"T12345678\",\n" +
                "      \"name\": \"Corgis\",\n" +
                "      \"icon\": {},\n" +
                "      \"is_verified\": false,\n" +
                "      \"domain\": \"corgis\",\n" +
                "      \"date_created\": 1626789600\n" +
                "    }\n" +
                "  ],\n" +
                "  \"accepting_user\": {\n" +
                "    \"id\": \"U87654321\",\n" +
                "    \"team_id\": \"T87654321\",\n" +
                "    \"name\": \"golden\",\n" +
                "    \"updated\": 1624406113,\n" +
                "    \"profile\": {\n" +
                "      \"real_name\": \"Golden Doodle\",\n" +
                "      \"display_name\": \"Golden\",\n" +
                "      \"real_name_normalized\": \"Golden Doodle\",\n" +
                "      \"display_name_normalized\": \"Golden\",\n" +
                "      \"team\": \"T87654321\",\n" +
                "      \"avatar_hash\": \"g717728b118x\",\n" +
                "      \"email\": \"golden@doodle.com\",\n" +
                "      \"image_24\": \"https://placekitten.com/24/24\",\n" +
                "      \"image_32\": \"https://placekitten.com/32/32\",\n" +
                "      \"image_48\": \"https://placekitten.com/48/48\",\n" +
                "      \"image_72\": \"https://placekitten.com/72/72\",\n" +
                "      \"image_192\": \"https://placekitten.com/192/192\",\n" +
                "      \"image_512\": \"https://placekitten.com/512/512\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"event_ts\": \"1626877800.000000\"\n" +
                "}";
        SharedChannelInviteAcceptedEvent event = GsonFactory.createSnakeCase().fromJson(json, SharedChannelInviteAcceptedEvent.class);
        assertThat(event.getType(), is("shared_channel_invite_accepted"));
        assertThat(event.getEventTs(), is("1626877800.000000"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        SharedChannelInviteAcceptedEvent event = new SharedChannelInviteAcceptedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"shared_channel_invite_accepted\",\"approval_required\":false}";
        assertThat(generatedJson, is(expectedJson));
    }

}
