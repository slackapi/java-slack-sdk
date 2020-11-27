package samples;

import com.slack.api.SlackConfig;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.event.AppMentionEvent;
import com.slack.api.model.event.TeamAccessGrantedEvent;
import com.slack.api.model.event.TeamAccessRevokedEvent;
import com.slack.api.model.view.View;
import com.slack.api.util.json.GsonFactory;
import util.TestSlackAppServer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.input;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.plainTextInput;
import static com.slack.api.model.view.Views.*;

public class EnterpriseGridOrgAppSample {

    public static void main(String[] args) throws Exception {
        String jsonString = Files.readAllLines(Paths.get("bolt-servlet/src/test/resources/appConfig_org_app.json"))
                .stream().collect(Collectors.joining("\n"));
        AppConfig appConfig = GsonFactory.createCamelCase(SlackConfig.DEFAULT).fromJson(jsonString, AppConfig.class);
        App app = new App(appConfig);
        app.event(AppMentionEvent.class, (req, ctx) -> {
            ctx.say("Hi");
            return ctx.ack();
        });
        app.event(TeamAccessGrantedEvent.class, (req, ctx) -> ctx.ack());
        app.event(TeamAccessRevokedEvent.class, (req, ctx) -> ctx.ack());

        View modal = view(v -> v
                .type("modal")
                .callbackId("test-view")
                .title(viewTitle(vt -> vt.type("plain_text").text("Org App Modal")))
                .close(viewClose(vc -> vc.type("plain_text").text("Close")))
                .submit(viewSubmit(vs -> vs.type("plain_text").text("Submit")))
                .blocks(asBlocks(input(input -> input
                        .blockId("agenda-block")
                        .element(plainTextInput(pti -> pti.actionId("agenda-action").multiline(true)))
                        .label(plainText(pt -> pt.text("Detailed Agenda").emoji(true)))
                )))
        );
        app.globalShortcut("org-level-shortcut", (req, ctx) -> {
            ctx.asyncClient().viewsOpen(r -> r.triggerId(req.getContext().getTriggerId()).view(modal));
            return ctx.ack();
        });
        app.viewSubmission("test-view", (req, ctx) -> ctx.ack());

        app.command("/org-level-command", (req, ctx) -> ctx.ack("Thanks!"));

        Map<String, App> apps = new HashMap<>();
        apps.put("/slack/events", app);
        apps.put("/slack/oauth/", new App(appConfig).asOAuthApp(true));
        TestSlackAppServer server = new TestSlackAppServer(apps);
        server.start();
    }
}
