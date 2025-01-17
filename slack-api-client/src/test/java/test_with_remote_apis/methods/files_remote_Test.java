package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatDeleteResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.chat.ChatUpdateResponse;
import com.slack.api.methods.response.files.remote.*;
import com.slack.api.methods.response.search.SearchFilesResponse;
import com.slack.api.model.File;
import com.slack.api.model.MatchedItem;
import config.Constants;
import config.SlackTestConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.UUID;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertTrue;

@Slf4j
public class files_remote_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("files.remote.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);
    static String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    static String randomChannelId = null;

    @BeforeClass
    public static void loadRandomChannel() throws IOException, SlackApiException {
        ChatPostMessageResponse message = slack.methods(botToken).chatPostMessage(r -> r
                .channel("#random")
                .asUser(true)
                .text("test prep"));
        assertThat(message.getError(), is(nullValue()));

        randomChannelId = message.getChannel();

        ChatDeleteResponse deletion = slack.methods(botToken).chatDelete(r -> r.channel(message.getChannel()).ts(message.getTs()));
        assertThat(deletion.getError(), is(nullValue()));
    }

    @Test
    public void listAllFiles() throws IOException, SlackApiException {
        {
            FilesRemoteListResponse response = slack.methods(botToken).filesRemoteList(r -> r);
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getFiles().size(), is(greaterThan(0)));
        }
    }

    @Test
    public void listFilesInAChannel() throws IOException, SlackApiException {
        FilesRemoteListResponse response = slack.methods(botToken).filesRemoteList(r -> r.channel(randomChannelId).limit(10));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void addAndInfoAndRemove() throws IOException, SlackApiException {
        FilesRemoteAddResponse addResponse = slack.methods(botToken).filesRemoteAdd(r -> r
                .externalId("test-external-id-" + System.currentTimeMillis())
                .externalUrl("https://a.slack-edge.com/4a5c4/marketing/img/icons/icon_slack.svg")
                .filetype("imageData/svg")
                .title("Slack Logo Image File"));
        assertThat(addResponse.getError(), is(nullValue()));
        assertThat(addResponse.isOk(), is(true));
        File file = addResponse.getFile();

        {
            FilesRemoteInfoResponse response = slack.methods(botToken).filesRemoteInfo(r -> r
                    .externalId(file.getExternalId()));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            FilesRemoteInfoResponse response = slack.methods(botToken).filesRemoteInfo(r -> r
                    .file(file.getId()));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
        {
            FilesRemoteRemoveResponse response = slack.methods(botToken).filesRemoteRemove(r -> r
                    .externalId(file.getExternalId()));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));

            FilesRemoteRemoveResponse response2 = slack.methods(botToken).filesRemoteRemove(r -> r
                    .externalId(file.getExternalId()));
            assertThat(response2.getError(), is("file_not_found"));
        }
    }

    @Data
    public static class Image {
        String url = "https://a.slack-edge.com/a8b6/marketing/img/homepage/uis/calls/voice-and-video-call-slack-product-mobile@2x.png";
        byte[] content;

        public Image() throws Exception {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int length;
            byte[] b = new byte[2048];
            URLConnection conn = new URL(url).openConnection();
            try (InputStream is = conn.getInputStream()) {
                while ((length = is.read(b)) != -1) {
                    out.write(b, 0, length);
                }
                this.content = out.toByteArray();
            }
        }
    }

    @Test
    public void updateAndShare() throws Exception {
        String externalId = "test-external-id-" + System.currentTimeMillis();
        Image image = new Image();
        FilesRemoteAddResponse addResponse = slack.methods(botToken).filesRemoteAdd(r -> r
                .externalId(externalId)
                .externalUrl(image.url)
                .previewImage(image.content)
                .title("Slack Call"));
        assertThat(addResponse.getError(), is(nullValue()));
        assertThat(addResponse.isOk(), is(true));

        File file = addResponse.getFile();

        {
            FilesRemoteUpdateResponse response = slack.methods(botToken).filesRemoteUpdate(r -> r
                    .externalId(file.getExternalId())
                    .title("Slack Call (edited)"));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            FilesRemoteShareResponse response = slack.methods(botToken).filesRemoteShare(r -> r
                    .externalId(file.getExternalId())
                    .channels(Arrays.asList(randomChannelId)));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            FilesRemoteUpdateResponse response = slack.methods(botToken).filesRemoteUpdate(r -> r
                    .externalId(file.getExternalId())
                    .title("Slack Call (re: edited)"));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            FilesRemoteShareResponse response = slack.methods(botToken).filesRemoteShare(r -> r
                    .file(file.getId())
                    .channels(Arrays.asList(randomChannelId)));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            // One tricky bit: in the response, the file object may indicate that "has_rich_preview" is false,
            // even if you include preview_image.
            // That's because it takes a few seconds for Slack to parse the preview_image you pass.
            // If you call files.remote.add with the same external_id later, you'll see "has_preview_image": true.
            long millis = 0;
            FilesRemoteInfoResponse info = slack.methods(botToken).filesRemoteInfo(r -> r.externalId(externalId));
            while (!info.getFile().isHasRichPreview() && millis < 20 * 1000) {
                Thread.sleep(100);
                millis += 100;
                info = slack.methods(botToken).filesRemoteInfo(r -> r.externalId(externalId));
            }
            log.info("Preview imageData took {} milliseconds to get the imageData ready", millis);
        }

    }

    @Ignore
    @Test
    public void updateAndShare_searchable() throws Exception {
        String externalId = "test-searchable-external-id-" + System.currentTimeMillis();
        Image image = new Image();
        FilesRemoteAddResponse addResponse = slack.methods(botToken).filesRemoteAdd(r -> r
                .externalId(externalId)
                .externalUrl(image.url)
                .previewImage(image.content)
                .indexableFileContents((externalId + ": Seamlessly start a voice call from any conversation").getBytes())
                .title("Searchable content " + externalId));
        assertThat(addResponse.getError(), is(nullValue()));
        assertThat(addResponse.isOk(), is(true));

        File file = addResponse.getFile();

        {
            FilesRemoteShareResponse response = slack.methods(botToken).filesRemoteShare(r -> r
                    .externalId(file.getExternalId())
                    .channels(Arrays.asList(randomChannelId)));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));

        }

        boolean found = false;
        String query = externalId + ": Seamlessly start a voice call";
        long millis = 0;
        while (!found && millis < 60 * 1000) {
            Thread.sleep(800);
            millis += 800;
            SearchFilesResponse searchResults = slack.methods(userToken).searchFiles(r -> r.query(query));
            assertThat(searchResults.getError(), is(nullValue()));
            for (MatchedItem item : searchResults.getFiles().getMatches()) {
                if (item.getId().equals(file.getId())) {
                    found = true;
                    break;
                }
            }
        }
        assertTrue("The uploaded file not found", found);

        log.info("Searchable file contents took {} milliseconds to get indexed", millis);
    }

    @Test
    public void testPreviewImageReplacement() throws Exception {
        loadRandomChannel();

        MethodsClient client = slack.methods(botToken);
        String externalUrl = "https://www.example.com/test-image";

        String firstExternalId = "remote-file-slack-logo-" + UUID.randomUUID();
        byte[] firstPreviewImage = Files.readAllBytes(new java.io.File("src/test/resources/seratch.jpg").toPath());
        FilesRemoteAddResponse firstFileCreation = client.filesRemoteAdd(r -> r
                .externalId(firstExternalId)
                .externalUrl(externalUrl)
                .title("Some image")
                .previewImage(firstPreviewImage)
        );
        assertThat(firstFileCreation.getError(), is(nullValue()));

        ChatPostMessageResponse newMessage = client.chatPostMessage(r -> r
                .channel(randomChannelId)
                .text("This is v1")
                .blocks(asBlocks(
                        section(s -> s.text(plainText("This is v1"))),
                        file(f -> f.externalId(firstExternalId).source("remote"))
                ))
        );
        assertThat(newMessage.getError(), is(nullValue()));

        // For humans
        Thread.sleep(2000);

        String secondExternalId = "remote-file-slack-logo-" + UUID.randomUUID();
        byte[] secondPreviewImage = Files.readAllBytes(new java.io.File("src/test/resources/slack-logo.gif").toPath());
        FilesRemoteAddResponse secondFileCreation = client.filesRemoteAdd(r -> r
                .externalId(secondExternalId)
                .externalUrl(externalUrl)
                .title("Some image")
                .previewImage(secondPreviewImage)
        );
        assertThat(secondFileCreation.getError(), is(nullValue()));

        ChatUpdateResponse messageModification = client.chatUpdate(r -> r
                .channel(randomChannelId)
                .ts(newMessage.getTs())
                .text("This is v2")
                .blocks(asBlocks(
                        section(s -> s.text(plainText("This is v2"))),
                        file(f -> f.externalId(secondExternalId).source("remote"))
                ))
        );
        assertThat(messageModification.getError(), is(nullValue()));

    }

}
