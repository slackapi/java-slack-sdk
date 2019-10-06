package samples;

import com.github.seratch.jslack.app_backend.interactive_messages.response.ActionResponse;
import com.github.seratch.jslack.app_backend.slash_commands.response.SlashCommandResponse;
import com.github.seratch.jslack.lightning.App;
import com.github.seratch.jslack.lightning.AppConfig;
import com.github.seratch.jslack.lightning.util.JsonOps;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AttachmentActionSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);

        app.use((req, chain) -> {
            log.info("request - {}", req);
            return chain.next(req);
        });

        // https://github.com/seratch/jslack/blob/master/jslack-lightning/src/test/resources/attachments/message1.json
        String firstMessage = ResourceLoader.load("attachments/message1.json");
        app.command("/view", (req, ctx) -> {
            ctx.respond(JsonOps.fromJson(firstMessage, SlashCommandResponse.class));
            return ctx.ack();
        });

        // https://github.com/seratch/jslack/blob/master/jslack-lightning/src/test/resources/action_response/message1.json
        String secondMessage = ResourceLoader.load("action_response/message1.json");
        app.attachmentAction("wopr_game", (req, ctx) -> {
            log.info("attachment action - {}", req.getPayload());
            ctx.respond(JsonOps.fromJson(secondMessage, ActionResponse.class));
            return ctx.ack();
        });

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
