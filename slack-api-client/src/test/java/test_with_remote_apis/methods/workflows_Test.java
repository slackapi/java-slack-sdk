package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.response.workflows.WorkflowsFeaturedAddResponse;
import com.slack.api.methods.response.workflows.WorkflowsFeaturedListResponse;
import com.slack.api.methods.response.workflows.WorkflowsFeaturedRemoveResponse;
import com.slack.api.methods.response.workflows.WorkflowsFeaturedSetResponse;
import com.slack.api.methods.response.workflows.WorkflowsStepCompletedResponse;
import com.slack.api.methods.response.workflows.WorkflowsStepFailedResponse;
import com.slack.api.methods.response.workflows.WorkflowsUpdateStepResponse;
import com.slack.api.model.workflow.WorkflowStepInput;
import com.slack.api.model.workflow.WorkflowStepOutput;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class workflows_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("workflows.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    @Test
    public void workflowsFeaturedAdd() throws ExecutionException, InterruptedException {
        List<String> triggerIds = new ArrayList<>();
        triggerIds.add("dummy-trigger-id");
        
        WorkflowsFeaturedAddResponse workflowsFeaturedAddResponse = slack.methodsAsync(botToken)
                .workflowsFeaturedAdd(r -> r
                        .channelId("dummy-channel-id")
                        .triggerIds(triggerIds)
                ).get();
        assertThat(workflowsFeaturedAddResponse.getError(), is(notNullValue()));
    }

    @Test
    public void workflowsFeaturedList() throws ExecutionException, InterruptedException {
        List<String> channelIds = new ArrayList<>();
        channelIds.add("dummy-channel-id");
        WorkflowsFeaturedListResponse workflowsFeaturedListResponse = slack.methodsAsync(botToken)
                .workflowsFeaturedList(r -> r
                        .channelIds(channelIds)
                ).get();
        assertThat(workflowsFeaturedListResponse.getError(), is(notNullValue()));
    }

    @Test
    public void workflowsFeaturedRemove() throws ExecutionException, InterruptedException {
        List<String> triggerIds = new ArrayList<>();
        triggerIds.add("dummy-trigger-id");

        WorkflowsFeaturedRemoveResponse workflowsFeaturedRemoveResponse = slack.methodsAsync(botToken)
                .workflowsFeaturedRemove(r -> r
                        .channelId("dummy-channel-id")
                        .triggerIds(triggerIds)
                ).get();
        assertThat(workflowsFeaturedRemoveResponse.getError(), is(notNullValue()));
    }

    @Test
    public void workflowsFeaturedSet() throws ExecutionException, InterruptedException {
        List<String> triggerIds = new ArrayList<>();
        triggerIds.add("dummy-trigger-id");

        WorkflowsFeaturedSetResponse workflowsFeaturedSetResponse = slack.methodsAsync(botToken)
                .workflowsFeaturedSet(r -> r
                        .channelId("dummy-channel-id")
                        .triggerIds(triggerIds)
                ).get();
        assertThat(workflowsFeaturedSetResponse.getError(), is(notNullValue()));
    }

    @Test
    public void workflowsUpdateStep() throws ExecutionException, InterruptedException {
        Map<String, WorkflowStepInput> inputs = new HashMap<>();
        List<WorkflowStepOutput> outputs = new ArrayList<>();
        WorkflowsUpdateStepResponse updateResponse = slack.methodsAsync(botToken)
                .workflowsUpdateStep(r -> r.workflowStepEditId("dummy")
                        .inputs(inputs)
                        .outputs(outputs)
                ).get();
        assertThat(updateResponse.getError(), is(notNullValue()));
    }

    @Test
    public void workflowsStepCompleted() throws ExecutionException, InterruptedException {
        Map<String, Object> outputs = new HashMap<>();
        WorkflowsStepCompletedResponse updateResponse = slack.methodsAsync(botToken)
                .workflowsStepCompleted(r -> r.workflowStepExecuteId("dummy").outputs(outputs)).get();
        assertThat(updateResponse.getError(), is(notNullValue()));
    }

    @Test
    public void workflowsStepFailed() throws ExecutionException, InterruptedException {
        Map<String, Object> error = new HashMap<>();
        error.put("message", "Something wrong!");
        WorkflowsStepFailedResponse updateResponse = slack.methodsAsync(botToken)
                .workflowsStepFailed(r -> r
                        .workflowStepExecuteId("dummy")
                        .error(error)
                ).get();
        assertThat(updateResponse.getError(), is(notNullValue()));
    }

}
