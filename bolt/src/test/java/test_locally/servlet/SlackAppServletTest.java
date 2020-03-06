package test_locally.servlet;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.servlet.SlackAppServlet;
import org.junit.Test;

import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SlackAppServletTest {

    @Test
    public void instantiation() {
        App app = new App(AppConfig.builder().singleTeamBotToken("xoxb-xxx").signingSecret("secret").build());
        SlackAppServlet servlet = new SlackAppServlet(app);
        assertEquals(app, servlet.getApp());
    }

    String slashCommandPayload = "token=gIkuvaNzQIHg97ATvDxqgjtO" +
            "&team_id=T0001" +
            "&team_domain=example" +
            "&enterprise_id=E0001" +
            "&enterprise_name=Globular%20Construct%20Inc" +
            "&channel_id=C2147483705" +
            "&channel_name=test" +
            "&user_id=U2147483697" +
            "&user_name=Steve" +
            "&command=/weather" +
            "&text=94070" +
            "&response_url=https://hooks.slack.com/commands/1234/5678" +
            "&trigger_id=13345224609.738474920.8088930838d88f008e0";

    static class SimpleServletInputStream extends ServletInputStream {
        final ByteArrayInputStream body;

        SimpleServletInputStream(String body) {
            this.body = new ByteArrayInputStream(body.getBytes());
        }

        @Override
        public boolean isFinished() {
            return body.available() == 0;
        }

        @Override
        public boolean isReady() {
            return body.available() > 0;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
        }

        @Override
        public int read() {
            return body.read();
        }
    }

    @Test
    public void errorHandler() throws ServletException, IOException {
        AppConfig config = AppConfig.builder().singleTeamBotToken("xoxb-xxx").signingSecret("secret").build();
        App app = new App(config, Collections.emptyList()); // bypass the built-in middleware
        app.command("/weather", (req, ctx) -> {
            throw new RuntimeException("Something is wrong!");
        });
        SlackAppServlet servlet = new SlackAppServlet(app);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getMethod()).thenReturn("POST");
        ServletInputStream body = new SimpleServletInputStream(slashCommandPayload);
        when(req.getInputStream()).thenReturn(body);
        when(req.getReader()).thenReturn(new BufferedReader(new InputStreamReader(body)));
        when(req.getHeaderNames()).thenReturn(Collections.emptyEnumeration());

        HttpServletResponse resp = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(writer);

        servlet.service(req, resp);

        verify(resp, times(1)).setStatus(500);
    }
}
