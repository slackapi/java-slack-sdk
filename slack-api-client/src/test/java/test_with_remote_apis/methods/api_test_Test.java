package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.Methods;
import com.slack.api.methods.RequestFormBuilder;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.api.ApiTestRequest;
import com.slack.api.methods.response.api.ApiTestResponse;
import com.slack.api.util.http.SlackHttpClient;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class api_test_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void ok() throws IOException, SlackApiException {
        ApiTestResponse response = slack.methods().apiTest(req -> req.foo("fine"));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getArgs().getFoo(), is("fine"));
    }

    @Test
    public void ok_async() throws ExecutionException, InterruptedException {
        ApiTestResponse response = slack.methodsAsync().apiTest(req -> req.foo("fine")).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getArgs().getFoo(), is("fine"));
    }

    @Test
    public void error() throws IOException, SlackApiException {
        ApiTestResponse response = slack.methods().apiTest(req -> req.error("error"));
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is("error"));
        assertThat(response.getArgs().getError(), is("error"));
    }

    @Test
    public void error_async() throws ExecutionException, InterruptedException {
        ApiTestResponse response = slack.methodsAsync().apiTest(req -> req.error("error")).get();
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is("error"));
        assertThat(response.getArgs().getError(), is("error"));
    }

    @Ignore
    @Test
    public void proxy() throws IOException, SlackApiException {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
        OkHttpClient okHttpClient = new OkHttpClient.Builder().proxy(proxy).build();
        SlackHttpClient slackHttpClient = new SlackHttpClient(okHttpClient);
        SlackTestConfig testConfig = SlackTestConfig.getInstance();
        Slack slack = Slack.getInstance(testConfig.getConfig(), slackHttpClient);

        ApiTestResponse response = slack.methods().apiTest(req -> req.foo("proxy?"));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getArgs().getFoo(), is("proxy?"));
    }

    @Test
    public void rawResponse() throws IOException {
        ApiTestRequest request = ApiTestRequest.builder().foo("fine").build();
        Response response = slack.methods().runPostForm(RequestFormBuilder.toForm(request), Methods.API_TEST);
        assertThat(response.code(), is(200));
        String body = response.body().string();
        assertThat(body, is("{\"ok\":true,\"args\":{\"foo\":\"fine\"}}"));
    }

}
