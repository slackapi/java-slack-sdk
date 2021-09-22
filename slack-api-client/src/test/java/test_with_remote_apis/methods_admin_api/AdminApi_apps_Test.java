package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.response.admin.apps.*;
import com.slack.api.model.admin.AppRequest;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AdminApi_apps_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    static String teamId = System.getenv(Constants.SLACK_SDK_TEST_GRID_TEAM_ID);
    static AsyncMethodsClient methodsAsync = slack.methodsAsync(orgAdminUserToken);

    // TODO: valid test
    @Ignore
    @Test
    public void apps_clearResolution() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAppsClearResolutionResponse response = methodsAsync
                    .adminAppsClearResolution(r -> r.appId("A111").teamId(teamId)).get();
            assertThat(response.getError(), is("missing_scope"));
        }
    }

    @Ignore
    @Test
    public void apps_uninstall() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAppsUninstallResponse response = methodsAsync
                    .adminAppsUninstall(r -> r.appId("A111").teamIds(Arrays.asList(teamId))).get();
            assertThat(response.getError(), is("missing_scope"));
        }
    }

    @Ignore
    @Test
    public void apps_approvedList() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAppsApprovedListResponse errorResponse = methodsAsync.adminAppsApprovedList(r -> r
                            .limit(1000)
                    /*.teamId(adminAppsTeamId)*/)
                    .get();
            assertThat(errorResponse.getError(), is("team_not_found"));

            AdminAppsApprovedListResponse response = methodsAsync.adminAppsApprovedList(r -> r
                    .limit(1000)
                    .teamId(teamId))
                    .get();
            assertThat(response.getError(), is(nullValue()));
        }
    }

    @Ignore
    @Test
    public void apps_restrictedList() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAppsRestrictedListResponse errorResponse = methodsAsync.adminAppsRestrictedList(r -> r
                            .limit(1000)
                    /*.teamId(adminAppsTeamId)*/)
                    .get();
            assertThat(errorResponse.getError(), is("team_not_found"));

            AdminAppsRestrictedListResponse response = methodsAsync.adminAppsRestrictedList(r -> r
                    .limit(1000)
                    .teamId(teamId))
                    .get();
            assertThat(response.getError(), is(nullValue()));
        }
    }

    @Ignore
    @Test
    public void appsRequests() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAppsRequestsListResponse list = methodsAsync.adminAppsRequestsList(r -> r
                    .limit(1000)
                    .teamId(teamId))
                    .get();
            assertThat(list.getError(), is(nullValue()));

            if (list.getAppRequests().size() >= 2) {
                AppRequest toBeApproved = list.getAppRequests().get(0);

                AdminAppsApproveResponse approvalError = methodsAsync.adminAppsApprove(req -> req
                        .appId(toBeApproved.getId())
                        .teamId(teamId))
                        .get();
                assertThat(approvalError.getError(), is(notNullValue()));

                AdminAppsApproveResponse approval = methodsAsync.adminAppsApprove(req -> req
                        .appId(toBeApproved.getApp().getId())
                        .teamId(teamId))
                        .get();
                assertThat(approval.getError(), is(nullValue()));

                AppRequest toBeRestricted = list.getAppRequests().get(1);

                AdminAppsRestrictResponse restrictionError = methodsAsync.adminAppsRestrict(req -> req
                        .appId(toBeRestricted.getId())
                        .teamId(teamId))
                        .get();
                assertThat(restrictionError.getError(), is(notNullValue()));

                AdminAppsRestrictResponse restriction = methodsAsync.adminAppsRestrict(req -> req
                        .appId(toBeRestricted.getApp().getId())
                        .teamId(teamId))
                        .get();
                assertThat(restriction.getError(), is(nullValue()));
            }
        }
    }
}
