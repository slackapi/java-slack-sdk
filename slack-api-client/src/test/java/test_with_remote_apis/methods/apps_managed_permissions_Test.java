package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.apps.managed.permissions.AppsManagedPermissionsSetResponse;
import com.slack.api.methods.response.apps.manifest.AppsManifestCreateResponse;
import com.slack.api.methods.response.apps.manifest.AppsManifestDeleteResponse;
import com.slack.api.model.manifest.AppManifestParams;
import com.slack.api.token_rotation.tooling.ToolingToken;
import com.slack.api.token_rotation.tooling.ToolingTokenRotator;
import com.slack.api.token_rotation.tooling.store.FileToolingTokenStore;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class apps_managed_permissions_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    String teamId = System.getenv(Constants.SLACK_SDK_TEST_APP_MANIFEST_API_TEAM_ID);
    String userId = System.getenv(Constants.SLACK_SDK_TEST_APP_MANIFEST_API_USER_ID);

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
        // The config token comes from the same token store as apps_manifest_Test
        ToolingTokenRotator tokenRotator = new ToolingTokenRotator(new FileToolingTokenStore("tmp/"));
        Optional<ToolingToken> maybeToken = tokenRotator.find(teamId, userId);
        assertThat(maybeToken.isPresent(), is(true));
        MethodsClient client = slack.methods(maybeToken.get().getAccessToken());

        AppManifestParams manifest = AppManifestParams.builder()
                .displayInformation(AppManifestParams.DisplayInformation.builder()
                        .name("managed-permissions-test-app")
                        .build())
                .build();
        AppsManifestCreateResponse creation = client.appsManifestCreate(r -> r.manifest(manifest));
        assertThat(creation.getError(), is(nullValue()));
        String appId = creation.getAppId();
        try {
            // A config token isn't issued to a manager app, so the permissions can't be set
            AppsManagedPermissionsSetResponse response = client.appsManagedPermissionsSet(r -> r
                    .appId(appId)
                    .permissions("everyone"));
            assertThat(response.getError(), is("manager_app_not_eligible"));
        } finally {
            AppsManifestDeleteResponse deletion = client.appsManifestDelete(r -> r.appId(appId));
            assertThat(deletion.getError(), is(nullValue()));
        }
    }
}
