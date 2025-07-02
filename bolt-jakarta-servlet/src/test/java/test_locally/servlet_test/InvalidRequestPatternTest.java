package test_locally.servlet_test;

import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.jakarta_servlet.SlackAppServlet;
import com.slack.api.bolt.middleware.builtin.RequestVerification;
import jakarta.servlet.Servlet;
import jakarta.servlet.annotation.WebServlet;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.*;
import org.eclipse.jetty.servlet.ServletTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class InvalidRequestPatternTest {

    private final String secret = "foo-bar-baz";
    private final SlackSignature.Generator generator = new SlackSignature.Generator(secret);
    private AppConfig appConfig = AppConfig.builder().singleTeamBotToken("xoxb-").signingSecret(secret).build();
    private App app;
    private Servlet webApp;
    private static final String TEXT_PLAIN = MimeTypes.Type.TEXT_PLAIN.getContentTypeField().getValue();
    private static final String APPLICATION_JSON = MimeTypes.Type.APPLICATION_JSON.getContentTypeField().getValue();
    private static final String CONTENT_TYPE = HttpHeader.CONTENT_TYPE.toString();

    @Before
    public void setUp() {
        // https://docs.slack.dev/authentication/verifying-requests-from-slack
        String signingSecret = appConfig.getSigningSecret();
        if (signingSecret == null || signingSecret.trim().isEmpty()) {
            // This is just a random value to avoid SlackSignature.Generator's initialization error.
            // When this App runs through Socket Mode connections, it skips request signature verification.
            signingSecret = "---";
        }
        SlackSignature.Verifier verifier = new SlackSignature.Verifier(new SlackSignature.Generator(signingSecret));
        RequestVerification requestVerification = new RequestVerification(verifier);
        app = new App(appConfig, Arrays.asList(
                requestVerification
        ));
        webApp = new SlackWebApp(app);
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

    @Test
    public void valid() throws Exception {
        ServletTester tester = TestUtils.getServletTester(webApp);
        HttpTester.Request request = TestUtils.prepareRequest();

        String requestBody = "{\n" +
                "  \"token\": \"fixed-value\",\n" +
                "  \"challenge\": \"challenge-value\",\n" +
                "  \"type\": \"url_verification\"\n" +
                "}";
        request.setContent(requestBody);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        request.setHeader(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp);
        request.setHeader(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, generator.generate(timestamp, requestBody));
        request.setHeader(CONTENT_TYPE, APPLICATION_JSON);

        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));

        assertThat(response.getStatus(), is(equalTo(HttpStatus.OK_200)));
        assertThat(response.getContent(), is(equalTo("challenge-value")));
        assertThat(response.get(CONTENT_TYPE), is(startsWith(TEXT_PLAIN)));
    }

    @Test
    public void nonsense() throws Exception {
        ServletTester tester = TestUtils.getServletTester(webApp);
        HttpTester.Request request = TestUtils.prepareRequest();

        String requestBody = "{\n" +
                "  \"token\": \"fixed-value\",\n" +
                "  \"challenge\": \"challenge-value\",\n" +
                "  \"type\": \"nonsense\"\n" +
                "}";
        request.setContent(requestBody);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        request.setHeader(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp);
        request.setHeader(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, generator.generate(timestamp, requestBody));
        request.setHeader(CONTENT_TYPE, APPLICATION_JSON);

        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));

        assertThat(response.getStatus(), is(equalTo(HttpStatus.BAD_REQUEST_400)));
        assertThat(response.getContent(), is(equalTo("Invalid Request")));
        assertThat(response.get(CONTENT_TYPE), is(startsWith(TEXT_PLAIN)));
    }

    @Test
    public void invalidSignature() throws Exception {
        ServletTester tester = TestUtils.getServletTester(webApp);
        HttpTester.Request request = TestUtils.prepareRequest();

        String requestBody = "{\n" +
                "  \"token\": \"fixed-value\",\n" +
                "  \"challenge\": \"challenge-value\",\n" +
                "  \"type\": \"url_verification\"\n" +
                "}";
        request.setContent(requestBody);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        request.setHeader(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp);
        SlackSignature.Generator differentGenerator = new SlackSignature.Generator("a different app's secret");
        request.setHeader(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, differentGenerator.generate(timestamp, requestBody));
        request.setHeader(CONTENT_TYPE, APPLICATION_JSON);

        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));

        assertThat(response.getStatus(), is(equalTo(HttpStatus.UNAUTHORIZED_401)));
        assertThat(response.getContent(), is(equalTo("{\"error\":\"invalid request\"}")));
        assertThat(response.get(CONTENT_TYPE), is(startsWith(APPLICATION_JSON)));
    }
}
