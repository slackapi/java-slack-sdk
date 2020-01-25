package test_locally.servlet_test;

import com.slack.api.webhook.WebhookResponse;
import com.github.seratch.jslack.app_backend.slash_commands.response.SlashCommandResponse;
import com.github.seratch.jslack.lightning.App;
import com.github.seratch.jslack.lightning.AppConfig;
import com.github.seratch.jslack.lightning.response.ResponseUrlSender;
import com.github.seratch.jslack.lightning.servlet.SlackAppServlet;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpTester;
import org.eclipse.jetty.servlet.ServletTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@Slf4j
public class SlashCommandTest {

    private AppConfig appConfig = new AppConfig();
    // For real apps, you should not go with empty middleware
    private App app = new App(appConfig, Collections.emptyList());

    private AtomicInteger weatherCalls = new AtomicInteger(0);
    private AtomicInteger echoCalls = new AtomicInteger(0);

    private ResponseUrlSender echoSenderMock = mock(ResponseUrlSender.class);

    {
        try {
            WebhookResponse response = WebhookResponse.builder().code(200).body("ok").build();
            when(echoSenderMock.send(Mockito.any(SlashCommandResponse.class))).thenReturn(response);
        } catch (IOException e) {
            log.error("Failed to send - {}", e.getMessage(), e);
        }
    }

    {
        app.command("/weather", (req, ctx) -> {
            weatherCalls.incrementAndGet();
            return ctx.ack(r -> r.text("It's rainy in the area: " + req.getPayload().getText()));
        });

        app.command("/echo", (req, ctx) -> {
            echoCalls.incrementAndGet();
            ctx.setResponseUrlSender(echoSenderMock);
            String message = "You said \"" + req.getPayload().getText() + "\" at " + req.getPayload().getChannelName();
            WebhookResponse respondResult = ctx.respond(r -> r.text(message));
            log.info("respond result - {}", respondResult);

            return ctx.ack();
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

    @WebServlet(urlPatterns = "/")
    public static class SlackWebApp extends SlackAppServlet {
        public SlackWebApp(App app) {
            super(app);
        }
    }

    Servlet webApp = new SlackWebApp(app);

    @Test
    public void weather() throws Exception {
        ServletTester tester = TestUtils.getServletTester(webApp);
        HttpTester.Request request = TestUtils.prepareRequest();

        // https://api.slack.com/slash-commands
        request.setContent("token=gIkuvaNzQIHg97ATvDxqgjtO" +
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
                "&trigger_id=13345224609.738474920.8088930838d88f008e0");

        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));

        assertThat(response.getContent(), is(equalTo("{\"text\":\"It\\u0027s rainy in the area: 94070\"}")));
        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(response.get("Content-Type"), is(startsWith("application/json")));
        assertThat(weatherCalls.get(), is(1));
    }

    @Test
    public void echo() throws Exception {
        ServletTester tester = TestUtils.getServletTester(webApp);
        HttpTester.Request request = TestUtils.prepareRequest();

        // https://api.slack.com/slash-commands
        request.setContent("token=gIkuvaNzQIHg97ATvDxqgjtO" +
                "&team_id=T0001" +
                "&team_domain=example" +
                "&enterprise_id=E0001" +
                "&enterprise_name=Globular%20Construct%20Inc" +
                "&channel_id=C2147483705" +
                "&channel_name=test" +
                "&user_id=U2147483697" +
                "&user_name=Steve" +
                "&command=/echo" +
                "&text=Can I ask you a favor?" +
                "&response_url=https://hooks.slack.com/commands/1234/5678" +
                "&trigger_id=13345224609.738474920.8088930838d88f008e0");

        HttpTester.Response response = HttpTester.parseResponse(tester.getResponses(request.generate()));

        assertThat(response.getContent(), is(equalTo("")));
        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(response.get("Content-Type"), is(startsWith("plain/text")));
        assertThat(echoCalls.get(), is(1));

        verify(echoSenderMock, times(1)).send(Mockito.any(SlashCommandResponse.class));
    }
}
