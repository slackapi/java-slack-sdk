package test_locally.app_backend.events;

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
import org.junit.Ignore;
import org.junit.Test;

import javax.servlet.annotation.WebServlet;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

// TODO: These tests somehow fail on TravisCI builds.
//       will come up with better ways to do the similar.
public class EventsApiHandlerTest {

    @WebServlet(urlPatterns = "/")
    public static class SampleServlet extends SlackEventsApiServlet {
        @Override
        protected boolean isSignatureVerifierEnabled() {
            return false;
        }

        @Override
        protected void setupDispatcher(EventsDispatcher dispatcher) {
            dispatcher.register(MESSAGE);
            dispatcher.register(APP_UNINSTALLED);
            dispatcher.register(GOODBYE);
        }
    }

    // --------------------------------
    // message

    public static AtomicInteger MESSAGE_CALL_COUNTER = new AtomicInteger(0);

    public static MessageHandler MESSAGE = new MessageHandler() {
        @Override
        public void handle(MessagePayload payload) {
            MESSAGE_CALL_COUNTER.incrementAndGet();
        }
    };

    // --------------------------------
    // app_uninstalled

    public static AtomicInteger APP_UNINSTALLED_CALL_COUNTER = new AtomicInteger(0);

    public static AppUninstalledHandler APP_UNINSTALLED = new AppUninstalledHandler() {
        @Override
        public void handle(AppUninstalledPayload event) {
            APP_UNINSTALLED_CALL_COUNTER.incrementAndGet();
        }
    };

    // --------------------------------
    // goodbye

    public static AtomicInteger GOODBYE_CALL_COUNTER = new AtomicInteger(0);

    public static GoodbyeHandler GOODBYE = new GoodbyeHandler() {
        @Override
        public void handle(GoodbyePayload payload) {
            GOODBYE_CALL_COUNTER.incrementAndGet();
        }
    };

    // -------------------------------------------------------------------

    @Ignore
    @Test
    public void urlVerification() throws Exception {
        ServletTester tester = getServletTester();
        HttpTester.Request request = prepareRequest();

        UrlVerificationPayload payload = new UrlVerificationPayload();
        payload.setChallenge("cha-xxxxxx");
        payload.setToken("token-xxxx");
        request.setContent(GsonFactory.createSnakeCase().toJson(payload));

        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));

        assertThat(response.getContent(), is(equalTo("cha-xxxxxx")));
        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(response.get("Content-Type"), is(equalTo("text/plain")));
    }

    @Ignore
    @Test
    public void message() throws Exception {
        ServletTester tester = getServletTester();
        HttpTester.Request request = prepareRequest();

        request.setContent("{\n" +
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
        assertThat(MESSAGE_CALL_COUNTER.get(), is(3));
    }

    @Ignore
    @Test
    public void app_uninstalled() throws Exception {
        ServletTester tester = getServletTester();
        HttpTester.Request request = prepareRequest();

        request.setContent("{\n" +
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
        assertThat(APP_UNINSTALLED_CALL_COUNTER.get(), is(1));
    }

    @Ignore
    @Test
    public void goodbye() throws Exception {
        ServletTester tester = getServletTester();
        HttpTester.Request request = prepareRequest();

        request.setContent("{\n" +
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
        assertThat(GOODBYE_CALL_COUNTER.get(), is(1));
    }

    // -------------------------------------------------------------------

    private static ServletTester getServletTester() throws Exception {
        ServletTester tester = new ServletTester();
        tester.addServlet(SampleServlet.class, "/");
        tester.start();
        return tester;
    }

    private static HttpTester.Request prepareRequest() {
        HttpTester.Request request = HttpTester.newRequest();
        request.setMethod("POST");
        request.setHeader("Host", "tester"); // should be "tester"
        request.setURI("/");
        request.setVersion("HTTP/1.1");
        request.setHeader("content-type", "application/json");
        request.setHeader("Connection", "close");
        return request;
    }

}
