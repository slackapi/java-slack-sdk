package test_locally.app_backend.dialogs.payload;

import com.github.seratch.jslack.app_backend.dialogs.payload.DialogCancellationPayload;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DialogCancellationPayloadTest {

    public static final String JSON = "{\n" +
            "  \"type\": \"dialog_cancellation\",\n" +
            "  \"token\": \"old-and-moldy-verification-token\",\n" +
            "  \"action_ts\": \"1542993577.333025\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T1ABCD2E12\",\n" +
            "    \"domain\": \"coverbands\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U2147483697\",\n" +
            "    \"name\": \"leepresson\"\n" +
            "  },\n" +
            "  \"channel\": {\n" +
            "    \"id\": \"C1AB2C3DE\",\n" +
            "    \"name\": \"graceland\"\n" +
            "  },\n" +
            "  \"callback_id\": \"best_elvis_impersonator_name\",\n" +
            "  \"response_url\": \"https://hooks.slack.com/app/T1ABCD2E12/486xxxxxx/jbF9HF\",\n" +
            "  \"state\": \"final_round\"\n" +
            "}";

    private Gson gson = GsonFactory.createSnakeCase();

    @Test
    public void test() {
        DialogCancellationPayload payload = gson.fromJson(JSON, DialogCancellationPayload.class);
        assertThat(payload, is(notNullValue()));
        assertThat(payload.getType(), is("dialog_cancellation"));
        assertThat(payload.getToken(), is("old-and-moldy-verification-token"));
        assertThat(payload.getActionTs(), is("1542993577.333025"));
        assertThat(payload.getTeam(), is(notNullValue()));
        assertThat(payload.getUser(), is(notNullValue()));
        assertThat(payload.getChannel(), is(notNullValue()));
        assertThat(payload.getCallbackId(), is("best_elvis_impersonator_name"));
        assertThat(payload.getResponseUrl(), is("https://hooks.slack.com/app/T1ABCD2E12/486xxxxxx/jbF9HF"));
        assertThat(payload.getState(), is("final_round"));
    }

}
