package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsListRequest;
import com.github.seratch.jslack.api.methods.request.files.FilesUploadRequest;
import com.github.seratch.jslack.api.methods.request.stars.StarsAddRequest;
import com.github.seratch.jslack.api.methods.request.stars.StarsListRequest;
import com.github.seratch.jslack.api.methods.request.stars.StarsRemoveRequest;
import com.github.seratch.jslack.api.methods.response.files.FilesUploadResponse;
import com.github.seratch.jslack.api.methods.response.stars.StarsAddResponse;
import com.github.seratch.jslack.api.methods.response.stars.StarsListResponse;
import com.github.seratch.jslack.api.methods.response.stars.StarsRemoveResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Slf4j
public class Slack_stars_Test {

    Slack slack = new Slack();
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void list() throws IOException, SlackApiException {
        StarsListResponse response = slack.methods().starsList(StarsListRequest.builder().token(token).build());
        assertThat(response.isOk(), is(true));
        assertThat(response.getItems(), is(notNullValue()));
    }

    @Test
    public void add() throws IOException, SlackApiException {
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
            StarsAddResponse response = slack.methods().starsAdd(StarsAddRequest.builder()
                    .token(token)
                    .channel(channels.get(0))
                    .file(fileObj.getId())
                    .build());
            assertThat(response.isOk(), is(true));
        }
        {
            StarsRemoveResponse response = slack.methods().starsRemove(StarsRemoveRequest.builder()
                    .token(token)
                    .channel(channels.get(0))
                    .file(fileObj.getId())
                    .build());
            assertThat(response.isOk(), is(true));
        }

        {
            // as of August 2018, File object no longer contains initialComment.
            if (fileObj.getInitialComment() != null) {
                StarsAddResponse response = slack.methods().starsAdd(StarsAddRequest.builder()
                        .token(token)
                        .channel(channels.get(0))
                        .fileComment(fileObj.getInitialComment().getId())
                        .build());
                assertThat(response.isOk(), is(true));
            }
        }

    }

}