package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.apps.manifest.*;
import com.slack.api.model.AppManifest;
import com.slack.api.token_rotation.tooling.ToolingToken;
import com.slack.api.token_rotation.tooling.ToolingTokenRotator;
import com.slack.api.token_rotation.tooling.store.FileToolingTokenStore;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class apps_manifest_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    String teamId = System.getenv(Constants.SLACK_SDK_TEST_APP_MANIFEST_API_TEAM_ID);
    String userId = System.getenv(Constants.SLACK_SDK_TEST_APP_MANIFEST_API_USER_ID);

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("apps.manifest.*");
        SlackTestConfig.initializeRawJSONDataFiles("tooling.tokens.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void manifestOperations() throws IOException, SlackApiException {
        // To grab your first refresh token, visit https://api.slack.com/reference/manifests#config-tokens
        // Create a new directory slack-api-client/tmp/ and place your JSON data as {team_id}-{user_id}.json (e.g., T03E94MJU-U03E94MK0.json)
        // The content should be something like this:
        // {"access_token": "xoxe.xoxp-1-....","refresh_token": "xoxe-1-...","team_id": "T03E94MJU","user_id": "U03E94MK0","expire_at": 1699361653}
        ToolingTokenRotator tokenRotator = new ToolingTokenRotator(new FileToolingTokenStore("tmp/"));
        Optional<ToolingToken> maybeToken = tokenRotator.find(teamId, userId);
        assertThat(maybeToken.isPresent(), is(true));
        ToolingToken token = maybeToken.get();
        MethodsClient client = slack.methods(token.getAccessToken());

        AppManifest invalidManifest = AppManifest.builder()
//                .displayInformation(AppManifest.DisplayInformation.builder()
//                        .name("manifest-test-app")
//                        .build())
                .features(AppManifest.Features.builder()
                        .botUser(AppManifest.BotUser.builder().displayName("test-bot").build())
                        .build())
                .settings(AppManifest.Settings.builder()
                        .socketModeEnabled(true)
                        .build())
                .oauthConfig(AppManifest.OAuthConfig.builder()
                        .scopes(AppManifest.Scopes.builder().bot(Arrays.asList("commands")).build())
                        .build())
                .build();

        AppManifest manifest = AppManifest.builder()
                .displayInformation(AppManifest.DisplayInformation.builder()
                        .name("manifest-test-app")
                        .build())
                .features(AppManifest.Features.builder()
                        .botUser(AppManifest.BotUser.builder().displayName("test-bot").build())
                        .build())
                .settings(AppManifest.Settings.builder()
                        .socketModeEnabled(true)
                        .build())
                .oauthConfig(AppManifest.OAuthConfig.builder()
                        .scopes(AppManifest.Scopes.builder().bot(Arrays.asList("commands")).build())
                        .build())
                .build();

        AppsManifestValidateResponse validation = client.appsManifestValidate(r -> r.manifest(invalidManifest));
        assertThat(validation.getError(), is("invalid_manifest"));
        validation = client.appsManifestValidate(r -> r.manifest(manifest));
        assertThat(validation.getError(), is(nullValue()));

        AppsManifestCreateResponse creation = null;
        try {
            creation = client.appsManifestCreate(r -> r.manifest(manifest));
            assertThat(creation.getError(), is(nullValue()));
            String appId = creation.getAppId();

            validation = client.appsManifestValidate(r -> r.manifest(manifest).appId(appId));
            assertThat(validation.getError(), is(nullValue()));

            AppsManifestUpdateResponse modification = client.appsManifestUpdate(r -> r.appId(appId).manifest(manifest));
            assertThat(modification.getError(), is(nullValue()));

            manifest.getDisplayInformation().setName("manifest-test-app-2");
            manifest.getOauthConfig().getScopes().setBot(Arrays.asList("commands", "chat:write"));
            modification = client.appsManifestUpdate(r -> r.appId(appId).manifest(manifest));
            assertThat(modification.getError(), is(nullValue()));

            AppsManifestExportResponse deletion = client.appsManifestExport(r -> r.appId(appId));
            assertThat(deletion.getError(), is(nullValue()));

        } finally {
            if (creation != null && creation.isOk()) {
                String appId = creation.getAppId();
                AppsManifestDeleteResponse deletion = client.appsManifestDelete(r -> r.appId(appId));
                assertThat(deletion.getError(), is(nullValue()));
            }
        }
    }


}
