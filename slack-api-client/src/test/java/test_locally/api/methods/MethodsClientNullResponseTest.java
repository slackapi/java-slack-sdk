package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.SlackApiException;
import com.slack.api.util.http.listener.HttpResponseListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import util.MockSlackApiServer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static util.MockSlackApi.ValidToken;

@RunWith(Parameterized.class)
public class MethodsClientNullResponseTest {

    @Parameterized.Parameters(name = "{index}: body={0}")
    public static Collection<Object[]> bodies() {
        return Arrays.asList(new Object[][]{{""}, {" \n\t"}, {"null"}});
    }

    @Parameterized.Parameter
    public String body;

    private MockSlackApiServer server;
    private Slack slack;
    private final List<HttpResponseListener.State> responses = new ArrayList<>();

    @Before
    public void setup() throws Exception {
        server = new MockSlackApiServer(new HttpServlet() {
            @Override
            protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
                response.setStatus(200);
                response.setContentType("application/json");
                response.setHeader("X-Slack-Req-Id", "null-response-test");
                response.getWriter().write(body);
            }
        });
        server.start();
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
        config.getMethodsConfig().setStatsEnabled(false);
        config.getHttpClientResponseHandlers().add(new HttpResponseListener() {
            @Override
            public void accept(State state) {
                responses.add(state);
            }
        });
        slack = Slack.getInstance(config);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void rejectsNullResponseWithoutTeamId() {
        SlackApiException failure = assertThrows(SlackApiException.class,
                () -> slack.methods().apiTest(r -> r));
        assertInvalidResponse(failure, false);
    }

    @Test
    public void rejectsNullResponseWithTeamId() {
        SlackApiException failure = assertThrows(SlackApiException.class,
                () -> slack.methods(ValidToken, "T123").usergroupsList(r -> r));
        assertInvalidResponse(failure, false);
    }

    @Test
    public void rejectsNullResponseForMultipartRequest() {
        SlackApiException failure = assertThrows(SlackApiException.class,
                () -> slack.methods(ValidToken, "T123").filesUpload(r ->
                        r.fileData("test".getBytes(StandardCharsets.UTF_8)).filename("test.txt")));
        assertInvalidResponse(failure, true);
    }

    @Test
    public void rejectsNullAnalyticsJsonResponse() {
        SlackApiException failure = assertThrows(SlackApiException.class,
                () -> slack.methods(ValidToken, "T123")
                        .adminAnalyticsGetFile(r -> r.date("2020-10-20").type("member")));
        assertInvalidResponse(failure, false);
    }

    private void assertInvalidResponse(SlackApiException failure, boolean binaryRequest) {
        assertEquals(200, failure.getResponse().code());
        assertEquals(body, failure.getResponseBody());
        assertEquals("null-response-test", failure.getResponse().header("X-Slack-Req-Id"));
        assertNull(failure.getError());
        assertEquals(1, responses.size());
        assertEquals(body, responses.get(0).getParsedResponseBody());
        assertEquals(binaryRequest, responses.get(0).isRequestBodyBinary());
    }
}
