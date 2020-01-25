package samples;

import com.github.seratch.jslack.lightning.App;
import com.github.seratch.jslack.lightning.AppConfig;
import lombok.extern.slf4j.Slf4j;
import samples.util.ResourceLoader;
import samples.util.TestSlackAppServer;

@Slf4j
public class MiddlewareSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);

        app.use((req, resp, chain) -> {
            log.info("request - {}", req);
            resp = chain.next(req);
            log.info("response - {}", resp);
            return resp;
        });

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
