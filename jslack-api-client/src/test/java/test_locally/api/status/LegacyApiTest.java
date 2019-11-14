package test_locally.api.status;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.SlackConfig;
import com.github.seratch.jslack.api.status.v1.model.LegacyCurrentStatus;
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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class LegacyApiTest {

    @WebServlet
    public static class SampleServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            if (!req.getRequestURI().equals("/api/current")) {
                resp.setStatus(400);
                return;
            }
            resp.setStatus(200);
            resp.getWriter().write("{}");
            resp.setContentType("application/json");
        }
    }

    int port = PortProvider.getPort(LegacyApiTest.class.getName());
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
    public void current() throws Exception {
        SlackConfig config = new SlackConfig();
        config.setLegacyStatusEndpointUrlPrefix("http://localhost:" + port + "/api/");

        LegacyCurrentStatus response = Slack.getInstance(config).statusLegacy().current();
        assertThat(response, is(notNullValue()));
    }
}