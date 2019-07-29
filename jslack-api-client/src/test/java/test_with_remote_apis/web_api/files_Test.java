package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.MethodsClient;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.chat.ChatDeleteRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatUpdateRequest;
import com.github.seratch.jslack.api.methods.request.files.FilesDeleteRequest;
import com.github.seratch.jslack.api.methods.request.files.FilesInfoRequest;
import com.github.seratch.jslack.api.methods.request.files.FilesRevokePublicURLRequest;
import com.github.seratch.jslack.api.methods.request.files.FilesSharedPublicURLRequest;
import com.github.seratch.jslack.api.methods.response.chat.ChatDeleteResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatUpdateResponse;
import com.github.seratch.jslack.api.methods.response.files.*;
import com.github.seratch.jslack.api.model.Channel;
import com.github.seratch.jslack.api.model.Conversation;
import com.github.seratch.jslack.shortcut.model.ApiToken;
import com.github.seratch.jslack.shortcut.model.ChannelName;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.TestChannelGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@Slf4j
public class files_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String userToken = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
    String botToken = System.getenv(Constants.SLACK_BOT_USER_TEST_OAUTH_ACCESS_TOKEN);

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
        List<Channel> channels_ = slack.methods().channelsList(r -> r.token(botToken)).getChannels();
        List<String> channels = new ArrayList<>();
        for (Channel c : channels_) {
            if (c.getName().equals("random")) {
                channels.add(c.getId());
                break;
            }
        }
        String channelId = channels.get(0);

        File file = new File("src/test/resources/sample.txt");
        final com.github.seratch.jslack.api.model.File fileObj;
        {
            FilesUploadResponse response = slack.methods(botToken).filesUpload(r -> r
                    .channels(channels)
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
            assertThat(fileObj.getShares().getPublicChannels().get(channels.get(0)), is(notNullValue()));

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
        List<Channel> channels_ = slack.methods().channelsList(r -> r.token(botToken)).getChannels();
        List<String> channels = new ArrayList<>();
        for (Channel c : channels_) {
            if (c.getName().equals("random")) {
                channels.add(c.getId());
                break;
            }
        }
        String channelId = channels.get(0);

        File file = new File("src/test/resources/sample_long.txt");
        com.github.seratch.jslack.api.model.File fileObj;
        {
            FilesUploadResponse response = slack.methods(botToken).filesUpload(r -> r
                    .channels(channels)
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
            assertThat(fileObj.getShares().getPublicChannels().get(channels.get(0)), is(notNullValue()));

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
        List<Channel> channels_ = slack.methods().channelsList(r -> r.token(userToken)).getChannels();
        List<String> channels = new ArrayList<>();
        for (Channel c : channels_) {
            if (c.getName().equals("random")) {
                channels.add(c.getId());
                break;
            }
        }
        String channelId = channels.get(0);

        File file = new File("src/test/resources/seratch.jpg");
        com.github.seratch.jslack.api.model.File fileObj;
        {
            FilesUploadResponse response = slack.methods().filesUpload(r -> r
                    .token(userToken)
                    .channels(channels)
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
            assertThat(fileObj.getSize(), is(29720));
            assertThat(fileObj.isEditable(), is(false));

            assertThat(fileObj.getImageExifRotation(), is(1));
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
            assertThat(fileObj.getShares().getPublicChannels().get(channels.get(0)), is(notNullValue()));

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
        TestChannelGenerator channelGenerator = new TestChannelGenerator(userToken);
        Conversation channel = channelGenerator.createNewPublicChannel("test" + System.currentTimeMillis());

        try {
            ChatPostMessageResponse postMessageResponse = slack.methods().chatPostMessage(r -> r
                    .token(userToken)
                    .channel(channel.getId())
                    .text("This is a test message posted by unit tests for jslack library")
                    .replyBroadcast(false));
            assertThat(postMessageResponse.getError(), is(nullValue()));
            assertThat(postMessageResponse.isOk(), is(true));

            ChatPostMessageResponse postThread1Response = slack.methods().chatPostMessage(r -> r
                    .token(userToken)
                    .channel(channel.getId())
                    .threadTs(postMessageResponse.getTs())
                    .text("[thread 1] This is a test message posted by unit tests for jslack library")
                    .replyBroadcast(false));
            assertThat(postThread1Response.getError(), is(nullValue()));
            assertThat(postThread1Response.isOk(), is(true));

            File file = new File("src/test/resources/sample.txt");
            com.github.seratch.jslack.api.model.File fileObj;
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
    public void uploadAndPostMessage() throws IOException, SlackApiException {
        String channelId = slack.shortcut(ApiToken.of(botToken))
                .findChannelIdByName(ChannelName.of("random"))
                .get().getValue();

        MethodsClient slackMethods = slack.methods(userToken);

        ChatPostMessageResponse message = slackMethods.chatPostMessage(r -> r
                .channel(channelId)
                .asUser(true)
                .text("Uploading a file..."));
        assertThat(message.getError(), is(nullValue()));

        File file = new File("src/test/resources/sample.txt");
        com.github.seratch.jslack.api.model.File fileObj;
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
    }

}