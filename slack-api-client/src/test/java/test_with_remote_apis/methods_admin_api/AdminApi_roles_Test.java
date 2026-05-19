package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.response.admin.roles.AdminRolesAddAssignmentsResponse;
import com.slack.api.methods.response.admin.roles.AdminRolesListAssignmentsResponse;
import com.slack.api.methods.response.admin.roles.AdminRolesRemoveAssignmentsResponse;
import com.slack.api.model.admin.RoleAssignment;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Slf4j
public class AdminApi_roles_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    static String workspaceAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_WORKSPACE_ADMIN_USER_TOKEN);

    static AsyncMethodsClient orgAdminMethodsAsync = slack.methodsAsync(orgAdminUserToken);
    static AsyncMethodsClient workspaceAdminMethodsAsync = slack.methodsAsync(workspaceAdminUserToken);

    @Test
    public void listRoleAssignments() throws Exception {
        if (orgAdminUserToken != null) {
            AdminRolesListAssignmentsResponse list = orgAdminMethodsAsync.adminRolesListAssignments(r -> r
                    .limit(3)
                    .sortDir("DESC")
                    .roleIds(Arrays.asList("Rl0A"))
            ).get();
            assertThat(list.getError(), is(nullValue()));
            assertThat(list.getRoleAssignments().size(), is(greaterThan(0)));
            RoleAssignment assignment = list.getRoleAssignments().get(0);
        }
    }

    @Test
    public void addAndRemoveAssignments() throws Exception {
        if (orgAdminUserToken != null) {
            String userId = orgAdminMethodsAsync.authTest(r -> r).get().getUserId();
            String channelId = workspaceAdminMethodsAsync.usersConversations(r -> r
                    .user(userId).excludeArchived(true).limit(1)
            ).get().getChannels().get(0).getId();
            AdminRolesAddAssignmentsResponse addition = orgAdminMethodsAsync.adminRolesAddAssignments(r -> r
                    .roleId("Rl0A")
                    .entityIds(Arrays.asList(channelId))
                    .userIds(Arrays.asList(userId))
            ).get();
            assertThat(addition.getError(), is(nullValue()));

            AdminRolesRemoveAssignmentsResponse removal = orgAdminMethodsAsync.adminRolesRemoveAssignments(r -> r
                    .roleId("Rl0A")
                    .entityIds(Arrays.asList(channelId))
                    .userIds(Arrays.asList(userId))
            ).get();
            assertThat(removal.getError(), is(nullValue()));
        }
    }

}
