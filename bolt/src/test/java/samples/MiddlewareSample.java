package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import org.slf4j.Logger;
import samples.util.ResourceLoader;
import samples.util.TestSlackAppServer;

public class MiddlewareSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);

        app.use((req, resp, chain) -> {
            Logger logger = req.getContext().logger;
            logger.info("request - {}", req);
            resp = chain.next(req);
            logger.info("response - {}", resp);
            return resp;
        });

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
