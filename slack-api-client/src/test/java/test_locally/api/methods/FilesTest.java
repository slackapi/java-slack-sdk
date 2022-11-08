package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.files.FilesCompleteUploadExternalRequest;
import com.slack.api.methods.request.files.comments.FilesCommentsAddRequest;
import com.slack.api.methods.request.files.comments.FilesCommentsDeleteRequest;
import com.slack.api.methods.request.files.comments.FilesCommentsEditRequest;
import com.slack.api.methods.response.files.FilesUploadResponse;
import com.slack.api.methods.response.files.FilesUploadV2Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static util.MockSlackApi.ValidToken;

public class FilesTest {

    MockSlackApiServer server = new MockSlackApiServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    @Before
    public void setup() throws Exception {
        server.start();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void test() throws Exception {
        assertThat(slack.methods(ValidToken).filesDelete(r -> r.file("F123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).filesInfo(r -> r.file("F123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).filesList(r -> r.channel("C123").types(Arrays.asList("")).count(1).page(10))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).filesRevokePublicURL(r -> r.file("F123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).filesSharedPublicURL(r -> r.file("F123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).filesUpload(r -> r.filename("name").channels(Arrays.asList("C123")).title("title"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).filesUploadV2(r -> r.content("something").filename("name").channel("C123").title("title"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).filesGetUploadURLExternal(r -> r.filename("name").length(100))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).filesCompleteUploadExternal(r -> r
                        .files(Arrays.asList(FilesCompleteUploadExternalRequest.FileDetails.builder().id("F111").build()))
                        .channelId("C111")
                        .initialComment("Here are files!")
                ).isOk(), is(true));
    }

    @Test
    public void test_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).filesDelete(r -> r.file("F123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesInfo(r -> r.file("F123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesList(r -> r.channel("C123").types(Arrays.asList("")).count(1).page(10))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesRevokePublicURL(r -> r.file("F123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesSharedPublicURL(r -> r.file("F123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesUpload(r -> r.filename("name").channels(Arrays.asList("C123")).title("title"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesUploadV2(r -> r.content("something").filename("name").channel("C123").title("title"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesGetUploadURLExternal(r -> r.filename("name").length(100))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesCompleteUploadExternal(r -> r
                .files(Arrays.asList(FilesCompleteUploadExternalRequest.FileDetails.builder().id("F111").build()))
                .channelId("C111")
                .initialComment("Here are files!")
        ).get().isOk(), is(true));
    }

    @Test
    public void fileUpload_bytes() throws Exception {
        byte[] fileData = "This is a text data".getBytes();
        FilesUploadResponse response = slack.methods(ValidToken).filesUpload(r ->
                r.fileData(fileData).filename("sample.txt").title("sample.txt").filetype("plain/text"));
        assertThat(response.isOk(), is(true));

        response = slack.methodsAsync(ValidToken).filesUpload(r ->
                r.fileData(fileData).filename("sample.txt").title("sample.txt").filetype("plain/text")).get();
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void fileUploadV2_bytes() throws Exception {
        byte[] fileData = "This is a text data".getBytes();
        FilesUploadV2Response response = slack.methods(ValidToken).filesUploadV2(r ->
                r.fileData(fileData).filename("sample.txt").title("sample.txt"));
        assertThat(response.isOk(), is(true));

        response = slack.methodsAsync(ValidToken).filesUploadV2(r ->
                r.fileData(fileData).filename("sample.txt").title("sample.txt")).get();
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void fileUpload_file() throws Exception {
        File file = new File("src/test/resources/sample.txt");
        FilesUploadResponse response = slack.methods(ValidToken).filesUpload(r ->
                r.file(file).filename("sample.txt").title("sample.txt").filetype("plain/text"));
        assertThat(response.isOk(), is(true));

        response = slack.methodsAsync(ValidToken).filesUpload(r ->
                r.file(file).filename("sample.txt").title("sample.txt").filetype("plain/text")).get();
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void fileUploadV2_file() throws Exception {
        File file = new File("src/test/resources/sample.txt");
        FilesUploadV2Response response = slack.methods(ValidToken).filesUploadV2(r ->
                r.file(file).filename("sample.txt").title("sample.txt"));
        assertThat(response.isOk(), is(true));

        response = slack.methods(ValidToken).filesUploadV2(r ->
                r.file(file).filename("sample.txt").title("sample.txt").requestFileInfo(false));
        assertThat(response.isOk(), is(true));

        response = slack.methodsAsync(ValidToken).filesUploadV2(r ->
                r.file(file).filename("sample.txt").title("sample.txt")).get();
        assertThat(response.isOk(), is(true));

        response = slack.methodsAsync(ValidToken).filesUploadV2(r ->
                r.file(file).filename("sample.txt").title("sample.txt").requestFileInfo(false)).get();
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void fileComments() throws IOException, SlackApiException {
        assertTrue(slack.methods(ValidToken)
                .filesCommentsAdd(FilesCommentsAddRequest.builder().comment("comment").file("file").build()).isOk());
        assertTrue(slack.methods(ValidToken)
                .filesCommentEdit(FilesCommentsEditRequest.builder().id("id").comment("comment").file("file").build()).isOk());
        assertTrue(slack.methods(ValidToken)
                .filesCommentsDelete(FilesCommentsDeleteRequest.builder().id("id").file("file").build()).isOk());
    }

}
