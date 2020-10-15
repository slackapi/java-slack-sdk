package util;

import com.slack.api.bolt.App;
import com.slack.api.bolt.WebEndpoint;
import com.slack.api.bolt.handler.WebEndpointHandler;
import com.slack.api.bolt.servlet.SlackAppServlet;
import com.slack.api.bolt.servlet.SlackOAuthAppServlet;
import com.slack.api.bolt.servlet.WebEndpointServlet;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TestSlackAppServer {

    private final Server server;
    private final Map<String, App> pathToApp;

    public TestSlackAppServer(App app) {
        this(app, "/slack/events", 3000);
    }

    public TestSlackAppServer(App app, String path) {
        this(app, path, 3000);
    }

    public TestSlackAppServer(App app, String path, int port) {
        this(toApps(app, path), port);
    }

    public TestSlackAppServer(Map<String, App> pathToApp) {
        this(pathToApp, 3000);
    }

    public TestSlackAppServer(Map<String, App> pathToApp, int port) {
        this.pathToApp = pathToApp;
        server = new Server(port);
        ServletContextHandler handler = new ServletContextHandler();
        Map<String, App> addedOnes = new HashMap<>();
        for (Map.Entry<String, App> entry : this.pathToApp.entrySet()) {
            String appPath = entry.getKey();
            App theApp = entry.getValue();
            theApp.config().setAppPath(appPath);
            handler.addServlet(new ServletHolder(new SlackAppServlet(theApp)), appPath);

            if (theApp.config().isOAuthInstallPathEnabled()) {
                if (theApp.config().isDistributedApp()) {
                    // start
                    String installPath = appPath + theApp.config().getOauthInstallPath();
                    App installPathApp = theApp.toOAuthInstallPathEnabledApp();
                    handler.addServlet(new ServletHolder(new SlackOAuthAppServlet(installPathApp)), installPath);
                    addedOnes.put(installPath, installPathApp);
                } else {
                    log.warn("The app is not ready for handling your Slack App installation URL. Make sure if you set all the necessary values in AppConfig.");
                }
            }

            if (theApp.config().isOAuthRedirectUriPathEnabled()) {
                if (theApp.config().isDistributedApp()) {
                    // callback
                    String redirectUriPath = appPath + theApp.config().getOauthRedirectUriPath();
                    App oAuthCallbackApp = theApp.toOAuthRedirectUriPathEnabledApp();
                    handler.addServlet(new ServletHolder(new SlackOAuthAppServlet(oAuthCallbackApp)), redirectUriPath);
                    addedOnes.put(redirectUriPath, oAuthCallbackApp);
                } else {
                    log.warn("The app is not ready for handling OAuth callback requests. Make sure if you set all the necessary values in AppConfig.");
                }
            }

            // Register additional web endpoints
            if (theApp.getWebEndpointHandlers() != null && theApp.getWebEndpointHandlers().size() > 0) {
                for (Map.Entry<WebEndpoint, WebEndpointHandler> ee : theApp.getWebEndpointHandlers().entrySet()) {
                    WebEndpoint endpoint = ee.getKey();
                    WebEndpointHandler endpointHandler = ee.getValue();
                    ServletHolder servletHolder = new ServletHolder(
                            new WebEndpointServlet(endpoint, endpointHandler, theApp.config()));
                    handler.addServlet(servletHolder, endpoint.getPath());
                }
            }
        }
        pathToApp.putAll(addedOnes);
        server.setHandler(handler);
    }

    public void startAsDaemon() throws Exception {
        for (App app : pathToApp.values()) {
            app.start();
        }
        server.start();
        log.info("⚡️ Bolt app is running!");
    }

    public void start() throws Exception {
        startAsDaemon();
        server.join();
    }

    public void stop() throws Exception {
        for (App app : pathToApp.values()) {
            app.stop();
        }
        log.info("⚡️ Your Bolt app has stopped...");
        server.stop();
    }

    // ----------------------------------------------------
    // internal methods
    // ----------------------------------------------------

    private static Map<String, App> toApps(App app, String path) {
        Map<String, App> apps = new HashMap<>();
        apps.put(path, app);
        return apps;
    }

}
