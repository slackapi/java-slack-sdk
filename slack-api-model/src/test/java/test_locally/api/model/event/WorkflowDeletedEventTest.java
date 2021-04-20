package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.WorkflowDeletedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WorkflowDeletedEventTest {

    @Test
    public void typeName() {
        assertThat(WorkflowDeletedEvent.TYPE_NAME, is("workflow_deleted"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"workflow_deleted\",\n" +
                "    \"workflow_id\": \"338772933017143757\",\n" +
                "    \"workflow_draft_configuration\": {\n" +
                "        \"version_id\": \"338776445998336786\",\n" +
                "        \"app_steps\": [\n" +
                "            {\n" +
                "                \"app_id\": \"A012VT792TC\",\n" +
                "                \"workflow_step_id\": \"def5f7a2-365f-42e9-8ab8-26f9e9716696\",\n" +
                "                \"callback_id\": \"take_two\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"app_id\": \"A012VT792TC\",\n" +
                "                \"workflow_step_id\": \"19dab8c3-c062-43f7-a912-a69da0cf0bc0\",\n" +
                "                \"callback_id\": \"take_three\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"event_ts\": \"1611456217.716570\"\n" +
                "}";
        WorkflowDeletedEvent deletedEvent = GsonFactory.createSnakeCase().fromJson(json, WorkflowDeletedEvent.class);
        assertThat(deletedEvent.getType(), is("workflow_deleted"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        WorkflowDeletedEvent deletedEvent = new WorkflowDeletedEvent();
        deletedEvent.setWorkflowId("338772933017143757");
        deletedEvent.setEventTs("1611456217.716570");
        String generatedJson = gson.toJson(deletedEvent);
        String expectedJson = "{\"type\":\"workflow_deleted\",\"workflow_id\":\"338772933017143757\",\"event_ts\":\"1611456217.716570\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
