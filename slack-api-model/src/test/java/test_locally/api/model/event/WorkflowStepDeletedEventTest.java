package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.WorkflowStepDeletedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WorkflowStepDeletedEventTest {

    @Test
    public void typeName() {
        assertThat(WorkflowStepDeletedEvent.TYPE_NAME, is("workflow_step_deleted"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "   \"type\": \"workflow_step_deleted\",\n" +
                "   \"workflow_id\": \"338772933017143757\",\n" +
                "   \"workflow_draft_configuration\": {\n" +
                "      \"version_id\": \"338776577011617206\",\n" +
                "      \"app_steps\": [\n" +
                "         {\n" +
                "            \"app_id\": \"A012VT792TC\",\n" +
                "            \"workflow_step_id\": \"def5f7a2-365f-42e9-8ab8-26f9e9716696\",\n" +
                "            \"callback_id\": \"take_two\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"app_id\": \"A012VT792TC\",\n" +
                "            \"workflow_step_id\": \"19dab8c3-c062-43f7-a912-a69da0cf0bc0\",\n" +
                "            \"callback_id\": \"take_three\"\n" +
                "         }\n" +
                "      ]\n" +
                "   },\n" +
                "   \"workflow_published_configuration\": {\n" +
                "      \"version_id\": \"338776445998336786\",\n" +
                "      \"app_steps\": [\n" +
                "         {\n" +
                "            \"app_id\": \"A012VT792TC\",\n" +
                "            \"workflow_step_id\": \"def5f7a2-365f-42e9-8ab8-26f9e9716696\",\n" +
                "            \"callback_id\": \"take_two\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"app_id\": \"A012VT792TC\",\n" +
                "            \"workflow_step_id\": \"19dab8c3-c062-43f7-a912-a69da0cf0bc0\",\n" +
                "            \"callback_id\": \"take_three\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"app_id\": \"A012VT792TC\",\n" +
                "            \"workflow_step_id\": \"e9f8c121-de29-4d37-9e00-5204663a1b4d\",\n" +
                "            \"callback_id\": \"take_four\"\n" +
                "         }\n" +
                "      ]\n" +
                "   },\n" +
                "   \"event_ts\": \"1611456175.413379\"\n" +
                "}";
        WorkflowStepDeletedEvent stepDeletedEvent = GsonFactory.createSnakeCase().fromJson(json, WorkflowStepDeletedEvent.class);
        assertThat(stepDeletedEvent.getType(), is("workflow_step_deleted"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        WorkflowStepDeletedEvent stepDeletedEvent = new WorkflowStepDeletedEvent();
        stepDeletedEvent.setWorkflowId("338772933017143757");
        stepDeletedEvent.setEventTs("1611456175.413379");
        String generatedJson = gson.toJson(stepDeletedEvent);
        String expectedJson = "{\"type\":\"workflow_step_deleted\",\"workflow_id\":\"338772933017143757\",\"event_ts\":\"1611456175.413379\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
