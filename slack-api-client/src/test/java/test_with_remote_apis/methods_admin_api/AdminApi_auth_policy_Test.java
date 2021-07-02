package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.response.admin.auth.policy.AdminAuthPolicyAssignEntitiesResponse;
import com.slack.api.methods.response.admin.auth.policy.AdminAuthPolicyGetEntitiesResponse;
import com.slack.api.methods.response.admin.auth.policy.AdminAuthPolicyRemoveEntitiesResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Running this test requires enabling the email_password auth policy on the Slack admin side.
 */
@Slf4j
public class AdminApi_auth_policy_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    static List<String> userIds = Arrays.asList(
            System.getenv(Constants.SLACK_SDK_TEST_GRID_USER_ID_ADMIN_AUTH).split(","));
    static AsyncMethodsClient methodsAsync = slack.methodsAsync(orgAdminUserToken);

    @Test
    public void userEntities() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAuthPolicyGetEntitiesResponse existingEntities =
                    methodsAsync.adminAuthPolicyGetEntities(r -> r.policyName("email_password").limit(3)).get();
            assertThat(existingEntities.getError(), is(nullValue()));

            AdminAuthPolicyAssignEntitiesResponse assignment = methodsAsync.adminAuthPolicyAssignEntities(r -> r
                    .entityIds(userIds)
                    .policyName("email_password")
                    .entityType("USER")
            ).get();
            assertThat(assignment.getError(), is(nullValue()));

            AdminAuthPolicyRemoveEntitiesResponse removal = methodsAsync.adminAuthPolicyRemoveEntities(r -> r
                    .entityIds(userIds)
                    .policyName("email_password")
                    .entityType("USER")
            ).get();
            assertThat(removal.getError(), is(nullValue()));
        }
    }
}
