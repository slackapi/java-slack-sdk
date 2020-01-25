package test_locally.app_backend.message_actions.payload;

import com.github.seratch.jslack.app_backend.message_actions.payload.MessageActionPayload;
import com.slack.api.util.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageActionPayloadTest {

    public static final String JSON = "{\n" +
            "  \"token\": \"Nj2rfC2hU8mAfgaJLemZgO7H\",\n" +
            "  \"callback_id\": \"chirp_message\",\n" +
            "  \"type\": \"message_action\",\n" +
            "  \"trigger_id\": \"13345224609.8534564800.6f8ab1f53e13d0cd15f96106292d5536\",\n" +
            "  \"response_url\": \"https://hooks.slack.com/app-actions/T0MJR11A4/21974584944/yk1S9ndf35Q1flupVG5JbpM6\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T0MJRM1A7\",\n" +
            "    \"domain\": \"pandamonium\"\n" +
            "  },\n" +
            "  \"channel\": {\n" +
            "    \"id\": \"D0LFFBKLZ\",\n" +
            "    \"name\": \"cats\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U0D15K92L\",\n" +
            "    \"name\": \"dr_maomao\"\n" +
            "  },\n" +
            "  \"message\": {\n" +
            "    \"type\": \"message\",\n" +
            "    \"user\": \"U0MJRG1AL\",\n" +
            "    \"ts\": \"1516229207.000133\",\n" +
            "    \"text\": \"World's smallest big cat! <https://youtube.com/watch?v=W86cTIoMv2U>\"\n" +
            "  }\n" +
            "}";

    private Gson gson = GsonFactory.createSnakeCase();

    @Test
    public void test() {
        MessageActionPayload payload = gson.fromJson(JSON, MessageActionPayload.class);
        assertThat(payload, is(notNullValue()));
        assertThat(payload.getType(), is("message_action"));
        assertThat(payload.getCallbackId(), is("chirp_message"));
        assertThat(payload.getToken(), is("Nj2rfC2hU8mAfgaJLemZgO7H"));
        assertThat(payload.getTriggerId(), is("13345224609.8534564800.6f8ab1f53e13d0cd15f96106292d5536"));
        assertThat(payload.getResponseUrl(), is("https://hooks.slack.com/app-actions/T0MJR11A4/21974584944/yk1S9ndf35Q1flupVG5JbpM6"));
        assertThat(payload.getTeam(), is(notNullValue()));
        assertThat(payload.getUser(), is(notNullValue()));
        assertThat(payload.getChannel(), is(notNullValue()));
        assertThat(payload.getMessage(), is(notNullValue()));
    }
}
