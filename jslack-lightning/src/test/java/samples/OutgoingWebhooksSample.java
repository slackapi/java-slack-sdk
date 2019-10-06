package samples;

import com.github.seratch.jslack.lightning.App;
import com.github.seratch.jslack.lightning.AppConfig;
import com.github.seratch.jslack.lightning.middleware.builtin.LegacyRequestVerification;
import com.github.seratch.jslack.lightning.middleware.builtin.SingleTeamAuthorization;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class OutgoingWebhooksSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        // https://github.com/seratch/jslack/blob/master/jslack-lightning/src/test/resources/
        String verificationToken = ResourceLoader.load("verificationToken.txt");
        App app = new App(config, Arrays.asList(
                // x-slack-signature unsupported
                new LegacyRequestVerification(verificationToken),
                new SingleTeamAuthorization(config)
        ));

        app.webhook("seratch", (req, ctx) -> {
            log.info("outgoing webhook - {}", req);
            return ctx.ack(r -> r.text("Hello " + req.getPayload().getUserName()));
        });
        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
