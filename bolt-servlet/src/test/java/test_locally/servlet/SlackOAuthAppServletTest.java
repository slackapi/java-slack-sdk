package test_locally.servlet;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.service.OAuthStateService;
import com.slack.api.bolt.servlet.SlackOAuthAppServlet;
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

public class SlackOAuthAppServletTest {

    @Test
    public void instantiation() {
        App app = new App(AppConfig.builder().singleTeamBotToken("xoxb-xxx").signingSecret("secret").build());
        SlackOAuthAppServlet servlet = new SlackOAuthAppServlet(app);
        assertEquals(app, servlet.getApp());
    }

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
    public void oauthStart() throws ServletException, IOException {
        AppConfig config = AppConfig.builder()
                .signingSecret("secret")
                .clientId("123.123")
                .clientSecret("secret")
                .oauthStartPath("/start")
                .build();
        App app = new App(config, Collections.emptyList()).asOAuthApp(true); // bypass the built-in middleware
        SlackOAuthAppServlet servlet = new SlackOAuthAppServlet(app);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getMethod()).thenReturn("GET");
        when(req.getRequestURI()).thenReturn("/start");
        ServletInputStream body = new SimpleServletInputStream("");
        when(req.getInputStream()).thenReturn(body);
        when(req.getReader()).thenReturn(new BufferedReader(new InputStreamReader(body)));
        when(req.getHeaderNames()).thenReturn(Collections.emptyEnumeration());

        HttpServletResponse resp = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(writer);

        servlet.service(req, resp);

        verify(resp, times(1)).setStatus(200);
        verify(resp, times(1)).setHeader("Content-Type","text/html; charset=utf-8");
    }

    @Test
    public void oauthStart_error() throws ServletException, IOException {
        AppConfig config = AppConfig.builder()
                .signingSecret("secret")
                .clientId("123.123")
                .clientSecret("secret")
                .oauthStartPath("/start")
                .build();
        App app = new App(config, Collections.emptyList()).asOAuthApp(true); // bypass the built-in middleware
        app.service(new OAuthStateService() {
            @Override
            public void addNewStateToDatastore(String state) throws Exception {
                throw new Exception("Something is wrong!");
            }

            @Override
            public boolean isAvailableInDatabase(String state) {
                return false;
            }

            @Override
            public void deleteStateFromDatastore(String state) {
            }
        });
        SlackOAuthAppServlet servlet = new SlackOAuthAppServlet(app);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getMethod()).thenReturn("GET");
        when(req.getRequestURI()).thenReturn("/start");
        ServletInputStream body = new SimpleServletInputStream("");
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
