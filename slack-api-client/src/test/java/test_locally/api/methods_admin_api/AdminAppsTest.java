package test_locally.api.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.admin.apps.AdminAppsApproveResponse;
import com.slack.api.methods.response.admin.apps.AdminAppsApprovedListResponse;
import com.slack.api.methods.response.admin.apps.AdminAppsRestrictResponse;
import com.slack.api.methods.response.admin.apps.AdminAppsRestrictedListResponse;
import com.slack.api.methods.response.api.ApiTestResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class AdminAppsTest {

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
    public void adminApps() throws Exception {
        MethodsClient methods = slack.methods(ValidToken);
        {
            AdminAppsApproveResponse response = methods.adminAppsApprove(r -> r.appId("A123").teamId("T123").requestId("test"));
            assertThat(response.isOk(), is(true));
        }
        {
            AdminAppsApprovedListResponse response = methods.adminAppsApprovedList(r -> r.teamId("T123"));
            assertThat(response.isOk(), is(true));
        }
        {
            AdminAppsRestrictedListResponse response = methods.adminAppsRestrictedList(r -> r.teamId("T123"));
            assertThat(response.isOk(), is(true));
        }
        {
            AdminAppsRestrictResponse response = methods.adminAppsRestrict(r -> r.appId("A123").teamId("T123").requestId("test"));
            assertThat(response.isOk(), is(true));
        }
    }

}
