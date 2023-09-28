package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.response.admin.functions.AdminFunctionsListResponse;
import com.slack.api.methods.response.admin.functions.AdminFunctionsPermissionsLookupResponse;
import com.slack.api.methods.response.admin.functions.AdminFunctionsPermissionsSetResponse;
import com.slack.api.methods.response.admin.workflows.*;
import com.slack.api.model.admin.AppFunction;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AdminApi_workflows_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    static String teamId = System.getenv(Constants.SLACK_SDK_TEST_GRID_TEAM_ID);
    static AsyncMethodsClient methodsAsync = slack.methodsAsync(orgAdminUserToken);

    @Test
    @Ignore // TODO: 2023-09-25: the server side returns "internal_error"
    public void adminWorkflowsCollaborators() throws Exception {
        if (orgAdminUserToken != null) {
            AdminWorkflowsSearchResponse searchResult = methodsAsync.adminWorkflowsSearch(r -> r
                    .limit(30)
            ).get();
            assertThat(searchResult.getError(), is(nullValue()));

            // TODO: make this more stable
            String userId = methodsAsync.usersList(r -> r.teamId(teamId)).get().getMembers().get(0).getId();

            AdminWorkflowsCollaboratorsRemoveResponse remove = methodsAsync.adminWorkflowsCollaboratorsRemove(r -> r
                    .workflowIds(Arrays.asList(searchResult.getWorkflows().get(0).getId()))
                    .collaboratorIds(Arrays.asList(userId))
            ).get();
            assertThat(remove.getError(), is(nullValue()));

            AdminWorkflowsCollaboratorsAddResponse add = methodsAsync.adminWorkflowsCollaboratorsAdd(r -> r
                    .workflowIds(Arrays.asList(searchResult.getWorkflows().get(0).getId()))
                    .collaboratorIds(Arrays.asList(userId))
            ).get();
            assertThat(add.getError(), is(nullValue()));
        }
    }

    @Test
    @Ignore // TODO: 2023-09-25: the server side returns "internal_error"
    public void adminWorkflowsPermissions() throws Exception {
        if (orgAdminUserToken != null) {
            AdminWorkflowsSearchResponse searchResult = methodsAsync.adminWorkflowsSearch(r -> r
                    .limit(30)
            ).get();
            assertThat(searchResult.getError(), is(nullValue()));

            AdminWorkflowsPermissionsLookupResponse result = methodsAsync.adminWorkflowsPermissionsLookup(r -> r
                    .workflowIds(Arrays.asList(searchResult.getWorkflows().get(0).getId()))
            ).get();
            assertThat(result.getError(), is(nullValue()));
        }
    }

    // TODO: valid test
    @Test
    public void adminWorkflowsUnpublish() throws Exception {
        if (orgAdminUserToken != null) {
            AdminWorkflowsUnpublishResponse result = methodsAsync.adminWorkflowsUnpublish(r -> r
                    .workflowIds(Arrays.asList("W111111"))
            ).get();
            assertThat(result.getError(), is("invalid_arguments"));
        }
    }
}
