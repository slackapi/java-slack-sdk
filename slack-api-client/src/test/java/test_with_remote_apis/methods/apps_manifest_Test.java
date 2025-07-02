package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.apps.manifest.*;
import com.slack.api.model.event.FunctionExecutedEvent;
import com.slack.api.model.manifest.AppManifest;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
        // To grab your first refresh token, visit https://docs.slack.dev/app-manifests/configuring-apps-with-app-manifests#config-tokens
        // Create a new directory slack-api-client/tmp/ and place your JSON data as {team_id}-{user_id}.json (e.g., T03E94MJU-U03E94MK0.json)
        // The content should be something like this:
        // {"access_token": "xoxe.xoxp-1-....","refresh_token": "xoxe-1-...","team_id": "T03E94MJU","user_id": "U03E94MK0","expire_at": 1699361653}
        ToolingTokenRotator tokenRotator = new ToolingTokenRotator(new FileToolingTokenStore("tmp/"));
        Optional<ToolingToken> maybeToken = tokenRotator.find(teamId, userId);
        assertThat(maybeToken.isPresent(), is(true));
        ToolingToken token = maybeToken.get();
        MethodsClient client = slack.methods(token.getAccessToken());

        AppManifestParams invalidManifest = AppManifestParams.builder()
//                .displayInformation(FunctionManifestRequest.DisplayInformation.builder()
//                        .name("manifest-test-app")
//                        .build())
                .features(AppManifestParams.Features.builder()
                        .botUser(AppManifestParams.BotUser.builder().displayName("test-bot").build())
                        .build())
                .settings(AppManifestParams.Settings.builder()
                        .socketModeEnabled(true)
                        .build())
                .oauthConfig(AppManifestParams.OAuthConfig.builder()
                        .scopes(AppManifestParams.Scopes.builder().bot(Arrays.asList("commands")).build())
                        .build())
                .build();

        AppManifestParams manifest = AppManifestParams.builder()
                .displayInformation(AppManifestParams.DisplayInformation.builder()
                        .name("manifest-test-app")
                        .build())
                .features(AppManifestParams.Features.builder()
                        .botUser(AppManifestParams.BotUser.builder().displayName("test-bot").build())
                        .build())
                .settings(AppManifestParams.Settings.builder()
                        .socketModeEnabled(true)
                        .build())
                .oauthConfig(AppManifestParams.OAuthConfig.builder()
                        .scopes(AppManifestParams.Scopes.builder().bot(Arrays.asList("commands")).build())
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

    @Test
    public void automationPlatform() throws IOException, SlackApiException {
        // To grab your first refresh token, visit https://docs.slack.dev/app-manifests/configuring-apps-with-app-manifests#config-tokens
        // Create a new directory slack-api-client/tmp/ and place your JSON data as {team_id}-{user_id}.json (e.g., T03E94MJU-U03E94MK0.json)
        // The content should be something like this:
        // {"access_token": "xoxe.xoxp-1-....","refresh_token": "xoxe-1-...","team_id": "T03E94MJU","user_id": "U03E94MK0","expire_at": 1699361653}
        ToolingTokenRotator tokenRotator = new ToolingTokenRotator(new FileToolingTokenStore("tmp/"));
        Optional<ToolingToken> maybeToken = tokenRotator.find(teamId, userId);
        assertThat(maybeToken.isPresent(), is(true));
        ToolingToken token = maybeToken.get();
        MethodsClient client = slack.methods(token.getAccessToken());

        Map<String, AppManifestParams.Function> functions = new HashMap<>();
        Map<String, AppManifestParams.ParameterProperty> properties = new HashMap<>();
        properties.put("user_id", AppManifestParams.ParameterProperty.builder()
                .type("slack#/types/user_id")
                .title("User")
                .description("Who to send it")
                .hint("Select a user in the workspace")
                .build());
        properties.put("message", AppManifestParams.ParameterProperty.builder()
                .type("string")
                .title("Message")
                .description("Whatever you want to tell")
                .hint("up to 100 characters")
                .maxLength(100)
                .minLength(1)
                .build());
        properties.put("amount", AppManifestParams.ParameterProperty.builder()
                .type("number")
                .title("Amount")
                .description("How many do you need?")
                .hint("How many do you need?")
                .minimum(1)
                .maximum(10)
                .build());

        functions.put("hello", AppManifestParams.Function.builder()
                .title("Hello")
                .description("Hello world!")
                .inputParameters(AppManifestParams.InputParameters.builder()
                        .properties(properties)
                        .required(Arrays.asList("user_id"))
                        .build())
                .outputParameters(AppManifestParams.OutputParameters.builder()
                        .properties(properties)
                        .required(Arrays.asList("user_id"))
                        .build())
                .build());

        AppManifestParams invalidManifest = AppManifestParams.builder()
                .metadata(AppManifestParams.Metadata.builder().majorVersion(2).build())
//                .displayInformation(FunctionManifestRequest.DisplayInformation.builder()
//                        .name("manifest-test-app")
//                        .build())
                .features(AppManifestParams.Features.builder()
                        .botUser(AppManifestParams.BotUser.builder().displayName("test-bot").build())
                        .build())
                .settings(AppManifestParams.Settings.builder()
                        .functionRuntime("remote")
                        .interactivity(AppManifestParams.Interactivity.builder()
                                .isEnabled(true)
                                .build())
                        .socketModeEnabled(true)
                        .orgDeployEnabled(true)
                        .build())
                .oauthConfig(AppManifestParams.OAuthConfig.builder()
                        .scopes(AppManifestParams.Scopes.builder().bot(Arrays.asList("commands")).build())
                        .build())
                .functions(functions)
                .build();

        AppManifestParams invalidManifest2 = AppManifestParams.builder()
                .metadata(AppManifestParams.Metadata.builder().majorVersion(2).build())
                .displayInformation(AppManifestParams.DisplayInformation.builder()
                        .name("manifest-test-app")
                        .build())
                .settings(AppManifestParams.Settings.builder()
                        .functionRuntime("remote")
                        .interactivity(AppManifestParams.Interactivity.builder()
                                .isEnabled(true)
                                .build())
//                        .eventSubscriptions(AppManifestParams.EventSubscriptions.builder()
//                                .botEvents(Arrays.asList(FunctionExecutedEvent.TYPE_NAME))
//                                .build())
                        .socketModeEnabled(true)
                        .orgDeployEnabled(true)
                        .build())
                .features(AppManifestParams.Features.builder()
                        .botUser(AppManifestParams.BotUser.builder().displayName("test-bot").build())
                        .build())
                .oauthConfig(AppManifestParams.OAuthConfig.builder()
                        .scopes(AppManifestParams.Scopes.builder().bot(Arrays.asList("commands")).build())
                        .build())
                .functions(functions)
                .build();

        AppManifestParams manifest = AppManifestParams.builder()
                .metadata(AppManifestParams.Metadata.builder().majorVersion(2).build())
                .displayInformation(AppManifestParams.DisplayInformation.builder()
                        .name("manifest-test-app")
                        .build())
                .settings(AppManifestParams.Settings.builder()
                        .functionRuntime("remote")
                        .interactivity(AppManifestParams.Interactivity.builder()
                                .isEnabled(true)
                                .build())
                        .eventSubscriptions(AppManifestParams.EventSubscriptions.builder()
                                .botEvents(Arrays.asList(FunctionExecutedEvent.TYPE_NAME))
                                .build())
                        .socketModeEnabled(true)
                        .orgDeployEnabled(true)
                        .build())
                .features(AppManifestParams.Features.builder()
                        .botUser(AppManifestParams.BotUser.builder().displayName("test-bot").build())
                        .build())
                .oauthConfig(AppManifestParams.OAuthConfig.builder()
                        .scopes(AppManifestParams.Scopes.builder().bot(Arrays.asList("commands")).build())
                        .build())
                .functions(functions)
                .build();

        AppsManifestValidateResponse validation = client.appsManifestValidate(r -> r.manifest(invalidManifest));
        assertThat(validation.getError(), is("invalid_manifest"));
        validation = client.appsManifestValidate(r -> r.manifest(invalidManifest2));
        assertThat(validation.getError(), is("invalid_manifest"));
        validation = client.appsManifestValidate(r -> r.manifest(manifest));
        assertThat(validation.getError(), is(nullValue()));

        AppsManifestCreateResponse creation = null;
        try {
            creation = client.appsManifestCreate(r -> r.manifest(invalidManifest));
            assertThat(creation.getError(), is("invalid_manifest"));
            creation = client.appsManifestCreate(r -> r.manifest(invalidManifest2));
            assertThat(creation.getError(), is("invalid_manifest"));
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
