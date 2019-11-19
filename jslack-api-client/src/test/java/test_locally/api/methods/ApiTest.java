package test_locally.api.methods;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.SlackConfig;
import com.github.seratch.jslack.api.methods.response.api.ApiTestResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test_locally.api.util.PortProvider;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApiTest {

    @WebServlet
    public static class SampleServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            if (!req.getRequestURI().equals("/api/api.test")) {
                resp.setStatus(400);
                return;
            }
            resp.setStatus(200);
            resp.getWriter().write("{\"ok\":true, \"args\":{\"foo\":\"" + req.getParameter("foo") + "\"}}");
            resp.setContentType("application/json");
        }
    }

    int port = PortProvider.getPort(ApiTest.class.getName());
    Server server = new Server(port);

    {
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(SampleServlet.class, "/*");
    }

    @Before
    public void setup() throws Exception {
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void apiTest() throws Exception {
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix("http://localhost:" + port + "/api/");

        ApiTestResponse response = Slack.getInstance(config).methods().apiTest(r -> r.foo("bar"));
        assertThat(response.isOk(), is(true));
        assertThat(response.getArgs().getFoo(), is("bar"));
    }
}
