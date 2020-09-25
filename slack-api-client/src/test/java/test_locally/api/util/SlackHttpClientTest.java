package test_locally.api.util;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.api.ApiTestResponse;
import com.slack.api.util.http.SlackHttpClient;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Response;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SlackHttpClientTest {

    @Test
    public void close_Slack() throws Exception {
        {
            Slack slack = Slack.getInstance();
            MethodsClient methods = slack.methods();
            ApiTestResponse response1 = methods.apiTest(r -> r.foo("bar"));
            slack.close();
            assertThat(response1.isOk(), is(true));

            ApiTestResponse response2 = methods.apiTest(r -> r.foo("bar"));
            slack.close();
            assertThat(response2.isOk(), is(true));
        }

        try (Slack slack = Slack.getInstance()) {
            MethodsClient methods = slack.methods();
            ApiTestResponse response3 = methods.apiTest(r -> r.foo("bar"));
            assertThat(response3.isOk(), is(true));
        }
    }

    @Test
    public void close_SlackHttpClient() throws Exception {
        FormBody body = new FormBody.Builder().build();
        {
            SlackHttpClient httpClient = new SlackHttpClient();
            for (int i = 0; i < 3; i++) {
                Response response = httpClient.postForm("https://slack.com/api/api.test", body);
                assertThat(response.code(), is(200));
            }
            httpClient.close();
        }

        try (SlackHttpClient httpClient = new SlackHttpClient()) {
            Response response = httpClient.postForm("https://slack.com/api/api.test", body);
            assertThat(response.code(), is(200));
        }
    }

    // https://github.com/slackapi/java-slack-sdk/issues/566
    @Test
    public void shouldNotRevealAuthorizationHeaderEvenIfItIsInvalid() throws Exception {
        String url = "http://localhost:123/";
        String expectedErrorMessage = "Invalid value detected for Authorization header";
        String token = "xoxb-111-222-zzz\nfoo-bar";
        try (SlackHttpClient httpClient = new SlackHttpClient()) {
            FormBody body = new FormBody.Builder().build();
            try {
                httpClient.postFormWithBearerHeader(url, token, body);
                fail();
            } catch (IllegalArgumentException e) {
                assertEquals(expectedErrorMessage, e.getMessage());
            }
            try {
                httpClient.postFormWithAuthorizationHeader(url, "Bearer " + token, body);
                fail();
            } catch (IllegalArgumentException e) {
                assertEquals(expectedErrorMessage, e.getMessage());
            }
            MultipartBody multipartBody = new MultipartBody.Builder()
                    .addFormDataPart("foo", "bar")
                    .build();
            try {
                httpClient.postMultipart(url, token, multipartBody);
                fail();
            } catch (IllegalArgumentException e) {
                assertEquals(expectedErrorMessage, e.getMessage());
            }
            try {
                httpClient.postCamelCaseJsonBodyWithBearerHeader(url,  token, body);
                fail();
            } catch (IllegalArgumentException e) {
                assertEquals(expectedErrorMessage, e.getMessage());
            }
            try {
                httpClient.patchCamelCaseJsonBodyWithBearerHeader(url,  token, body);
                fail();
            } catch (IllegalArgumentException e) {
                assertEquals(expectedErrorMessage, e.getMessage());
            }
            try {
                httpClient.get(url, Collections.emptyMap(), token);
                fail();
            } catch (IllegalArgumentException e) {
                assertEquals(expectedErrorMessage, e.getMessage());
            }
        }
    }
}
