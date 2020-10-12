package test_locally;

import com.slack.api.bolt.App;
import org.http4k.server.Http4kServer;
import org.http4k.server.ServerConfig;
import org.jetbrains.annotations.NotNull;

import static org.http4k.server.Http4kServerKt.asServer;

/**
 * Simple Slack App Server wrapper class for Http4k apps.
 */
public class Http4kSlackAppServer implements Http4kServer {
    private final Http4kServer server;

    public Http4kSlackAppServer(App apiApp, ServerConfig serverConfig) {
        server = asServer(new Http4kSlackApp(apiApp), serverConfig);
    }

    @NotNull
    public Http4kServer start() {
        server.start();
        return this;
    }

    @NotNull
    public Http4kServer stop() {
        if (server != null) server.stop();
        return this;
    }

    @Override
    public void block() {
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        server.close();
    }

    @Override
    public int port() {
        return server.port();
    }
}
