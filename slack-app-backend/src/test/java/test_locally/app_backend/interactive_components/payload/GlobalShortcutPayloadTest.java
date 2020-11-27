package test_locally.app_backend.interactive_components.payload;

import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.app_backend.interactive_components.payload.GlobalShortcutPayload;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GlobalShortcutPayloadTest {

    String json = "{\n" +
            "  \"type\": \"shortcut\",\n" +
            "  \"token\": \"xxx\",\n" +
            "  \"action_ts\": \"1606467247.592429\",\n" +
            "  \"team\": null,\n" +
            "  \"user\": {\n" +
            "    \"id\": \"W111\",\n" +
            "    \"username\": \"primary-owner\",\n" +
            "    \"team_id\": \"T111\"\n" +
            "  },\n" +
            "  \"is_enterprise_install\": true,\n" +
            "  \"enterprise\": {\n" +
            "    \"id\": \"E111\",\n" +
            "    \"name\": \"TestOrg\"\n" +
            "  },\n" +
            "  \"callback_id\": \"org-level-shortcut\",\n" +
            "  \"trigger_id\": \"111.222.xxx\"\n" +
            "}\n";

    @Test
    public void deserialize() {
        GlobalShortcutPayload payload = GsonFactory.createSnakeCase().fromJson(json, GlobalShortcutPayload.class);
        assertThat(payload.getType(), is("shortcut"));
        assertThat(payload.getEnterprise().getId(), is("E111"));
        assertThat(payload.getTeam(), is(nullValue()));
        assertThat(payload.isEnterpriseInstall(), is(true));
    }

}
