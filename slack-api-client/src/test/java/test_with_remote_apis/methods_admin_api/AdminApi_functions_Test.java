package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.response.admin.functions.AdminFunctionsListResponse;
import com.slack.api.methods.response.admin.functions.AdminFunctionsPermissionsLookupResponse;
import com.slack.api.methods.response.admin.functions.AdminFunctionsPermissionsSetResponse;
import com.slack.api.model.admin.AppFunction;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AdminApi_functions_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    static AsyncMethodsClient methodsAsync = slack.methodsAsync(orgAdminUserToken);

    @Test
    public void adminFunctions() throws Exception {
        if (orgAdminUserToken != null) {
            final List<String> appIds = Arrays.asList(System.getenv(Constants.SLACK_SDK_TEST_GRID_APP_IDS).split(","));
            AdminFunctionsListResponse response = methodsAsync.adminFunctionsList(r -> r
                    .appIds(appIds)
                    .limit(1000)
            ).get();
            assertThat(response.getError(), is(nullValue()));

            List<String> functionIds = response.getFunctions().stream().map(AppFunction::getId).collect(Collectors.toList());
            AdminFunctionsPermissionsLookupResponse permissions =
                    methodsAsync.adminFunctionsPermissionsLookup(r -> r.functionIds(functionIds)).get();
            assertThat(permissions.getError(), is(nullValue()));
            String userId = methodsAsync.authTest(r -> r).get().getUserId();
            AdminFunctionsPermissionsSetResponse set = methodsAsync.adminFunctionsPermissionsSet(r -> r
                    .functionId(functionIds.get(0))
                    .userIds(Arrays.asList(userId))
                    .visibility("named_entities")
            ).get();
            assertThat(set.getError(), is(nullValue()));
            set = methodsAsync.adminFunctionsPermissionsSet(r -> r
                    .functionId(functionIds.get(0))
                    .visibility("no_one")
            ).get();
            assertThat(set.getError(), is(nullValue()));
            set = methodsAsync.adminFunctionsPermissionsSet(r -> r
                    .functionId(functionIds.get(0))
                    .visibility("everyone")
            ).get();
            assertThat(set.getError(), is(nullValue()));

            String nextCursor = response.getResponseMetadata().getNextCursor();

            while (nextCursor != null && !nextCursor.equals("")) {
                final String _nextCursor = nextCursor;
                response = methodsAsync.adminFunctionsList(req -> req
                        .appIds(appIds)
                        .limit(1000)
                        .cursor(_nextCursor)
                ).get();
                assertThat(response.getError(), is(nullValue()));
                nextCursor = response.getResponseMetadata().getNextCursor();

                List<String> _functionIds = response.getFunctions().stream().map(AppFunction::getId).collect(Collectors.toList());
                if (_functionIds.size() > 0) {
                    permissions = methodsAsync.adminFunctionsPermissionsLookup(r -> r.functionIds(_functionIds)).get();
                    assertThat(permissions.getError(), is(nullValue()));
                }
            }
        }
    }
}
