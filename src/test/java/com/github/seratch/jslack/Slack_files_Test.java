package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsListRequest;
import com.github.seratch.jslack.api.methods.request.files.*;
import com.github.seratch.jslack.api.methods.request.files.comments.FilesCommentsAddRequest;
import com.github.seratch.jslack.api.methods.request.files.comments.FilesCommentsDeleteRequest;
import com.github.seratch.jslack.api.methods.request.files.comments.FilesCommentsEditRequest;
import com.github.seratch.jslack.api.methods.response.files.*;
import com.github.seratch.jslack.api.methods.response.files.comments.FilesCommentsAddResponse;
import com.github.seratch.jslack.api.methods.response.files.comments.FilesCommentsDeleteResponse;
import com.github.seratch.jslack.api.methods.response.files.comments.FilesCommentsEditResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Slf4j
public class Slack_files_Test {

    Slack slack = Slack.getInstance();
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void describe() throws IOException, SlackApiException {
        {
            FilesListResponse response = slack.methods().filesList(FilesListRequest.builder().token(token).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getFiles(), is(notNullValue()));
        }
    }

    @Test
    public void createFileAndComments() throws IOException, SlackApiException {
        List<String> channels = slack.methods().channelsList(ChannelsListRequest.builder().token(token).build())
                .getChannels().stream()
                .filter(c -> c.getName().equals("general"))
                .map(c -> c.getId()).collect(toList());

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
            assertThat(response.isOk(), is(true));
            fileObj = response.getFile();
        }

        {
            FilesInfoResponse response = slack.methods().filesInfo(FilesInfoRequest.builder()
                    .token(token)
                    .file(fileObj.getId())
                    .build());
            assertThat(response.isOk(), is(true));
        }

        {
            FilesSharedPublicURLResponse response = slack.methods().filesSharedPublicURL(
                    FilesSharedPublicURLRequest.builder().token(token).file(fileObj.getId()).build());
            assertThat(response.isOk(), is(true));
        }

        {
            FilesRevokePublicURLResponse response = slack.methods().filesRevokePublicURL(
                    FilesRevokePublicURLRequest.builder().token(token).file(fileObj.getId()).build());
            assertThat(response.isOk(), is(true));
        }

        // comments
        {
            FilesCommentsAddResponse addResponse = slack.methods().filesCommentsAdd(FilesCommentsAddRequest.builder()
                    .token(token)
                    .file(fileObj.getId())
                    .comment("test comment")
                    .build());
            assertThat(addResponse.isOk(), is(true));

            FilesCommentsEditResponse editResponse = slack.methods().filesCommentEdit(FilesCommentsEditRequest.builder()
                    .token(token)
                    .file(fileObj.getId())
                    .id(addResponse.getComment().getId())
                    .comment("modified comment")
                    .build());
            assertThat(editResponse.isOk(), is(true));

            FilesCommentsDeleteResponse deleteResponse = slack.methods().filesCommentsDelete(FilesCommentsDeleteRequest.builder()
                    .token(token)
                    .file(fileObj.getId())
                    .id(addResponse.getComment().getId()).build()
            );
            assertThat(deleteResponse.isOk(), is(true));
        }

        {
            FilesDeleteResponse response = slack.methods().filesDelete(FilesDeleteRequest.builder()
                    .token(token)
                    .file(fileObj.getId())
                    .build());
            assertThat(response.isOk(), is(true));
        }
    }

}