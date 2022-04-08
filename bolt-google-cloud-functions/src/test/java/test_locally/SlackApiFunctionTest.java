package test_locally;

import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.google_cloud_functions.SlackApiFunction;
import com.slack.api.model.event.ReactionAddedEvent;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class SlackApiFunctionTest {
    private static HttpRequest buildTestHttpRequest() {
        return new HttpRequest() {

            private byte[] bytes = ("{\n" +
                    "  \"token\": \"verification_token\",\n" +
                    "  \"team_id\": \"T111\",\n" +
                    "  \"enterprise_id\": \"E111\",\n" +
                    "  \"api_app_id\": \"A111\",\n" +
                    "  \"event\": {\n" +
                    "    \"type\": \"reaction_added\",\n" +
                    "    \"user\": \"W111\",\n" +
                    "    \"item\": {\n" +
                    "      \"type\": \"message\",\n" +
                    "      \"channel\": \"C111\",\n" +
                    "      \"ts\": \"1599529504.000400\"\n" +
                    "    },\n" +
                    "    \"reaction\": \"heart_eyes\",\n" +
                    "    \"item_user\": \"W111\",\n" +
                    "    \"event_ts\": \"1599616881.000800\"\n" +
                    "  },\n" +
                    "  \"type\": \"event_callback\",\n" +
                    "  \"event_id\": \"Ev111\",\n" +
                    "  \"event_time\": 1599616881,\n" +
                    "  \"authed_users\": [\n" +
                    "    \"W111\"\n" +
                    "  ]\n" +
                    "}").getBytes(StandardCharsets.UTF_8);

            @Override
            public String getMethod() {
                return "POST";
            }

            @Override
            public String getUri() {
                return "http://localhost:3000/slack/events";
            }

            @Override
            public String getPath() {
                return "/slack/events";
            }

            @Override
            public Optional<String> getQuery() {
                return Optional.empty();
            }

            @Override
            public Map<String, List<String>> getQueryParameters() {
                return Collections.emptyMap();
            }

            @Override
            public Map<String, HttpPart> getParts() {
                return Collections.emptyMap();
            }

            @Override
            public Optional<String> getContentType() {
                return Optional.of("application/json");
            }

            @Override
            public long getContentLength() {
                return bytes.length;
            }

            @Override
            public Optional<String> getCharacterEncoding() {
                return Optional.empty();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(bytes);
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return new BufferedReader(new InputStreamReader(getInputStream()));
            }

            @Override
            public Map<String, List<String>> getHeaders() {
                return Collections.emptyMap();
            }
        };
    }

    private static HttpResponse buildNullHttpResponse() {
        return new HttpResponse() {
            @Override
            public void setStatusCode(int code) {
            }

            @Override
            public void setStatusCode(int code, String message) {
            }

            @Override
            public void setContentType(String contentType) {
            }

            @Override
            public Optional<String> getContentType() {
                return Optional.empty();
            }

            @Override
            public void appendHeader(String header, String value) {
            }

            @Override
            public Map<String, List<String>> getHeaders() {
                return null;
            }

            @Override
            public OutputStream getOutputStream() throws IOException {
                return new ByteArrayOutputStream();
            }

            @Override
            public BufferedWriter getWriter() throws IOException {
                return new BufferedWriter(new StringWriter());
            }
        };
    }

    @Test
    public void service() throws Exception {
        App app = new App(
                AppConfig.builder().signingSecret("xxx").singleTeamBotToken("xoxb-").build(),
                Collections.emptyList() // TODO: mock authorization etc.
        );
        AtomicBoolean called = new AtomicBoolean(false);
        app.event(ReactionAddedEvent.class, (payload, ctx) -> {
            called.set(true);
            return ctx.ack();
        });

        SlackApiFunction function = new SlackApiFunction(app);
        function.service(buildTestHttpRequest(), buildNullHttpResponse());
        assertThat(called.get(), is(true));
    }
}
