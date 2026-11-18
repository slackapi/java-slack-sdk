package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.apps.managed.permissions.AppsManagedPermissionsSetResponse;
import com.slack.api.methods.response.apps.manifest.AppsManifestCreateResponse;
import com.slack.api.methods.response.apps.manifest.AppsManifestDeleteResponse;
import com.slack.api.model.manifest.AppManifestParams;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class apps_managed_permissions_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    // The Luna app is a manager app: it has app_configurations:write and its org is enabled for managed apps
    static String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("apps.managed.permissions.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void set() throws Exception {
        if (orgAdminUserToken != null) {
            MethodsClient client = slack.methods(orgAdminUserToken);
            // A managed app is one the manager app creates, and its permissions can be set before it's installed
            AppManifestParams manifest = AppManifestParams.builder()
                    .displayInformation(AppManifestParams.DisplayInformation.builder()
                            .name("managed-permissions-test-app")
                            .build())
                    .build();
            AppsManifestCreateResponse creation = client.appsManifestCreate(r -> r.manifest(manifest));
            assertThat(creation.getError(), is(nullValue()));
            String appId = creation.getAppId();
            try {
                AppsManagedPermissionsSetResponse everyone = client.appsManagedPermissionsSet(r -> r
                        .appId(appId)
                        .permissions("everyone"));
                assertThat(everyone.getError(), is(nullValue()));
                assertThat(everyone.getPermissionType(), is("everyone"));

                String userId = client.authTest(r -> r).getUserId();
                AppsManagedPermissionsSetResponse appOwner = client.appsManagedPermissionsSet(r -> r
                        .appId(appId)
                        .permissions("app_owner"));
                assertThat(appOwner.getError(), is(nullValue()));
                assertThat(appOwner.getPermissionType(), is("named_entities"));
                assertThat(appOwner.getUserIds(), is(Collections.singletonList(userId)));
            } finally {
                AppsManifestDeleteResponse deletion = client.appsManifestDelete(r -> r.appId(appId));
                assertThat(deletion.getError(), is(nullValue()));
            }
        }
    }
}
