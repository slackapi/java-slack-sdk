package test_with_remote_apis.methods.samples;

import com.slack.api.Slack;
import com.slack.api.methods.response.files.FilesUploadResponse;
import config.Constants;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * For https://github.com/slackapi/java-slack-sdk/issues/453
 */
public class Issue453_DownloadingFiles_Test {

    @Test
    public void sample() throws Exception {
        Slack slack = Slack.getInstance();
        String token = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
        FilesUploadResponse uploadResult = slack.methods(token)
                .filesUpload(r -> r.content("This is a file!").filename("sample.txt"));

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(uploadResult.getFile().getUrlPrivateDownload())
                .header("Authorization", "Bearer " + token)
                .build();
        Response response = okHttpClient.newCall(request).execute();

        // You can find the file at slack-api-client/target/sample.txt
        Path localFilepath = Paths.get("target/sample.txt");
        Files.write(localFilepath, response.body().bytes());

        String body = Files.readAllLines(localFilepath).stream().collect(joining());
        assertThat(body, is("This is a file!"));
    }
}
