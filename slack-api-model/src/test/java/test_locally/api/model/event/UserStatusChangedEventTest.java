package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.UserStatusChangedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserStatusChangedEventTest {

    @Test
    public void typeName() {
        assertThat(UserStatusChangedEvent.TYPE_NAME, is("user_status_changed"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "  \"user\": {\n" +
                "    \"id\": \"U111\",\n" +
                "    \"team_id\": \"T111\",\n" +
                "    \"name\": \"seratch\",\n" +
                "    \"deleted\": false,\n" +
                "    \"color\": \"9f69e7\",\n" +
                "    \"real_name\": \"Kaz\",\n" +
                "    \"tz\": \"Asia/Tokyo\",\n" +
                "    \"tz_label\": \"Japan Standard Time\",\n" +
                "    \"tz_offset\": 32400,\n" +
                "    \"profile\": {\n" +
                "      \"title\": \"\",\n" +
                "      \"phone\": \"1111\",\n" +
                "      \"skype\": \"skype-xxx\",\n" +
                "      \"real_name\": \"Kaz\",\n" +
                "      \"real_name_normalized\": \"Kaz\",\n" +
                "      \"display_name\": \"seratch\",\n" +
                "      \"display_name_normalized\": \"seratch\",\n" +
                "      \"fields\": {\n" +
                "        \"Xf019LT13Z16\": {\n" +
                "          \"value\": \"\",\n" +
                "          \"alt\": \"x\"\n" +
                "        },\n" +
                "        \"Xf038VF43ENP\": {\n" +
                "          \"value\": \"Tokyo\",\n" +
                "          \"alt\": \"\"\n" +
                "        },\n" +
                "        \"Xf038XRC0ESG\": {\n" +
                "          \"value\": \"2\",\n" +
                "          \"alt\": \"\"\n" +
                "        },\n" +
                "        \"Xf038VF4PFL3\": {\n" +
                "          \"value\": \"https://www.example.com\",\n" +
                "          \"alt\": \"test\"\n" +
                "        },\n" +
                "        \"Xf038NRGUU7Q\": {\n" +
                "          \"value\": \"U16UN6R6U\",\n" +
                "          \"alt\": \"\"\n" +
                "        },\n" +
                "        \"Xf038ET1DHMM\": {\n" +
                "          \"value\": \"xxxx\",\n" +
                "          \"alt\": \"\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"status_text\": \"Out sick\",\n" +
                "      \"status_emoji\": \":face_with_thermometer:\",\n" +
                "      \"status_emoji_display_info\": [],\n" +
                "      \"status_expiration\": 1652108399,\n" +
                "      \"avatar_hash\": \"66c61a28ede6\",\n" +
                "      \"image_original\": \"https://avatars.slack-edge.com/2022-05-07/xxx_original.jpg\",\n" +
                "      \"is_custom_image\": true,\n" +
                "      \"pronouns\": \"he/him/his\",\n" +
                "      \"huddle_state\": \"default_unset\",\n" +
                "      \"first_name\": \"Kaz\",\n" +
                "      \"last_name\": \"\",\n" +
                "      \"image_24\": \"https://avatars.slack-edge.com/2022-05-07/xxx_24.jpg\",\n" +
                "      \"image_32\": \"https://avatars.slack-edge.com/2022-05-07/xxx_32.jpg\",\n" +
                "      \"image_48\": \"https://avatars.slack-edge.com/2022-05-07/xxx_48.jpg\",\n" +
                "      \"image_72\": \"https://avatars.slack-edge.com/2022-05-07/xxx_72.jpg\",\n" +
                "      \"image_192\": \"https://avatars.slack-edge.com/2022-05-07/xxx_192.jpg\",\n" +
                "      \"image_512\": \"https://avatars.slack-edge.com/2022-05-07/xxx_512.jpg\",\n" +
                "      \"image_1024\": \"https://avatars.slack-edge.com/2022-05-07/xxx_1024.jpg\",\n" +
                "      \"status_text_canonical\": \"Out sick\",\n" +
                "      \"team\": \"T111\"\n" +
                "    },\n" +
                "    \"is_admin\": true,\n" +
                "    \"is_owner\": true,\n" +
                "    \"is_primary_owner\": true,\n" +
                "    \"is_restricted\": false,\n" +
                "    \"is_ultra_restricted\": false,\n" +
                "    \"is_bot\": false,\n" +
                "    \"is_app_user\": false,\n" +
                "    \"updated\": 1652050264,\n" +
                "    \"is_email_confirmed\": true,\n" +
                "    \"who_can_share_contact_card\": \"EVERYONE\",\n" +
                "    \"locale\": \"en-US\"\n" +
                "  },\n" +
                "  \"cache_ts\": 1652050264,\n" +
                "  \"type\": \"user_status_changed\",\n" +
                "  \"event_ts\": \"1652050264.234000\"\n" +
                "}";
        UserStatusChangedEvent event = GsonFactory.createSnakeCase().fromJson(json, UserStatusChangedEvent.class);
        assertThat(event.getType(), is("user_status_changed"));
        assertThat(event.getUser(), is(notNullValue()));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        UserStatusChangedEvent event = new UserStatusChangedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"user_status_changed\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
