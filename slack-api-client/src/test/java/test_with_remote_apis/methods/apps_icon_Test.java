package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.apps.icon.AppsIconSetRequest;
import com.slack.api.methods.response.apps.icon.AppsIconSetResponse;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class apps_icon_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    String teamId = System.getenv(Constants.SLACK_SDK_TEST_APP_MANIFEST_API_TEAM_ID);
    String userId = System.getenv(Constants.SLACK_SDK_TEST_APP_MANIFEST_API_USER_ID);

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("apps.icon.*");
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
                        .name("icon-test-app")
                        .build())
                .build();
        AppsManifestCreateResponse creation = client.appsManifestCreate(r -> r.manifest(manifest));
        assertThat(creation.getError(), is(nullValue()));
        String appId = creation.getAppId();
        try {
            // Icons must be between 512x512px and 2000x2000px
            BufferedImage image = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
            ByteArrayOutputStream icon = new ByteArrayOutputStream();
            ImageIO.write(image, "png", icon);
            File file = File.createTempFile("icon", ".png");
            file.deleteOnExit();
            Files.write(file.toPath(), icon.toByteArray());

            // Icon changes are rate limited for a short time, and the async client waits out Retry-After
            AsyncMethodsClient asyncClient = slack.methodsAsync(maybeToken.get().getAccessToken());

            AppsIconSetResponse fileData = asyncClient.appsIconSet(AppsIconSetRequest.builder()
                    .appId(appId)
                    .fileData(icon.toByteArray())
                    .build()).get();
            assertThat(fileData.getError(), is(nullValue()));

            AppsIconSetResponse fromFile = asyncClient.appsIconSet(AppsIconSetRequest.builder()
                    .appId(appId)
                    .file(file)
                    .build()).get();
            assertThat(fromFile.getError(), is(nullValue()));

            // A publicly hosted 560x560px PNG
            AppsIconSetResponse fromUrl = asyncClient.appsIconSet(AppsIconSetRequest.builder()
                    .appId(appId)
                    .url("https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png")
                    .build()).get();
            assertThat(fromUrl.getError(), is(nullValue()));
        } finally {
            AppsManifestDeleteResponse deletion = client.appsManifestDelete(r -> r.appId(appId));
            assertThat(deletion.getError(), is(nullValue()));
        }
    }
}
