package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.response.bots.BotsInfoResponse;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.model.event.AppMentionEvent;
import util.ResourceLoader;
import util.TestSlackAppServer;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.plainTextInput;
import static com.slack.api.model.view.Views.*;
import static org.junit.Assert.assertNotNull;

public class OrgAppSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig("appConfig_org_app.json");
        App apiApp = new App(config).asOAuthApp(false);
        apiApp.use((req, resp, chain) -> {
            String body = req.getRequestBodyAsString();
            String payload = body.startsWith("payload=") ? URLDecoder.decode(body.split("payload=")[1], "UTF-8") : body;
            req.getContext().logger.info(payload);
            return chain.next(req);
        });

        apiApp.event(AppMentionEvent.class, (req, ctx) -> {
            ctx.say("Hi!");
            BotsInfoResponse response = ctx.client().botsInfo(r -> r.bot(ctx.getBotId()));
            ctx.logger.info("response: {}", response);
            response = ctx.client().botsInfo(r -> r.teamId(req.getTeamId()).bot(ctx.getBotId()));
            ctx.logger.info("response: {}", response);
            return ctx.ack();
        });

        apiApp.command("/org-level-command", (req, ctx) -> {
            return ctx.ack("It works!");
        });

        apiApp.globalShortcut("org-level-shortcut", (req, ctx) -> {
            ViewsOpenResponse viewsOpen = ctx.client().viewsOpen(r -> r
                    .triggerId(req.getContext().getTriggerId())
                    .view(view(v -> v
                            .type("modal")
                            .callbackId("org-app-view")
                            .title(viewTitle(vt -> vt.type("plain_text").text("Org App")))
                            .close(viewClose(vc -> vc.type("plain_text").text("Close")))
                            .blocks(asBlocks(section(s -> s.text(plainText("It works!")))))
                    )));
            return ctx.ack();
        });

        App oauthApp = new App(config).asOAuthApp(true);

        Map<String, App> apps = new HashMap<>();
        apps.put("/slack/events", apiApp);
        apps.put("/slack/oauth/", oauthApp);
        TestSlackAppServer server = new TestSlackAppServer(apps);
        server.start();
    }
}
