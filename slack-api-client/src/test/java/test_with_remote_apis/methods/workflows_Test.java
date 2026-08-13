package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import com.slack.api.methods.response.workflows.WorkflowsFeaturedAddResponse;
import com.slack.api.methods.response.workflows.WorkflowsFeaturedListResponse;
import com.slack.api.methods.response.workflows.WorkflowsFeaturedRemoveResponse;
import com.slack.api.methods.response.workflows.WorkflowsFeaturedSetResponse;
import com.slack.api.methods.response.workflows.WorkflowsStepCompletedResponse;
import com.slack.api.methods.response.workflows.WorkflowsStepFailedResponse;
import com.slack.api.methods.response.workflows.WorkflowsUpdateStepResponse;
import com.slack.api.model.Conversation;
import com.slack.api.model.workflow.WorkflowStepInput;
import com.slack.api.model.workflow.WorkflowStepOutput;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

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
    String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);

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

    private String findChannelId() throws Exception {
        ConversationsListResponse channels = slack.methods(botToken).conversationsList(r -> r
                .excludeArchived(true).limit(100));
        assertThat(channels.getError(), is(nullValue()));
        for (Conversation channel : channels.getChannels()) {
            if (channel.getName().equals("general")) {
                return channel.getId();
            }
        }
        return null;
    }

    @Test
    public void workflowsFeaturedList() throws Exception {
        String triggerId = System.getenv(Constants.SLACK_SDK_TEST_WORKFLOW_TRIGGER_ID);
        Assume.assumeTrue(triggerId != null);
        // Featuring a workflow requires a caller with manage-workflows permission in the
        // channel; a bot token is denied with "restricted_action". Use the user token.
        Assume.assumeTrue(userToken != null);
        String channelId = findChannelId();
        Assume.assumeTrue(channelId != null);
        List<String> triggerIds = new ArrayList<>();
        triggerIds.add(triggerId);

        WorkflowsFeaturedAddResponse added = slack.methodsAsync(userToken)
                .workflowsFeaturedAdd(r -> r.channelId(channelId).triggerIds(triggerIds)).get();
        assertThat(added.getError(), is(nullValue()));
        try {
            List<String> channelIds = new ArrayList<>();
            channelIds.add(channelId);
            WorkflowsFeaturedListResponse listed = slack.methodsAsync(userToken)
                    .workflowsFeaturedList(r -> r.channelIds(channelIds)).get();
            assertThat(listed.getError(), is(nullValue()));
            assertThat(listed.getFeaturedWorkflows(), is(notNullValue()));
            assertThat(listed.getFeaturedWorkflows(), is(not(empty())));
            assertThat(listed.getFeaturedWorkflows().get(0).getChannelId(), is(notNullValue()));
        } finally {
            WorkflowsFeaturedRemoveResponse removed = slack.methodsAsync(userToken)
                    .workflowsFeaturedRemove(r -> r.channelId(channelId).triggerIds(triggerIds)).get();
            assertThat(removed.getError(), is(nullValue()));
        }
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
