package test_locally.servlet_test;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.WebEndpoint;
import com.slack.api.bolt.handler.WebEndpointHandler;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.servlet.WebEndpointServlet;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpTester;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class EndpointTest {

    private AppConfig appConfig = new AppConfig();
    // For real apps, you should not go with empty middleware
    private App app = new App(appConfig, Collections.emptyList());

    {
        app.endpoint("GET", "/hello-world", (req, ctx) -> {
            Map<String, String> body = new HashMap<>();
            body.put("message", "Hello World!");
            body.put("query", req.getQueryString());
            return Response.json(200, body);
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

    @Test
    public void helloWorld() throws Exception {
        ServletTester tester = new ServletTester();
        Map.Entry<WebEndpoint, WebEndpointHandler> e = app.getWebEndpointHandlers().entrySet().iterator().next();
        WebEndpointServlet app = new WebEndpointServlet(e.getKey(), e.getValue(), appConfig);
        tester.addServlet(new ServletHolder(app), "/");
        tester.start();

        HttpTester.Request request = TestUtils.prepareRequest();
        request.setURI("/hello-world");
        request.setMethod("GET");
        request.setContent("dummy");

        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));

        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(response.getContent(), is(equalTo("{\"message\":\"Hello World!\"}")));
    }

    @Test
    public void helloWorld_with_query_string() throws Exception {
        ServletTester tester = new ServletTester();
        Map.Entry<WebEndpoint, WebEndpointHandler> e = app.getWebEndpointHandlers().entrySet().iterator().next();
        WebEndpointServlet app = new WebEndpointServlet(e.getKey(), e.getValue(), appConfig);
        tester.addServlet(new ServletHolder(app), "/");
        tester.start();

        HttpTester.Request request = TestUtils.prepareRequest();
        request.setURI("/hello-world?foo=bar&baz=var");
        request.setMethod("GET");
        request.setContent("dummy");

        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));

        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(response.getContent(),
                is(equalTo("{\"query\":\"foo\\u003dbar\\u0026baz\\u003dvar\",\"message\":\"Hello World!\"}")));
    }
}
