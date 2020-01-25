package test_locally;

import com.slack.api.Slack;
import com.slack.api.RequestConfigurator;
import com.github.seratch.jslack.api.methods.MethodsClient;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.response.auth.AuthTestResponse;
import com.github.seratch.jslack.app_backend.SlackSignature;
import com.github.seratch.jslack.lightning.AppConfig;
import io.micronaut.context.annotation.Primary;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.IOException;

import static org.mockito.Mockito.*;

@MicronautTest
public class CommandsTest {

    // ------------------------------------------------------------------------------------------

    String helloBody = "token=legacy&" +
            "team_id=T12345678&" +
            "team_domain=workspace&" +
            "channel_id=C12345678&" +
            "channel_name=dev&" +
            "user_id=U12345678&" +
            "user_name=seratch&" +
            "command=%2Fhello&" +
            "text=something&" +
            "response_url=https%3A%2F%2Fhooks.slack.com%2Fcommands%2FT12345678%2F123456789012%2Frandom-value&" +
            "trigger_id=123.456.abc";

    @Inject
    @Client("/")
    RxHttpClient client;

    String signingSecret = "test";
    String botToken = "xoxb-dummy";
    SlackSignature.Generator signatureGenerator = new SlackSignature.Generator(signingSecret);

    @Primary
    @MockBean(AppConfig.class)
    AppConfig mockSlackAppConfig() throws IOException, SlackApiException {
        AppConfig config = AppConfig.builder().signingSecret(signingSecret).singleTeamBotToken(botToken).build();
        config.setSlack(mockSlack());
        return config;
    }

    @MockBean(Slack.class)
    Slack mockSlack() throws IOException, SlackApiException {
        Slack slack = mock(Slack.class);
        MethodsClient methods = mock(MethodsClient.class);
        when(slack.methods(botToken)).thenReturn(methods);
        AuthTestResponse authTestResponse = new AuthTestResponse();
        authTestResponse.setOk(true);
        when(methods.authTest(any(RequestConfigurator.class))).thenReturn(authTestResponse);
        return slack;
    }

    @Test
    public void command() {
        MutableHttpRequest<String> request = HttpRequest.POST("/slack/events", "");
        request.header("Content-Type", "application/x-www-form-urlencoded");
        String timestamp = "" + (System.currentTimeMillis() / 1000);
        request.header(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp);
        String signature = signatureGenerator.generate(timestamp, helloBody);
        request.header(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, signature);
        request.body(helloBody);
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);
        Assertions.assertEquals(200, response.getStatus().getCode());
        Assertions.assertEquals("{\"text\":\"Thanks!\"}", response.getBody().get());
    }

    @Test
    public void invalidSignature() {
        MutableHttpRequest<String> request = HttpRequest.POST("/slack/events", "");
        request.header("Content-Type", "application/x-www-form-urlencoded");
        String timestamp = "" + (System.currentTimeMillis() / 1000 - 30 * 60);
        request.header(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp);
        String signature = signatureGenerator.generate(timestamp, helloBody);
        request.header(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, signature);
        request.body(helloBody);
        try {
            client.toBlocking().exchange(request, String.class);
            Assertions.fail();
        } catch (HttpClientResponseException e) {
            Assertions.assertEquals(401, e.getStatus().getCode());
            Assertions.assertEquals("{\"error\":\"invalid request\"}", e.getResponse().getBody().get());
        }
    }

    // ------------------------------------------------------------------------------------------

    String submissionBody = "token=legacy&" +
            "team_id=T12345678&" +
            "team_domain=workspace&" +
            "channel_id=C12345678&" +
            "channel_name=dev&" +
            "user_id=U12345678&" +
            "user_name=seratch&" +
            "command=%2Fsubmission-no.2019&" +
            "text=something&" +
            "response_url=https%3A%2F%2Fhooks.slack.com%2Fcommands%2FT12345678%2F123456789012%2Frandom-value&" +
            "trigger_id=123.456.abc";

    @Test
    public void regexp_matching() {
        MutableHttpRequest<String> request = HttpRequest.POST("/slack/events", "");
        request.header("Content-Type", "application/x-www-form-urlencoded");
        String timestamp = "" + (System.currentTimeMillis() / 1000);
        request.header(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp);
        String signature = signatureGenerator.generate(timestamp, submissionBody);
        request.header(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, signature);
        request.body(submissionBody);
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);
        Assertions.assertEquals(200, response.getStatus().getCode());
        Assertions.assertEquals("{\"text\":\"/submission-no.2019\"}", response.getBody().get());
    }

}
