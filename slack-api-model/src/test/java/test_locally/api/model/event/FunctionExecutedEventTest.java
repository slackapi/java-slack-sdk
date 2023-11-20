package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.FunctionExecutedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FunctionExecutedEventTest {

    String JSON = "{\n" +
            "  \"type\": \"function_executed\",\n" +
            "  \"function\": {\n" +
            "    \"id\": \"Fn066C7U22JD\",\n" +
            "    \"callback_id\": \"hello\",\n" +
            "    \"title\": \"Hello\",\n" +
            "    \"description\": \"Hello world!\",\n" +
            "    \"type\": \"app\",\n" +
            "    \"input_parameters\": [\n" +
            "      {\n" +
            "        \"type\": \"number\",\n" +
            "        \"name\": \"amount\",\n" +
            "        \"description\": \"How many do you need?\",\n" +
            "        \"title\": \"Amount\",\n" +
            "        \"is_required\": false,\n" +
            "        \"hint\": \"How many do you need?\",\n" +
            "        \"maximum\": 10,\n" +
            "        \"minimum\": 1\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"slack#/types/user_id\",\n" +
            "        \"name\": \"user_id\",\n" +
            "        \"description\": \"Who to send it\",\n" +
            "        \"title\": \"User\",\n" +
            "        \"is_required\": true,\n" +
            "        \"hint\": \"Select a user in the workspace\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"string\",\n" +
            "        \"name\": \"message\",\n" +
            "        \"description\": \"Whatever you want to tell\",\n" +
            "        \"title\": \"Message\",\n" +
            "        \"is_required\": false,\n" +
            "        \"hint\": \"up to 100 characters\",\n" +
            "        \"maxLength\": 100,\n" +
            "        \"minLength\": 1\n" +
            "      }\n" +
            "    ],\n" +
            "    \"output_parameters\": [\n" +
            "      {\n" +
            "        \"type\": \"number\",\n" +
            "        \"name\": \"amount\",\n" +
            "        \"description\": \"How many do you need?\",\n" +
            "        \"title\": \"Amount\",\n" +
            "        \"is_required\": false,\n" +
            "        \"hint\": \"How many do you need?\",\n" +
            "        \"maximum\": 10,\n" +
            "        \"minimum\": 1\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"slack#/types/user_id\",\n" +
            "        \"name\": \"user_id\",\n" +
            "        \"description\": \"Who to send it\",\n" +
            "        \"title\": \"User\",\n" +
            "        \"is_required\": true,\n" +
            "        \"hint\": \"Select a user in the workspace\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"string\",\n" +
            "        \"name\": \"message\",\n" +
            "        \"description\": \"Whatever you want to tell\",\n" +
            "        \"title\": \"Message\",\n" +
            "        \"is_required\": false,\n" +
            "        \"hint\": \"up to 100 characters\",\n" +
            "        \"maxLength\": 100,\n" +
            "        \"minLength\": 1\n" +
            "      }\n" +
            "    ],\n" +
            "    \"app_id\": \"A065ZJM410S\",\n" +
            "    \"date_created\": 1700110468,\n" +
            "    \"date_updated\": 1700110470,\n" +
            "    \"date_deleted\": 0,\n" +
            "    \"form_enabled\": false\n" +
            "  },\n" +
            "  \"inputs\": {\n" +
            "    \"amount\": 1,\n" +
            "    \"message\": \"hey\",\n" +
            "    \"user_id\": \"U03E94MK0\"\n" +
            "  },\n" +
            "  \"function_execution_id\": \"Fx065S3T3W2K\",\n" +
            "  \"workflow_execution_id\": \"Wx0666KGEUQ2\",\n" +
            "  \"event_ts\": \"1700201360.208558\",\n" +
            "  \"bot_access_token\": \"xwfp-...\"\n" +
            "}";

    @Test
    public void deserialize() {
        Gson gson = GsonFactory.createSnakeCase();
        FunctionExecutedEvent event = gson.fromJson(JSON, FunctionExecutedEvent.class);
        assertThat(event, is(notNullValue()));
        assertThat(event.getFunction().getInputParameters().size(), is(3));
        assertThat(event.getInputs().get("amount").asInteger(), is(1));
        assertThat(event.getInputs().get("message").asString(), is("hey"));
        assertThat(event.getInputs().get("user_id").asString(), is("U03E94MK0"));
        assertThat(event.getFunctionExecutionId(), is("Fx065S3T3W2K"));
        assertThat(event.getBotAccessToken(), is("xwfp-..."));
    }
}
