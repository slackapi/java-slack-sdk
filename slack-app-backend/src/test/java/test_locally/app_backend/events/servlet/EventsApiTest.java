package test_locally.app_backend.events.servlet;

import com.slack.api.app_backend.events.EventsDispatcher;
import com.slack.api.app_backend.events.handler.AppUninstalledHandler;
import com.slack.api.app_backend.events.handler.GoodbyeHandler;
import com.slack.api.app_backend.events.handler.MessageHandler;
import com.slack.api.app_backend.events.payload.AppUninstalledPayload;
import com.slack.api.app_backend.events.payload.GoodbyePayload;
import com.slack.api.app_backend.events.payload.MessagePayload;
import com.slack.api.app_backend.events.payload.UrlVerificationPayload;
import com.slack.api.app_backend.events.servlet.SlackEventsApiServlet;
import com.slack.api.util.json.GsonFactory;
import org.eclipse.jetty.http.HttpTester;
import org.eclipse.jetty.servlet.ServletTester;
import org.junit.Before;
import org.junit.Test;
import util.ServletTestUtils;

import javax.servlet.annotation.WebServlet;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class EventsApiTest {

    private static final String signingSecret = "secret";

    public static AtomicInteger messageCalls = new AtomicInteger(0);
    public static MessageHandler message = new MessageHandler() {
        @Override
        public void handle(MessagePayload payload) {
            messageCalls.incrementAndGet();
        }
    };

    public static AtomicInteger appUninstallsCalls = new AtomicInteger(0);
    public static AppUninstalledHandler allUninstall = new AppUninstalledHandler() {
        @Override
        public void handle(AppUninstalledPayload event) {
            appUninstallsCalls.incrementAndGet();
        }
    };

    public static AtomicInteger goodbyeCalls = new AtomicInteger(0);
    public static GoodbyeHandler goodbye = new GoodbyeHandler() {
        @Override
        public void handle(GoodbyePayload payload) {
            goodbyeCalls.incrementAndGet();
        }
    };

    @Before
    public void setup() {
        messageCalls.set(0);
        appUninstallsCalls.set(0);
        goodbyeCalls.set(0);
    }

    SlackWebApp webApp = new SlackWebApp();

    @WebServlet(urlPatterns = "/")
    public static class SlackWebApp extends SlackEventsApiServlet {

        @Override
        protected String getSlackSigningSecret() {
            return signingSecret;
        }

        @Override
        protected void setupDispatcher(EventsDispatcher dispatcher) {
            assertNotNull(getSlackSigningSecret());
            dispatcher.register(message);
            dispatcher.register(allUninstall);
            dispatcher.register(goodbye);
        }
    }

    @Test
    public void destroy() {
        SlackWebApp app = new SlackWebApp();
        app.destroy();
    }

    @Test
    public void urlVerification() throws Exception {
        ServletTester tester = ServletTestUtils.getServletTester(webApp);

        UrlVerificationPayload payload = new UrlVerificationPayload();
        payload.setChallenge("cha-xxxxxx");
        payload.setToken("token-xxxx");

        HttpTester.Request request = ServletTestUtils.prepareRequest(signingSecret, GsonFactory.createSnakeCase().toJson(payload));
        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));

        assertThat(response.getContent(), is(equalTo("cha-xxxxxx")));
        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(response.get("Content-Type"), is(startsWith("text/plain")));
    }

    @Test
    public void message() throws Exception {
        ServletTester tester = ServletTestUtils.getServletTester(webApp);
        HttpTester.Request request = ServletTestUtils.prepareRequest(signingSecret, "{\n" +
                "    \"token\": \"XXYYZZ\",\n" +
                "    \"team_id\": \"TXXXXXXXX\",\n" +
                "    \"api_app_id\": \"AXXXXXXXXX\",\n" +
                "    \"event\": {\n" +
                "        \"type\": \"message\",\n" +
                "        \"channel\": \"C2147483705\",\n" +
                "        \"user\": \"U2147483697\",\n" +
                "        \"text\": \"Hello world\",\n" +
                "        \"ts\": \"1355517523.000005\"\n" +
                "}   ,\n" +
                "    \"type\": \"event_callback\",\n" +
                "    \"event_id\": \"EvXXXXXXXX\",\n" +
                "    \"event_time\": 1234567890\n" +
                "}");

        HttpTester.Response response1 = HttpTester.parseResponse(tester.getResponses(request.generate()));
        HttpTester.Response response2 = HttpTester.parseResponse(tester.getResponses(request.generate()));
        HttpTester.Response response3 = HttpTester.parseResponse(tester.getResponses(request.generate()));

        // wait for the async execution
        Thread.sleep(200L);

        assertThat(response1.getStatus(), is(equalTo(200)));
        assertThat(response2.getStatus(), is(equalTo(200)));
        assertThat(response3.getStatus(), is(equalTo(200)));
        assertThat(messageCalls.get(), is(3));
    }

    @Test
    public void app_uninstalled() throws Exception {
        ServletTester tester = ServletTestUtils.getServletTester(webApp);
        HttpTester.Request request = ServletTestUtils.prepareRequest(signingSecret, "{\n" +
                "    \"token\": \"XXYYZZ\",\n" +
                "    \"team_id\": \"TXXXXXXXX\",\n" +
                "    \"api_app_id\": \"AXXXXXXXXX\",\n" +
                "    \"event\": {\n" +
                "        \"type\": \"app_uninstalled\"\n" +
                "    },\n" +
                "    \"type\": \"event_callback\",\n" +
                "    \"event_id\": \"EvXXXXXXXX\",\n" +
                "    \"event_time\": 1234567890\n" +
                "}");

        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));

        // wait for the async execution
        Thread.sleep(200L);

        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(appUninstallsCalls.get(), is(1));
    }

    @Test
    public void goodbye() throws Exception {
        ServletTester tester = ServletTestUtils.getServletTester(webApp);
        HttpTester.Request request = ServletTestUtils.prepareRequest(signingSecret, "{\n" +
                "    \"token\": \"XXYYZZ\",\n" +
                "    \"team_id\": \"TXXXXXXXX\",\n" +
                "    \"api_app_id\": \"AXXXXXXXXX\",\n" +
                "    \"event\": {\n" +
                "        \"type\": \"goodbye\"\n" +
                "    },\n" +
                "    \"type\": \"event_callback\",\n" +
                "    \"event_id\": \"EvXXXXXXXX\",\n" +
                "    \"event_time\": 1234567890\n" +
                "}");

        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));

        // wait for the async execution
        Thread.sleep(200L);

        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(goodbyeCalls.get(), is(1));
    }

    @Test
    public void invalid() throws Exception {
        ServletTester tester = ServletTestUtils.getServletTester(webApp);
        HttpTester.Request request = ServletTestUtils.prepareRequest("invalid", "{\n" +
                "    \"token\": \"XXYYZZ\",\n" +
                "    \"team_id\": \"TXXXXXXXX\",\n" +
                "    \"api_app_id\": \"AXXXXXXXXX\",\n" +
                "    \"event\": {\n" +
                "        \"type\": \"goodbye\"\n" +
                "    },\n" +
                "    \"type\": \"event_callback\",\n" +
                "    \"event_id\": \"EvXXXXXXXX\",\n" +
                "    \"event_time\": 1234567890\n" +
                "}");

        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));

        // wait for the async execution
        Thread.sleep(200L);

        assertThat(response.getStatus(), is(equalTo(401)));
        assertThat(goodbyeCalls.get(), is(0));
    }
}
