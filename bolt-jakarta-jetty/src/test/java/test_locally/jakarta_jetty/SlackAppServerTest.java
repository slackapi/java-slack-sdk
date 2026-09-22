package test_locally.jakarta_jetty;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.jakarta_jetty.SlackAppServer;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

public class SlackAppServerTest {

    @Test
    public void startsAndServesMinimalErrorResponseForEverySupportedAcceptType() throws Exception {
        assumeThat(System.getenv("SLACK_APP_LOCAL_DEBUG"), is(nullValue()));

        int port = findAvailablePort();
        App app = new App(new AppConfig(), Collections.emptyList());
        SlackAppServer server = new SlackAppServer(app, port);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> serverFuture = executor.submit(() -> {
            server.start();
            return null;
        });

        try {
            String[][] acceptTypes = {
                    {null, "text/html"},
                    {"text/html", "text/html"},
                    {"text/*", "text/html"},
                    {"*/*", "text/html"},
                    {"application/json", "application/json"},
                    {"text/json", "text/json"},
                    {"text/plain", "text/plain"}
            };
            for (String[] acceptType : acceptTypes) {
                HttpURLConnection connection = awaitResponse(serverFuture, port, acceptType[0]);
                try {
                    assertMinimalErrorResponse(connection, acceptType[0], acceptType[1]);
                } finally {
                    connection.disconnect();
                }
            }
        } finally {
            try {
                server.stop();
                serverFuture.get(10, TimeUnit.SECONDS);
            } finally {
                executor.shutdownNow();
            }
        }
    }

    private static void assertMinimalErrorResponse(
            HttpURLConnection connection, String acceptType, String expectedContentType) throws IOException {
        String requestDescription = acceptType == null ? "with default Accept" : "with Accept: " + acceptType;
        assertThat("status " + requestDescription, connection.getResponseCode(), is(equalTo(404)));
        assertThat("Server header " + requestDescription, connection.getHeaderField("Server"), is(nullValue()));
        assertThat("Content-Type " + requestDescription,
                connection.getContentType(), startsWith(expectedContentType));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getErrorStream(), StandardCharsets.UTF_8))) {
            assertThat("body " + requestDescription,
                    reader.lines().collect(Collectors.joining("\n")),
                    is(equalTo("{\"status\":\"404\"}")));
        }
    }

    private static int findAvailablePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    private static HttpURLConnection awaitResponse(Future<?> serverFuture, int port, String acceptType)
            throws Exception {
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(10);
        IOException lastFailure = null;
        while (System.nanoTime() < deadline) {
            if (serverFuture.isDone()) {
                serverFuture.get();
                throw new AssertionError("SlackAppServer stopped before accepting requests");
            }

            HttpURLConnection connection = (HttpURLConnection) new URL(
                    "http://127.0.0.1:" + port + "/missing").openConnection();
            if (acceptType != null) {
                connection.setRequestProperty("Accept", acceptType);
            }
            connection.setConnectTimeout(200);
            connection.setReadTimeout(200);
            try {
                connection.getResponseCode();
                return connection;
            } catch (IOException e) {
                lastFailure = e;
                connection.disconnect();
                Thread.sleep(10);
            }
        }
        throw new AssertionError("SlackAppServer did not accept requests within 10 seconds", lastFailure);
    }
}
