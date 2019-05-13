package test_locally.app_backend.dialogs.payload;

import com.github.seratch.jslack.app_backend.dialogs.payload.DialogSuggestionPayload;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class DialogSuggestionPayloadTest {

    public static final String JSON = "{\n" +
            "  \"type\": \"dialog_suggestion\",\n" +
            "  \"token\": \"W3VDvuzi2nRLsiaDOsmJranO\",\n" +
            "  \"action_ts\": \"1528203589.238335\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T24BK35ML\",\n" +
            "    \"domain\": \"hooli-hq\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U900MV5U7\",\n" +
            "    \"name\": \"gbelson\"\n" +
            "  },\n" +
            "  \"channel\": {\n" +
            "    \"id\": \"C012AB3CD\",\n" +
            "    \"name\": \"triage-platform\"\n" +
            "  },\n" +
            "  \"name\": \"external_data\",\n" +
            "  \"value\": \"something\",\n" +
            "  \"callback_id\": \"bugs\"\n" +
            "}";

    private Gson gson = GsonFactory.createSnakeCase();

    @Test
    public void test() {
        DialogSuggestionPayload payload = gson.fromJson(JSON, DialogSuggestionPayload.class);
        assertThat(payload, is(notNullValue()));
        assertThat(payload.getType(), is("dialog_suggestion"));
        assertThat(payload.getToken(), is("W3VDvuzi2nRLsiaDOsmJranO"));
        assertThat(payload.getActionTs(), is("1528203589.238335"));
        assertThat(payload.getTeam(), is(notNullValue()));
        assertThat(payload.getUser(), is(notNullValue()));
        assertThat(payload.getChannel(), is(notNullValue()));
        assertThat(payload.getName(), is("external_data"));
        assertThat(payload.getValue(), is("something"));
        assertThat(payload.getCallbackId(), is("bugs"));
    }

}