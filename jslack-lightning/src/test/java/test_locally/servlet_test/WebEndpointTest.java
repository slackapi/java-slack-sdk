package test_locally.servlet_test;

import com.github.seratch.jslack.lightning.App;
import com.github.seratch.jslack.lightning.AppConfig;
import com.github.seratch.jslack.lightning.WebEndpoint;
import com.github.seratch.jslack.lightning.handler.WebEndpointHandler;
import com.github.seratch.jslack.lightning.response.Response;
import com.github.seratch.jslack.lightning.servlet.WebEndpointServlet;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpTester;
import org.eclipse.jetty.servlet.ServletTester;
import org.junit.Test;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebServlet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class WebEndpointTest {

    private static AppConfig appConfig = new AppConfig();
    private static App app = new App(appConfig);

    static WebEndpoint foo = new WebEndpoint(WebEndpoint.Method.GET, "/foo");
    static WebEndpoint bar = new WebEndpoint(WebEndpoint.Method.POST, "/bar");
    static WebEndpointHandler handler = (req, ctx) -> Response.ok("Thanks!");

    @WebServlet
    public static class FooApp extends WebEndpointServlet {
        public FooApp() {
            super(foo, handler, app.config());
        }
    }

    @WebServlet
    public static class BarApp extends WebEndpointServlet {
        public BarApp() {
            super(bar, handler, app.config());
        }
    }

    static Servlet fooApp = new FooApp();
    static Servlet barApp = new BarApp();

    @Test
    public void foo_GET() throws Exception {
        ServletTester tester = TestUtils.getServletTester(fooApp);
        HttpTester.Request request = buildRequest("GET", "/foo");
        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));
        assertThat(response.getContent(), is(equalTo("Thanks!")));
        assertThat(response.getStatus(), is(equalTo(200)));
    }

    @Test
    public void foo_POST() throws Exception {
        ServletTester tester = TestUtils.getServletTester(fooApp);
        HttpTester.Request request = buildRequest("POST", "/foo");
        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));
        assertThat(response.getContent(), is(equalTo("{\"error\":\"Not found\"}")));
        assertThat(response.getStatus(), is(equalTo(404)));
    }

    @Test
    public void bar_GET() throws Exception {
        ServletTester tester = TestUtils.getServletTester(barApp);
        HttpTester.Request request = buildRequest("GET", "/bar");
        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));
        assertThat(response.getContent(), is(equalTo("{\"error\":\"Not found\"}")));
        assertThat(response.getStatus(), is(equalTo(404)));
    }

    @Test
    public void bar_POST() throws Exception {
        ServletTester tester = TestUtils.getServletTester(barApp);
        HttpTester.Request request = buildRequest("POST", "/bar");
        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));
        assertThat(response.getContent(), is(equalTo("Thanks!")));
        assertThat(response.getStatus(), is(equalTo(200)));
    }

    @Test
    public void foo_towards_bar() throws Exception {
        ServletTester tester = TestUtils.getServletTester(barApp);
        HttpTester.Request request = buildRequest("POST", "/foo");
        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));
        assertThat(response.getContent(), is(equalTo("{\"error\":\"Not found\"}")));
        assertThat(response.getStatus(), is(equalTo(404)));
    }

    private HttpTester.Request buildRequest(String method, String uri) {
        HttpTester.Request request = HttpTester.newRequest();
        request.setMethod(method);
        request.setHeader("Host", "tester"); // must be "tester"
        request.setURI(uri);
        request.setVersion("HTTP/1.1");
        request.setHeader("Connection", "close");
        return request;
    }
}

