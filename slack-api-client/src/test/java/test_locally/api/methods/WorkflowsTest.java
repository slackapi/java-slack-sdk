package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.model.workflow.WorkflowStepInput;
import com.slack.api.model.workflow.WorkflowStepOutput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class WorkflowsTest {

    MockSlackApiServer server = new MockSlackApiServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    @Before
    public void setup() throws Exception {
        server.start();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void test() throws Exception {
        List<String> triggerIds = new ArrayList<>();
        triggerIds.add("dummy-trigger-id");

        List<String> channelIds = new ArrayList<>();
        channelIds.add("dummy-channel-id");

        Map<String, WorkflowStepInput> inputs = new HashMap<>();
        List<WorkflowStepOutput> outputs = new ArrayList<>();

        Map<String, Object> stepOutputs = new HashMap<>();
        Map<String, Object> error = new HashMap<>();
        error.put("message", "Something wrong!");

        assertThat(slack.methods(ValidToken).workflowsFeaturedAdd(r -> r
                .channelId("dummy-channel-id")
                .triggerIds(triggerIds)
        ).isOk(), is(true));

        assertThat(slack.methods(ValidToken).workflowsFeaturedList(r -> r
                .channelIds(channelIds)
        ).isOk(), is(true));

        assertThat(slack.methods(ValidToken).workflowsFeaturedRemove(r -> r
                .channelId("dummy-channel-id")
                .triggerIds(triggerIds)
        ).isOk(), is(true));

        assertThat(slack.methods(ValidToken).workflowsFeaturedSet(r -> r
                .channelId("dummy-channel-id")
                .triggerIds(triggerIds)
        ).isOk(), is(true));

        assertThat(slack.methods(ValidToken).workflowsUpdateStep(r -> r
                .workflowStepEditId("dummy")
                .inputs(inputs)
                .outputs(outputs)
        ).isOk(), is(true));

        assertThat(slack.methods(ValidToken).workflowsStepCompleted(r -> r
                .workflowStepExecuteId("dummy")
                .outputs(stepOutputs)
        ).isOk(), is(true));

        assertThat(slack.methods(ValidToken).workflowsStepFailed(r -> r
                .workflowStepExecuteId("dummy")
                .error(error)
        ).isOk(), is(true));
    }

    @Test
    public void test_async() throws Exception {
        List<String> triggerIds = new ArrayList<>();
        triggerIds.add("dummy-trigger-id");

        List<String> channelIds = new ArrayList<>();
        channelIds.add("dummy-channel-id");

        Map<String, WorkflowStepInput> inputs = new HashMap<>();
        List<WorkflowStepOutput> outputs = new ArrayList<>();

        Map<String, Object> stepOutputs = new HashMap<>();
        Map<String, Object> error = new HashMap<>();
        error.put("message", "Something wrong!");

        assertThat(slack.methodsAsync(ValidToken).workflowsFeaturedAdd(r -> r
                .channelId("dummy-channel-id")
                .triggerIds(triggerIds)
        ).get().isOk(), is(true));

        assertThat(slack.methodsAsync(ValidToken).workflowsFeaturedList(r -> r
                .channelIds(channelIds)
        ).get().isOk(), is(true));

        assertThat(slack.methodsAsync(ValidToken).workflowsFeaturedRemove(r -> r
                .channelId("dummy-channel-id")
                .triggerIds(triggerIds)
        ).get().isOk(), is(true));

        assertThat(slack.methodsAsync(ValidToken).workflowsFeaturedSet(r -> r
                .channelId("dummy-channel-id")
                .triggerIds(triggerIds)
        ).get().isOk(), is(true));

        assertThat(slack.methodsAsync(ValidToken).workflowsUpdateStep(r -> r
                .workflowStepEditId("dummy")
                .inputs(inputs)
                .outputs(outputs)
        ).get().isOk(), is(true));

        assertThat(slack.methodsAsync(ValidToken).workflowsStepCompleted(r -> r
                .workflowStepExecuteId("dummy")
                .outputs(stepOutputs)
        ).get().isOk(), is(true));

        assertThat(slack.methodsAsync(ValidToken).workflowsStepFailed(r -> r
                .workflowStepExecuteId("dummy")
                .error(error)
        ).get().isOk(), is(true));
    }

}

