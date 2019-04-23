package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsListRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatDeleteRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.methods.request.files.*;
import com.github.seratch.jslack.api.methods.request.files.comments.FilesCommentsAddRequest;
import com.github.seratch.jslack.api.methods.request.files.comments.FilesCommentsDeleteRequest;
import com.github.seratch.jslack.api.methods.request.files.comments.FilesCommentsEditRequest;
import com.github.seratch.jslack.api.methods.response.chat.ChatDeleteResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.methods.response.files.*;
import com.github.seratch.jslack.api.methods.response.files.comments.FilesCommentsAddResponse;
import com.github.seratch.jslack.api.methods.response.files.comments.FilesCommentsDeleteResponse;
import com.github.seratch.jslack.api.methods.response.files.comments.FilesCommentsEditResponse;
import com.github.seratch.jslack.api.model.Conversation;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import testing.Constants;
import testing.SlackTestConfig;
import testing.TestChannelGenerator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class Slack_files_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void describe() throws IOException, SlackApiException {
        {
            FilesListResponse response = slack.methods().filesList(FilesListRequest.builder().token(token).build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getFiles(), is(notNullValue()));
        }
    }

    @Test
    public void createTextFileAndComments() throws IOException, SlackApiException {
        List<String> channels = slack.methods().channelsList(ChannelsListRequest.builder().token(token).build())
                .getChannels().stream()
                .filter(c -> c.getName().equals("general"))
                .map(c -> c.getId()).collect(toList());
        String channelId = channels.get(0);

        File file = new File("src/test/resources/sample.txt");
        com.github.seratch.jslack.api.model.File fileObj;
        {
            FilesUploadResponse response = slack.methods().filesUpload(FilesUploadRequest.builder()
                    .token(token)
                    .channels(channels)
                    .file(file)
                    .filename("sample.txt")
                    .initialComment("initial comment")
                    .title("file title")
                    .build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            fileObj = response.getFile();

            /*
{
  "id": "FHH2J____",
  "created": 1554107549,
  "timestamp": 1554107549,
  "name": "sample.txt",
  "title": "file title",
  "mimetype": "text/plain",
  "filetype": "text",
  "pretty_type": "Plain Text",
  "user": "U03E9____",
  "editable": true,
  "size": 57,
  "mode": "snippet",
  "is_external": false,
  "external_type": "",
  "is_public": true,
  "public_url_shared": false,
  "display_as_bot": false,
  "username": "",
  "url_private": "https://files.slack.com/files-pri/T03E9____-FHH2J____/sample.txt",
  "url_private_download": "https://files.slack.com/files-pri/T03E9____-FHH2J____/download/sample.txt",
  "permalink": "https://seratch.slack.com/files/U03E9____/FHH2J____/sample.txt",
  "permalink_public": "https://slack-files.com/T03E9____-FHH2J____-bfa09b78b0",
  "edit_link": "https://seratch.slack.com/files/U03E9____/FHH2J____/sample.txt/edit",
  "preview": "Hello, World!!!!!\n\nThis is a sample text file.\n\n日本語",
  "preview_highlight": "<div class=\"CodeMirror cm-s-default CodeMirrorServer\" oncopy=\"if(event.clipboardData){event.clipboardData.setData('text/plain',window.getSelection().toString().replace(/\\u200b/g,''));event.preventDefault();event.stopPropagation();}\">\n<div class=\"CodeMirror-code\">\n<div><pre>Hello, World!!!!!</pre></div>\n<div><pre></pre></div>\n<div><pre>This is a sample text file.</pre></div>\n<div><pre></pre></div>\n<div><pre>日本語</pre></div>\n</div>\n</div>\n",
  "lines": 5,
  "lines_more": 0,
  "preview_is_truncated": false,
  "comments_count": 0,
  "is_starred": false,
  "shares": {
    "public": {
      "C03E9____": [
        {
          "reply_users": [],
          "reply_users_count": 0,
          "reply_count": 0,
          "ts": "1554107549.000600",
          "channel_name": "general",
          "team_id": "T03E9____"
        }
      ]
    }
  },
  "channels": [
    "C03E9____"
  ],
  "groups": [],
  "ims": [],
  "has_rich_preview": false
}
             */
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
            FilesInfoResponse response = slack.methods().filesInfo(FilesInfoRequest.builder()
                    .token(token)
                    .file(fileObj.getId())
                    .build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            FilesSharedPublicURLResponse response = slack.methods().filesSharedPublicURL(
                    FilesSharedPublicURLRequest.builder().token(token).file(fileObj.getId()).build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            FilesRevokePublicURLResponse response = slack.methods().filesRevokePublicURL(
                    FilesRevokePublicURLRequest.builder().token(token).file(fileObj.getId()).build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        // comments
        {
            FilesCommentsAddResponse addResponse = slack.methods().filesCommentsAdd(FilesCommentsAddRequest.builder()
                    .token(token)
                    .file(fileObj.getId())
                    .comment("test comment")
                    .build());
            assertThat(addResponse.getError(), is(nullValue()));
            assertThat(addResponse.isOk(), is(true));

            FilesInfoResponse filesInfoResponse = slack.methods().filesInfo(FilesInfoRequest.builder()
                    .token(token)
                    .file(fileObj.getId())
                    .build());
            assertThat(filesInfoResponse.getError(), is(nullValue()));
            assertThat(filesInfoResponse.isOk(), is(true));
            fileObj = filesInfoResponse.getFile();
            assertThat(fileObj.getCommentsCount(), is(1));

            FilesCommentsEditResponse editResponse = slack.methods().filesCommentEdit(FilesCommentsEditRequest.builder()
                    .token(token)
                    .file(fileObj.getId())
                    .id(addResponse.getComment().getId())
                    .comment("modified comment")
                    .build());
            assertThat(editResponse.getError(), is(nullValue()));
            assertThat(editResponse.isOk(), is(true));

            FilesCommentsDeleteResponse deleteResponse = slack.methods().filesCommentsDelete(FilesCommentsDeleteRequest.builder()
                    .token(token)
                    .file(fileObj.getId())
                    .id(addResponse.getComment().getId()).build()
            );
            assertThat(deleteResponse.getError(), is(nullValue()));
            assertThat(deleteResponse.isOk(), is(true));

            filesInfoResponse = slack.methods().filesInfo(FilesInfoRequest.builder()
                    .token(token)
                    .file(fileObj.getId())
                    .build());
            assertThat(filesInfoResponse.getError(), is(nullValue()));
            assertThat(filesInfoResponse.isOk(), is(true));
            fileObj = filesInfoResponse.getFile();
            assertThat(fileObj.getCommentsCount(), is(0));
        }

        {
            ChatDeleteResponse response = slack.methods().chatDelete(ChatDeleteRequest.builder()
                    .token(token)
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
                    .token(token)
                    .file(fileObj.getId())
                    .build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
    }

    @Test
    public void createLongTextFile() throws IOException, SlackApiException {
        List<String> channels = slack.methods().channelsList(ChannelsListRequest.builder().token(token).build())
                .getChannels().stream()
                .filter(c -> c.getName().equals("general"))
                .map(c -> c.getId()).collect(toList());
        String channelId = channels.get(0);

        File file = new File("src/test/resources/sample_long.txt");
        com.github.seratch.jslack.api.model.File fileObj;
        {
            FilesUploadResponse response = slack.methods().filesUpload(FilesUploadRequest.builder()
                    .token(token)
                    .channels(channels)
                    .file(file)
                    .filename("sample.txt")
                    .initialComment("initial comment")
                    .title("file title")
                    .build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            fileObj = response.getFile();

            /*
{
  "size": 19648,
  "lines": 182,
  "lines_more": 177,
  "preview_is_truncated": true
}
             */

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
            ChatDeleteResponse response = slack.methods().chatDelete(ChatDeleteRequest.builder()
                    .token(token)
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
                    .token(token)
                    .file(fileObj.getId())
                    .build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
    }

    @Test
    public void createImageFileAndComments() throws IOException, SlackApiException {
        List<String> channels = slack.methods().channelsList(ChannelsListRequest.builder().token(token).build())
                .getChannels().stream()
                .filter(c -> c.getName().equals("general"))
                .map(c -> c.getId()).collect(toList());
        String channelId = channels.get(0);

        File file = new File("src/test/resources/seratch.jpg");
        com.github.seratch.jslack.api.model.File fileObj;
        {
            FilesUploadResponse response = slack.methods().filesUpload(FilesUploadRequest.builder()
                    .token(token)
                    .channels(channels)
                    .file(file)
                    .filename("seratch.jpg")
                    .initialComment("This is me!")
                    .title("@seratch")
                    .build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            fileObj = response.getFile();

            /*
{
  "id": "FHELY____",
  "created": 1554108289,
  "timestamp": 1554108289,
  "name": "seratch.jpg",
  "title": "@seratch",
  "mimetype": "image/jpeg",
  "filetype": "jpg",
  "pretty_type": "JPEG",
  "user": "U03E9____",
  "editable": false,
  "size": 29720,
  "mode": "hosted",
  "is_external": false,
  "external_type": "",
  "is_public": true,
  "public_url_shared": false,
  "display_as_bot": false,
  "username": "",
  "url_private": "https://files.slack.com/files-pri/T03E9____-FHELY____/seratch.jpg",
  "url_private_download": "https://files.slack.com/files-pri/T03E9____-FHELY____/download/seratch.jpg",
  "thumb_64": "https://files.slack.com/files-tmb/T03E9____-FHELY____-560e2b1db6/seratch_64.jpg",
  "thumb_80": "https://files.slack.com/files-tmb/T03E9____-FHELY____-560e2b1db6/seratch_80.jpg",
  "thumb_360": "https://files.slack.com/files-tmb/T03E9____-FHELY____-560e2b1db6/seratch_360.jpg",
  "thumb_360_w": 360,
  "thumb_360_h": 360,
  "thumb_160": "https://files.slack.com/files-tmb/T03E9____-FHELY____-560e2b1db6/seratch_160.jpg",
  "image_exif_rotation": 1,
  "original_w": 400,
  "original_h": 400,
  "permalink": "https://seratch.slack.com/files/U03E9____/FHELY____/seratch.jpg",
  "permalink_public": "https://slack-files.com/T03E9____-FHELY____-00d7e651ee",
  "comments_count": 0,
  "is_starred": false,
  "shares": {
    "public": {
      "C03E9____": [
        {
          "reply_users": [],
          "reply_users_count": 0,
          "reply_count": 0,
          "ts": "1554108291.001800",
          "channel_name": "general",
          "team_id": "T03E9____"
        }
      ]
    }
  },
  "channels": [
    "C03E9____"
  ],
  "groups": [],
  "ims": [],
  "has_rich_preview": false
}
             */
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
                    .token(token)
                    .file(fileObj.getId())
                    .build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            FilesSharedPublicURLResponse response = slack.methods().filesSharedPublicURL(
                    FilesSharedPublicURLRequest.builder().token(token).file(fileObj.getId()).build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            FilesRevokePublicURLResponse response = slack.methods().filesRevokePublicURL(
                    FilesRevokePublicURLRequest.builder().token(token).file(fileObj.getId()).build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        // comments
        {
            FilesCommentsAddResponse addResponse = slack.methods().filesCommentsAdd(FilesCommentsAddRequest.builder()
                    .token(token)
                    .file(fileObj.getId())
                    .comment("test comment")
                    .build());
            assertThat(addResponse.getError(), is(nullValue()));
            assertThat(addResponse.isOk(), is(true));

            FilesInfoResponse filesInfoResponse = slack.methods().filesInfo(FilesInfoRequest.builder()
                    .token(token)
                    .file(fileObj.getId())
                    .build());
            assertThat(filesInfoResponse.getError(), is(nullValue()));
            assertThat(filesInfoResponse.isOk(), is(true));
            fileObj = filesInfoResponse.getFile();
            assertThat(fileObj.getCommentsCount(), is(1));

            FilesCommentsEditResponse editResponse = slack.methods().filesCommentEdit(FilesCommentsEditRequest.builder()
                    .token(token)
                    .file(fileObj.getId())
                    .id(addResponse.getComment().getId())
                    .comment("modified comment")
                    .build());
            assertThat(editResponse.getError(), is(nullValue()));
            assertThat(editResponse.isOk(), is(true));

            FilesCommentsDeleteResponse deleteResponse = slack.methods().filesCommentsDelete(FilesCommentsDeleteRequest.builder()
                    .token(token)
                    .file(fileObj.getId())
                    .id(addResponse.getComment().getId()).build()
            );
            assertThat(deleteResponse.getError(), is(nullValue()));
            assertThat(deleteResponse.isOk(), is(true));

            filesInfoResponse = slack.methods().filesInfo(FilesInfoRequest.builder()
                    .token(token)
                    .file(fileObj.getId())
                    .build());
            assertThat(filesInfoResponse.getError(), is(nullValue()));
            assertThat(filesInfoResponse.isOk(), is(true));
            fileObj = filesInfoResponse.getFile();
            assertThat(fileObj.getCommentsCount(), is(0));
        }

        {
            ChatDeleteResponse response = slack.methods().chatDelete(ChatDeleteRequest.builder()
                    .token(token)
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
                    .token(token)
                    .file(fileObj.getId())
                    .build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
    }

    @Test
    public void createFileForAThread() throws IOException, SlackApiException {
        TestChannelGenerator channelGenerator = new TestChannelGenerator(token);
        Conversation channel = channelGenerator.createNewPublicChannel("test" + System.currentTimeMillis());

        try {
            ChatPostMessageResponse postMessageResponse = slack.methods().chatPostMessage(
                    ChatPostMessageRequest.builder()
                            .token(token)
                            .channel(channel.getId())
                            .text("This is a test message posted by unit tests for jslack library")
                            .replyBroadcast(false)
                            .build());
            assertThat(postMessageResponse.getError(), is(nullValue()));
            assertThat(postMessageResponse.isOk(), is(true));

            ChatPostMessageResponse postThread1Response = slack.methods().chatPostMessage(
                    ChatPostMessageRequest.builder()
                            .token(token)
                            .channel(channel.getId())
                            .threadTs(postMessageResponse.getTs())
                            .text("[thread 1] This is a test message posted by unit tests for jslack library")
                            .replyBroadcast(false)
                            .build());
            assertThat(postThread1Response.getError(), is(nullValue()));
            assertThat(postThread1Response.isOk(), is(true));

            File file = new File("src/test/resources/sample.txt");
            com.github.seratch.jslack.api.model.File fileObj;
            {
                FilesUploadResponse response = slack.methods().filesUpload(FilesUploadRequest.builder()
                        .token(token)
                        .file(file)
                        .filename("sample.txt")
                        .initialComment("initial comment")
                        .title("file title")
                        .threadTs(postThread1Response.getTs())
                        .build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
                fileObj = response.getFile();
            }

            {
                FilesInfoResponse response = slack.methods().filesInfo(FilesInfoRequest.builder()
                        .token(token)
                        .file(fileObj.getId())
                        .build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }
        } finally {
            channelGenerator.archiveChannel(channel);
        }
    }

}