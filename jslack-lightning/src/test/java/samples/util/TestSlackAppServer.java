package samples.util;

import com.github.seratch.jslack.lightning.App;
import com.github.seratch.jslack.lightning.servlet.SlackAppServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class TestSlackAppServer {

    private final Server server;
    private final App app;

    public TestSlackAppServer(App app) {
        this(app, "/slack/events", 3000);
    }

    public TestSlackAppServer(App app, String path) {
        this(app, path, 3000);
    }

    public TestSlackAppServer(App app, String path, int port) {
        this.app = app;
        server = new Server(port);
        ServletContextHandler handler = new ServletContextHandler();
        ServletHolder holder = new ServletHolder(new SlackAppServlet(app));
        handler.addServlet(holder, path);
        server.setHandler(handler);
    }

    public void start() throws Exception {
        app.start();
        server.start();
        server.join();
    }

    public void stop() throws Exception {
        app.stop();
        server.stop();
    }
}
