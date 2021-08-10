package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatDeleteRequest;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.chat.ChatUpdateRequest;
import com.slack.api.methods.request.files.FilesDeleteRequest;
import com.slack.api.methods.request.files.FilesInfoRequest;
import com.slack.api.methods.request.files.FilesRevokePublicURLRequest;
import com.slack.api.methods.request.files.FilesSharedPublicURLRequest;
import com.slack.api.methods.response.chat.ChatDeleteResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.chat.ChatUpdateResponse;
import com.slack.api.methods.response.files.*;
import com.slack.api.methods.response.users.UsersConversationsResponse;
import com.slack.api.model.Conversation;
import com.slack.api.model.ConversationType;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import util.TestChannelGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Slf4j
public class files_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);
    static String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    static String channelId = null;

    @BeforeClass
    public static void loadRandomChannel() throws IOException, SlackApiException {
        ChatPostMessageResponse message = slack.methods(botToken).chatPostMessage(r -> r
                .channel("#random")
                .asUser(true)
                .text("test prep"));
        assertThat(message.getError(), is(nullValue()));

        channelId = message.getChannel();

        ChatDeleteResponse deletion = slack.methods(botToken).chatDelete(r -> r.channel(message.getChannel()).ts(message.getTs()));
        assertThat(deletion.getError(), is(nullValue()));
    }

    @Test
    public void describe() throws IOException, SlackApiException {
        {
            FilesListResponse response = slack.methods().filesList(r -> r.token(userToken));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getFiles(), is(notNullValue()));
        }
    }

    @Test
    public void describe_ShowFilesHiddenByLimit() throws IOException, SlackApiException {
        {
            FilesListResponse response = slack.methods(userToken).filesList(r -> r.showFilesHiddenByLimit(true));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getFiles(), is(notNullValue()));
        }
    }

    @Test
    public void createTextFileAndComments() throws IOException, SlackApiException {
        File file = new File("src/test/resources/sample.txt");
        final com.slack.api.model.File fileObj;
        {
            FilesUploadResponse response = slack.methods(botToken).filesUpload(r -> r
                    .channels(Arrays.asList(channelId))
                    .file(file)
                    .filename("sample.txt")
                    .initialComment("initial comment")
                    .title("file title"));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            fileObj = response.getFile();
            assertThat(fileObj.getTitle(), is("file title"));
            assertThat(fileObj.getName(), is("sample.txt"));
            assertThat(fileObj.getMimetype(), is("text/plain"));
            assertThat(fileObj.getFiletype(), is("text"));
            assertThat(fileObj.getPrettyType(), is("Plain Text"));
            assertThat(fileObj.getSize(), is(57));
            assertThat(fileObj.isEditable(), is(true));

            assertThat(fileObj.getPreview(), is(notNullValue()));
            assertThat(fileObj.getLines(), is(5));
            assertThat(fileObj.isPreviewTruncated(), is(false));

            // image related attributes should be null
            assertThat(fileObj.getImageExifRotation(), is(nullValue()));
            assertThat(fileObj.getPjpeg(), is(nullValue()));
            assertThat(fileObj.getOriginalWidth(), is(nullValue()));
            assertThat(fileObj.getThumb360Width(), is(nullValue()));
            assertThat(fileObj.getThumb480Width(), is(nullValue()));
            assertThat(fileObj.getOriginalHeight(), is(nullValue()));
            assertThat(fileObj.getThumb360Height(), is(nullValue()));
            assertThat(fileObj.getThumb480Height(), is(nullValue()));

            assertThat(fileObj.getMode(), is("snippet"));
            assertThat(fileObj.isHasRichPreview(), is(false));
            assertThat(fileObj.isStarred(), is(false));
            assertThat(fileObj.isPublic(), is(true));
            assertThat(fileObj.isPublicUrlShared(), is(false));

            assertThat(fileObj.getCommentsCount(), is(0));

            assertThat(fileObj.getChannels().size(), is(1));
            assertThat(fileObj.getGroups().size(), is(0));
            assertThat(fileObj.getIms().size(), is(0));

            assertThat(fileObj.getShares(), is(notNullValue()));
            assertThat(fileObj.getShares().getPublicChannels(), is(notNullValue()));
            assertThat(fileObj.getShares().getPublicChannels().get(channelId), is(notNullValue()));

            assertThat(fileObj.getShares().getPrivateChannels(), is(nullValue()));
        }

        {
            FilesInfoResponse response = slack.methods(botToken).filesInfo(r -> r.file(fileObj.getId()));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            FilesSharedPublicURLResponse response = slack.methods(userToken).filesSharedPublicURL(r -> r.file(fileObj.getId()));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            FilesRevokePublicURLResponse response = slack.methods(userToken).filesRevokePublicURL(
                    FilesRevokePublicURLRequest.builder().file(fileObj.getId()).build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
        {
            ChatDeleteResponse response = slack.methods(botToken).chatDelete(r -> r
                    .channel(channelId)
                    .ts(fileObj
                            .getShares()
                            .getPublicChannels().get(channelId).get(0)
                            .getTs())
            );
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
        {
            FilesDeleteResponse response = slack.methods(botToken).filesDelete(r -> r.file(fileObj.getId()));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
    }

    @Test
    public void createLongTextFile() throws IOException, SlackApiException {
        File file = new File("src/test/resources/sample_long.txt");
        com.slack.api.model.File fileObj;
        {
            FilesUploadResponse response = slack.methods(botToken).filesUpload(r -> r
                    .channels(Arrays.asList(channelId))
                    .file(file)
                    .filename("sample.txt")
                    .initialComment("initial comment")
                    .title("file title"));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            fileObj = response.getFile();

            assertThat(fileObj.getTitle(), is("file title"));
            assertThat(fileObj.getName(), is("sample.txt"));
            assertThat(fileObj.getMimetype(), is("text/plain"));
            assertThat(fileObj.getFiletype(), is("text"));
            assertThat(fileObj.getPrettyType(), is("Plain Text"));
            assertThat(fileObj.getSize(), is(19648));
            assertThat(fileObj.isEditable(), is(true));

            assertThat(fileObj.isPreviewTruncated(), is(true));
            assertThat(fileObj.getLines(), is(182));
            assertThat(fileObj.getLinesMore(), is(177));

            // image related attributes should be null
            assertThat(fileObj.getImageExifRotation(), is(nullValue()));
            assertThat(fileObj.getPjpeg(), is(nullValue()));
            assertThat(fileObj.getOriginalWidth(), is(nullValue()));
            assertThat(fileObj.getThumb360Width(), is(nullValue()));
            assertThat(fileObj.getThumb480Width(), is(nullValue()));
            assertThat(fileObj.getOriginalHeight(), is(nullValue()));
            assertThat(fileObj.getThumb360Height(), is(nullValue()));
            assertThat(fileObj.getThumb480Height(), is(nullValue()));

            assertThat(fileObj.getMode(), is("snippet"));
            assertThat(fileObj.isHasRichPreview(), is(false));
            assertThat(fileObj.isStarred(), is(false));
            assertThat(fileObj.isPublic(), is(true));
            assertThat(fileObj.isPublicUrlShared(), is(false));

            assertThat(fileObj.getChannels().size(), is(1));
            assertThat(fileObj.getGroups().size(), is(0));
            assertThat(fileObj.getIms().size(), is(0));

            assertThat(fileObj.getShares(), is(notNullValue()));
            assertThat(fileObj.getShares().getPublicChannels(), is(notNullValue()));
            assertThat(fileObj.getShares().getPublicChannels().get(channelId), is(notNullValue()));

            assertThat(fileObj.getShares().getPrivateChannels(), is(nullValue()));
        }

        {
            ChatDeleteResponse response = slack.methods(botToken).chatDelete(r -> r
                    .channel(channelId)
                    .ts(fileObj
                            .getShares()
                            .getPublicChannels().get(channelId).get(0)
                            .getTs())
            );
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            FilesDeleteResponse response = slack.methods(botToken).filesDelete(r -> r.file(fileObj.getId()));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
    }

    @Test
    public void createImageFileAndComments() throws IOException, SlackApiException {
        File file = new File("src/test/resources/seratch.jpg");
        com.slack.api.model.File fileObj;
        {
            FilesUploadResponse response = slack.methods().filesUpload(r -> r
                    .token(userToken)
                    .channels(Arrays.asList(channelId))
                    .file(file)
                    .filename("seratch.jpg")
                    .initialComment("This is me!")
                    .title("@seratch"));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            fileObj = response.getFile();
            assertThat(fileObj.getTitle(), is("@seratch"));
            assertThat(fileObj.getName(), is("seratch.jpg"));
            assertThat(fileObj.getMimetype(), is("image/jpeg"));
            assertThat(fileObj.getFiletype(), is("jpg"));
            assertThat(fileObj.getPrettyType(), is("JPEG"));
            assertThat(fileObj.getSize(), is(30317));
            assertThat(fileObj.isEditable(), is(false));

            if (fileObj.getImageExifRotation() != null) {
                // This field is occasionally returned as null
                assertThat(fileObj.getImageExifRotation(), is(1));
            }
            assertThat(fileObj.getPjpeg(), is(nullValue()));
            assertThat(fileObj.getOriginalWidth(), is("400"));
            assertThat(fileObj.getThumb360Width(), is("360"));
            assertThat(fileObj.getThumb480Width(), is(nullValue()));
            assertThat(fileObj.getOriginalHeight(), is("400"));
            assertThat(fileObj.getThumb360Height(), is("360"));
            assertThat(fileObj.getThumb480Height(), is(nullValue()));

            assertThat(fileObj.getMode(), is("hosted"));
            assertThat(fileObj.isHasRichPreview(), is(false));
            assertThat(fileObj.isStarred(), is(false));
            assertThat(fileObj.isPublic(), is(true));
            assertThat(fileObj.isPublicUrlShared(), is(false));

            assertThat(fileObj.getChannels().size(), is(1));
            assertThat(fileObj.getGroups().size(), is(0));
            assertThat(fileObj.getIms().size(), is(0));

            assertThat(fileObj.getShares(), is(notNullValue()));
            assertThat(fileObj.getShares().getPublicChannels(), is(notNullValue()));
            assertThat(fileObj.getShares().getPublicChannels().get(channelId), is(notNullValue()));

            assertThat(fileObj.getShares().getPrivateChannels(), is(nullValue()));
        }

        {
            FilesInfoResponse response = slack.methods().filesInfo(FilesInfoRequest.builder()
                    .token(userToken)
                    .file(fileObj.getId())
                    .build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            FilesSharedPublicURLResponse response = slack.methods().filesSharedPublicURL(
                    FilesSharedPublicURLRequest.builder().token(userToken).file(fileObj.getId()).build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            FilesRevokePublicURLResponse response = slack.methods().filesRevokePublicURL(
                    FilesRevokePublicURLRequest.builder().token(userToken).file(fileObj.getId()).build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            ChatDeleteResponse response = slack.methods().chatDelete(ChatDeleteRequest.builder()
                    .token(userToken)
                    .channel(channelId)
                    .ts(fileObj
                            .getShares()
                            .getPublicChannels().get(channelId).get(0)
                            .getTs())
                    .build()
            );
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            FilesDeleteResponse response = slack.methods().filesDelete(FilesDeleteRequest.builder()
                    .token(userToken)
                    .file(fileObj.getId())
                    .build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
    }

    @Test
    public void createFileForAThread() throws IOException, SlackApiException {
        TestChannelGenerator channelGenerator = new TestChannelGenerator(testConfig, userToken);
        Conversation channel = channelGenerator.createNewPublicChannel("test" + System.currentTimeMillis());

        try {
            ChatPostMessageResponse postMessageResponse = slack.methods().chatPostMessage(r -> r
                    .token(userToken)
                    .channel(channel.getId())
                    .text("This is a test message posted by unit tests for Java Slack SDK library")
                    .replyBroadcast(false));
            assertThat(postMessageResponse.getError(), is(nullValue()));
            assertThat(postMessageResponse.isOk(), is(true));

            ChatPostMessageResponse postThread1Response = slack.methods().chatPostMessage(r -> r
                    .token(userToken)
                    .channel(channel.getId())
                    .threadTs(postMessageResponse.getTs())
                    .text("[thread 1] This is a test message posted by unit tests for Java Slack SDK library")
                    .replyBroadcast(false));
            assertThat(postThread1Response.getError(), is(nullValue()));
            assertThat(postThread1Response.isOk(), is(true));

            File file = new File("src/test/resources/sample.txt");
            com.slack.api.model.File fileObj;
            {
                FilesUploadResponse response = slack.methods().filesUpload(r -> r
                        .token(userToken)
                        .file(file)
                        .filename("sample.txt")
                        .initialComment("initial comment")
                        .title("file title")
                        .threadTs(postThread1Response.getTs()));
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
                fileObj = response.getFile();
            }

            {
                FilesInfoResponse response = slack.methods().filesInfo(r -> r
                        .token(userToken)
                        .file(fileObj.getId()));
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }
        } finally {
            channelGenerator.archiveChannel(channel);
        }
    }

    @Test
    public void issue523_text() throws IOException, SlackApiException {
        MethodsClient slackMethods = slack.methods(userToken);
        File file = new File("src/test/resources/sample.txt");
        byte[] fileData = Files.readAllBytes(Paths.get(file.toURI()));
        FilesUploadResponse response = slackMethods.filesUpload(r -> r.fileData(fileData).filename("sample.txt"));
        assertThat(response.getError(), is(nullValue()));
    }

    @Test
    public void issue523_image() throws IOException, SlackApiException {
        MethodsClient slackMethods = slack.methods(userToken);
        File file = new File("src/test/resources/user_photo.jpg");
        byte[] fileData = Files.readAllBytes(Paths.get(file.toURI()));
        FilesUploadResponse response = slackMethods.filesUpload(r -> r.fileData(fileData).filename("sample.jpg"));
        assertThat(response.getError(), is(nullValue()));
    }

    @Test
    public void issue523_text_no_filename() throws IOException, SlackApiException {
        MethodsClient slackMethods = slack.methods(userToken);
        File file = new File("src/test/resources/sample.txt");
        byte[] fileData = Files.readAllBytes(Paths.get(file.toURI()));
        FilesUploadResponse response = slackMethods.filesUpload(r -> r.fileData(fileData));
        // assertThat(response.getError(), is("no_file_data"));
        assertThat(response.getError(), is(nullValue()));
    }

    @Test
    public void issue523_image_no_filename() throws IOException, SlackApiException {
        MethodsClient slackMethods = slack.methods(userToken);
        File file = new File("src/test/resources/user_photo.jpg");
        byte[] fileData = Files.readAllBytes(Paths.get(file.toURI()));
        FilesUploadResponse response = slackMethods.filesUpload(r -> r.fileData(fileData));
        // assertThat(response.getError(), is("no_file_data"));
        assertThat(response.getError(), is(nullValue()));
    }

    @Test
    public void uploadAndPostMessage() throws IOException, SlackApiException, InterruptedException {
        MethodsClient slackMethods = slack.methods(userToken);

        ChatPostMessageResponse message = slackMethods.chatPostMessage(r -> r
                .channel("#random")
                .asUser(true)
                .text("Uploading a file..."));
        assertThat(message.getError(), is(nullValue()));

        String channelId = message.getChannel();

        // Share the file in a private channel
        UsersConversationsResponse userPrivateChannels = slackMethods.usersConversations(r -> r
                .types(Arrays.asList(ConversationType.PRIVATE_CHANNEL)));
        assertThat(userPrivateChannels.getError(), is(nullValue()));
        assertThat(userPrivateChannels.getChannels().size(), is(greaterThan(0)));

        String privateChannelId = userPrivateChannels.getChannels().get(0).getId();

        File file = new File("src/test/resources/sample.txt");
        com.slack.api.model.File fileObj;
        {
            FilesUploadResponse response = slackMethods.filesUpload(r -> r
                    .file(file)
                    .initialComment("test")
                    .filetype("text")
                    .filename("sample.txt")
                    .title("file title"));
            assertThat(response.getError(), is(nullValue()));
            fileObj = response.getFile();
        }

        {
            // This operation doesn't attach the uploaded files
            ChatUpdateResponse modification = slackMethods.chatUpdate(ChatUpdateRequest.builder()
                    .channel(channelId)
                    .ts(message.getTs())
                    .text(fileObj.getPermalink())
                    .build());
            assertThat(modification.getError(), is(nullValue()));
        }

        {
            // This operation attaches the upload file in the message.
            ChatPostMessageResponse newMessage = slackMethods.chatPostMessage(ChatPostMessageRequest.builder()
                    .channel(channelId)
                    .text(fileObj.getPermalink())
                    .asUser(true)
                    .build());
            assertThat(newMessage.getError(), is(nullValue()));
        }

        {
            ChatPostMessageResponse newMessage = slackMethods.chatPostMessage(ChatPostMessageRequest.builder()
                    .channel(privateChannelId)
                    .text(fileObj.getPermalink())
                    .unfurlLinks(true)
                    .unfurlMedia(true)
                    .build());
            assertThat(newMessage.getError(), is(nullValue()));
        }

        Thread.sleep(2_000L);

        // verify if the shares is expected
        FilesInfoResponse fileInfo = slackMethods.filesInfo(r -> r.file(fileObj.getId()));
        assertThat(fileInfo.getError(), is(nullValue()));
        assertThat(fileInfo.getFile().getShares().getPublicChannels(), is(notNullValue()));
        assertThat(fileInfo.getFile().getShares().getPrivateChannels(), is(notNullValue()));

        FilesUploadResponse uploadAndSharedInTwoTypesOfChannels = slackMethods.filesUpload(r -> r
                .file(file)
                .initialComment("test")
                .filetype("text")
                .filename("sample.txt")
                .title("file title")
                .channels(Arrays.asList(channelId, privateChannelId))
        );
        assertThat(uploadAndSharedInTwoTypesOfChannels.getError(), is(nullValue()));
        assertThat(uploadAndSharedInTwoTypesOfChannels.getFile().getShares().getPublicChannels(), is(notNullValue()));
        assertThat(uploadAndSharedInTwoTypesOfChannels.getFile().getShares().getPrivateChannels(), is(notNullValue()));
    }

    @Test
    public void uploadInThreads() throws Exception {
        MethodsClient slackMethods = slack.methods(userToken);

        ChatPostMessageResponse message = slackMethods.chatPostMessage(r -> r
                .channel("#random")
                .text("Uploading a file in thread..."));
        assertThat(message.getError(), is(nullValue()));
        String threadTs = message.getTs();
        String channelId = message.getChannel();

        FilesUploadResponse upload1 = slackMethods.filesUpload(r -> r
                .channels(Arrays.asList(channelId))
                .threadTs(threadTs)
                .content("in thread")
        );
        assertThat(upload1.getError(), is(nullValue()));
        com.slack.api.model.File.ShareDetail share1 = upload1
                .getFile().getShares().getPublicChannels().get(channelId).get(0);
        assertThat(share1.getThreadTs(), is(threadTs));

        FilesUploadResponse upload2 = slackMethods.filesUpload(r -> r
                .channels(Arrays.asList(channelId))
                .threadTs(threadTs)
                .file(new File("src/test/resources/sample.txt"))
                .initialComment("test")
                .filetype("text")
                .filename("sample.txt")
                .title("file title"));
        assertThat(upload2.getError(), is(nullValue()));
        com.slack.api.model.File.ShareDetail share2 = upload2
                .getFile().getShares().getPublicChannels().get(channelId).get(0);
        assertThat(share2.getThreadTs(), is(threadTs));
    }

}
