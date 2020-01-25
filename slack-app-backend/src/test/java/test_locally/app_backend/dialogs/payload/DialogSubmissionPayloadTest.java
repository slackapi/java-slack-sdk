package test_locally.app_backend.dialogs.payload;

import com.github.seratch.jslack.app_backend.dialogs.payload.DialogSubmissionPayload;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DialogSubmissionPayloadTest {

    public static final String JSON = "{\n" +
            "    \"type\": \"dialog_submission\",\n" +
            "    \"submission\": {\n" +
            "        \"name\": \"Sigourney Dreamweaver\",\n" +
            "        \"email\": \"sigdre@example.com\",\n" +
            "        \"phone\": \"+1 800-555-1212\",\n" +
            "        \"meal\": \"burrito\",\n" +
            "        \"comment\": \"No sour cream please\",\n" +
            "        \"team_channel\": \"C0LFFBKPB\",\n" +
            "        \"who_should_sing\": \"U0MJRG1AL\"\n" +
            "    },\n" +
            "    \"callback_id\": \"employee_offsite_1138b\",\n" +
            "    \"state\": \"vegetarian\",\n" +
            "    \"team\": {\n" +
            "        \"id\": \"T1ABCD2E12\",\n" +
            "        \"domain\": \"coverbands\"\n" +
            "    },\n" +
            "    \"user\": {\n" +
            "        \"id\": \"W12A3BCDEF\",\n" +
            "        \"name\": \"dreamweaver\"\n" +
            "    },\n" +
            "    \"channel\": {\n" +
            "        \"id\": \"C1AB2C3DE\",\n" +
            "        \"name\": \"coverthon-1999\"\n" +
            "    },\n" +
            "    \"action_ts\": \"936893340.702759\",\n" +
            "    \"token\": \"M1AqUUw3FqayAbqNtsGMch72\",\n" +
            "    \"response_url\": \"https://hooks.slack.com/app/T012AB0A1/123456789/JpmK0yzoZDeRiqfeduTBYXWQ\"\n" +
            "}";

    private Gson gson = GsonFactory.createSnakeCase();

    @Test
    public void test() {
        DialogSubmissionPayload payload = gson.fromJson(JSON, DialogSubmissionPayload.class);
        assertThat(payload, is(notNullValue()));
        assertThat(payload.getType(), is("dialog_submission"));
        assertThat(payload.getCallbackId(), is("employee_offsite_1138b"));
        assertThat(payload.getState(), is("vegetarian"));
        assertThat(payload.getSubmission().get("name"), is("Sigourney Dreamweaver"));
        assertThat(payload.getTeam(), is(notNullValue()));
        assertThat(payload.getUser(), is(notNullValue()));
        assertThat(payload.getChannel(), is(notNullValue()));
        assertThat(payload.getActionTs(), is("936893340.702759"));
        assertThat(payload.getToken(), is("M1AqUUw3FqayAbqNtsGMch72"));
        assertThat(payload.getResponseUrl(), is("https://hooks.slack.com/app/T012AB0A1/123456789/JpmK0yzoZDeRiqfeduTBYXWQ"));
    }

}
