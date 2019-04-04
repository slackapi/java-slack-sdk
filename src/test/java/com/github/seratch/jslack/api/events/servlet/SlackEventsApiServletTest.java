package com.github.seratch.jslack.api.events.servlet;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.EventsDispatcher;
import com.github.seratch.jslack.api.events.payload.AppUninstalledPayload;
import com.github.seratch.jslack.api.events.payload.UrlVerificationPayload;
import com.github.seratch.jslack.api.model.event.AppUninstalledEvent;
import com.github.seratch.jslack.common.json.GsonFactory;
import org.eclipse.jetty.http.HttpTester;
import org.eclipse.jetty.servlet.ServletTester;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SlackEventsApiServletTest {

    public static AtomicInteger counter = new AtomicInteger(0);

    public static EventHandler<AppUninstalledPayload> appUninstalledEventHandler = new EventHandler<AppUninstalledPayload>() {

        @Override
        public String getEventType() {
            return AppUninstalledEvent.TYPE_NAME;
        }

        @Override
        public void handle(AppUninstalledPayload event) {
            counter.incrementAndGet();
        }
    };

    public static class SampleServlet extends SlackEventsApiServlet {

        @Override
        protected void setupDispatcher(EventsDispatcher dispatcher) {
            dispatcher.register(appUninstalledEventHandler);
        }
    }

    @Test
    public void urlVerificationRequest() throws Exception {
        ServletTester tester = new ServletTester();
        tester.addServlet(SampleServlet.class, "/");
        tester.start();

        HttpTester.Request request = HttpTester.newRequest();
        request.setMethod("POST");
        request.setHeader("Host", "tester"); // should be "tester"
        request.setURI("/");
        request.setVersion("HTTP/1.1");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Connection", "close");

        UrlVerificationPayload payload = new UrlVerificationPayload();
        payload.setChallenge("cha-xxxxxx");
        payload.setToken("token-xxxx");
        request.setContent(GsonFactory.createSnakeCase().toJson(payload));

        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));

        assertThat(response.getContent(), is(equalTo("cha-xxxxxx")));
        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(response.get("Content-Type"), is(equalTo("text/plain")));
    }

    @Test
    public void app_uninstalled() throws Exception {
        ServletTester tester = new ServletTester();
        tester.addServlet(SampleServlet.class, "/");
        tester.start();

        HttpTester.Request request = HttpTester.newRequest();
        request.setMethod("POST");
        request.setHeader("Host", "tester"); // should be "tester"
        request.setURI("/");
        request.setVersion("HTTP/1.1");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Connection", "close");

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
        Thread.sleep(1000L);

        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(counter.get(), is(1));
    }

}