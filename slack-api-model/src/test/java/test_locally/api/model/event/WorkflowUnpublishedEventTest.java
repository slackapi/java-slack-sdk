package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.WorkflowUnpublishedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WorkflowUnpublishedEventTest {

    @Test
    public void typeName() {
        assertThat(WorkflowUnpublishedEvent.TYPE_NAME, is("workflow_unpublished"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "   \"type\": \"workflow_unpublished\",\n" +
                "   \"workflow_id\": \"338772933017143757\",\n" +
                "   \"workflow_draft_configuration\":{\n" +
                "      \"version_id\": \"338776445998336786\",\n" +
                "      \"app_steps\":[\n" +
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
                "   \"event_ts\": \"1611456137.967843\"\n" +
                "}";
        WorkflowUnpublishedEvent unpublishedEvent = GsonFactory.createSnakeCase().fromJson(json, WorkflowUnpublishedEvent.class);
        assertThat(unpublishedEvent.getType(), is("workflow_unpublished"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        WorkflowUnpublishedEvent unpublishedEvent = new WorkflowUnpublishedEvent();
        unpublishedEvent.setWorkflowId("338772933017143757");
        unpublishedEvent.setEventTs("1611456137.967843");
        String generateJson = gson.toJson(unpublishedEvent);
        String expectedJson = "{\"type\":\"workflow_unpublished\",\"workflow_id\":\"338772933017143757\",\"event_ts\":\"1611456137.967843\"}";
        assertThat(generateJson, is(expectedJson));
    }
}
