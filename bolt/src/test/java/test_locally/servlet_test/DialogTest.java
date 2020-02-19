package test_locally.servlet_test;

import com.slack.api.app_backend.dialogs.response.Error;
import com.slack.api.app_backend.dialogs.response.Option;
import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.response.ResponseUrlSender;
import com.slack.api.bolt.servlet.SlackAppServlet;
import com.slack.api.webhook.WebhookResponse;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpTester;
import org.eclipse.jetty.servlet.ServletTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@Slf4j
public class DialogTest {

    private ResponseUrlSender submissionSenderMock = mock(ResponseUrlSender.class);

    {
        try {
            WebhookResponse response = WebhookResponse.builder().code(200).body("ok").build();
            when(submissionSenderMock.send(Mockito.any(ActionResponse.class))).thenReturn(response);
        } catch (IOException e) {
            log.error("Failed to send - {}", e.getMessage(), e);
        }
    }

    private AppConfig appConfig = new AppConfig();
    // For real apps, you should not go with empty middleware
    private App app = new App(appConfig, Collections.emptyList());

    {
        app.dialogSubmission("ok-submission-callback-id", (req, ctx) -> {
            ctx.setResponseUrlSender(submissionSenderMock);
            WebhookResponse resp = ctx.respond(r -> r.text("Thanks!"));
            log.info("respond result - {}", resp);
            return ctx.ack();
        });
        app.dialogSubmission("ng-submission-callback-id", (req, ctx) -> ctx.ack(r -> r.errors(Arrays.asList(
                new Error("email", "must be an email!")
        ))));

        app.dialogSuggestion("dialog-callback-id", (req, ctx) -> {
            return ctx.ack(r -> r.options(Arrays.asList(
                    new Option("l", "v")
            )));
        });
        app.dialogCancellation("dialog-callback-id", (req, ctx) -> {
            ctx.setResponseUrlSender(submissionSenderMock);
            WebhookResponse resp = ctx.respond(r -> r.text("Next time!"));
            log.info("respond result - {}", resp);
            return ctx.ack();
        });
    }

    @Before
    public void setUp() {
        app.start();
    }

    @After
    public void tearDown() {
        app.stop();
    }

    @WebServlet(urlPatterns = "/")
    public static class SlackWebApp extends SlackAppServlet {
        public SlackWebApp(App app) {
            super(app);
        }
    }

    Servlet webApp = new SlackWebApp(app);

    @Test
    public void submission_ok() throws Exception {
        ServletTester tester = TestUtils.getServletTester(webApp);
        HttpTester.Request request = TestUtils.prepareRequest();

        String payload = "{\n" +
                "    \"type\": \"dialog_submission\",\n" +
                "    \"submission\": {\n" +
                "        \"name\": \"Sigourney Dreamweaver\",\n" +
                "        \"email\": \"sigdre@example.com\",\n" +
                "        \"phone\": \"+1 800-555-1212\",\n" +
                "        \"meal\": \"burrito\",\n" +
                "        \"comment\": \"No sour cream please\",\n" +
                "        \"team_channel\": \"C0LFFBKPB\",\n" +
                "        \"who_should_sing\": \"U0MJRG1AL\"\n" +
                "    },\n" +
                "    \"callback_id\": \"ok-submission-callback-id\",\n" +
                "    \"state\": \"vegetarian\",\n" +
                "    \"team\": {\n" +
                "        \"id\": \"T1ABCD2E12\",\n" +
                "        \"domain\": \"coverbands\"\n" +
                "    },\n" +
                "    \"user\": {\n" +
                "        \"id\": \"W12A3BCDEF\",\n" +
                "        \"name\": \"dreamweaver\"\n" +
                "    },\n" +
                "    \"channel\": {\n" +
                "        \"id\": \"C1AB2C3DE\",\n" +
                "        \"name\": \"coverthon-1999\"\n" +
                "    },\n" +
                "    \"action_ts\": \"936893340.702759\",\n" +
                "    \"token\": \"M1AqUUw3FqayAbqNtsGMch72\",\n" +
                "    \"response_url\": \"https://hooks.slack.com/app/T012AB0A1/123456789/JpmK0yzoZDeRiqfeduTBYXWQ\"\n" +
                "}";
        request.setContent("payload=" + URLEncoder.encode(payload, "UTF-8"));

        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));

        assertThat(response.getContent(), is(equalTo("")));
        assertThat(response.getStatus(), is(equalTo(200)));

        verify(submissionSenderMock, times(1)).send(Mockito.any(ActionResponse.class));
    }

    @Test
    public void submission_error() throws Exception {
        ServletTester tester = TestUtils.getServletTester(webApp);
        HttpTester.Request request = TestUtils.prepareRequest();

        String payload = "{\n" +
                "    \"type\": \"dialog_submission\",\n" +
                "    \"submission\": {\n" +
                "        \"name\": \"Sigourney Dreamweaver\",\n" +
                "        \"email\": \"sigdre\",\n" +
                "        \"phone\": \"+1 800-555-1212\",\n" +
                "        \"meal\": \"burrito\",\n" +
                "        \"comment\": \"No sour cream please\",\n" +
                "        \"team_channel\": \"C0LFFBKPB\",\n" +
                "        \"who_should_sing\": \"U0MJRG1AL\"\n" +
                "    },\n" +
                "    \"callback_id\": \"ng-submission-callback-id\",\n" +
                "    \"state\": \"vegetarian\",\n" +
                "    \"team\": {\n" +
                "        \"id\": \"T1ABCD2E12\",\n" +
                "        \"domain\": \"coverbands\"\n" +
                "    },\n" +
                "    \"user\": {\n" +
                "        \"id\": \"W12A3BCDEF\",\n" +
                "        \"name\": \"dreamweaver\"\n" +
                "    },\n" +
                "    \"channel\": {\n" +
                "        \"id\": \"C1AB2C3DE\",\n" +
                "        \"name\": \"coverthon-1999\"\n" +
                "    },\n" +
                "    \"action_ts\": \"936893340.702759\",\n" +
                "    \"token\": \"M1AqUUw3FqayAbqNtsGMch72\",\n" +
                "    \"response_url\": \"https://hooks.slack.com/app/T012AB0A1/123456789/JpmK0yzoZDeRiqfeduTBYXWQ\"\n" +
                "}";
        request.setContent("payload=" + URLEncoder.encode(payload, "UTF-8"));

        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));

        assertThat(response.getContent(), is(equalTo("{\"errors\":[{\"name\":\"email\",\"error\":\"must be an email!\"}]}")));
        assertThat(response.getStatus(), is(equalTo(200)));
    }

}
