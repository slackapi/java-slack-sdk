package samples;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.interactive_components.response.Option;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import samples.util.ResourceLoader;
import samples.util.TestSlackAppServer;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;

@Slf4j
public class BlocksSample {

    public static void main(String[] args) throws Exception {
        SlackConfig slackConfig = new SlackConfig();
        slackConfig.setPrettyResponseLoggingEnabled(true);
        Slack slack = Slack.getInstance(slackConfig);
        AppConfig appConfig = ResourceLoader.loadAppConfig();
        appConfig.setSlack(slack);
        App app = new App(appConfig);

        Gson gson = GsonFactory.createSnakeCase(slackConfig);

        app.use((req, resp, chain) -> {
            String body = req.getRequestBodyAsString();
            String payload = body.startsWith("payload=") ? URLDecoder.decode(body.split("payload=")[1], "UTF-8") : body;
            try {
                payload = gson.toJson(gson.fromJson(payload, JsonElement.class));
            } catch (JsonSyntaxException ignore) {
            }
            req.getContext().logger.info("Payload:\n{}", payload);
            return chain.next(req);
        });

        app.command("/test-blocks", (req, ctx) -> {
            ctx.respond(asBlocks(
                    section(section -> section.text(markdownText("*Actions:*"))),
                    actions(actions -> actions
                            .blockId("actions-block")
                            .elements(asElements(
                                    button(b -> b.actionId("block-button-1").text(plainText(pt -> pt.emoji(true).text("Button1"))).value("v1")),
                                    button(b -> b.actionId("block-button-2").text(plainText(pt -> pt.emoji(true).text("Button2"))).value("v2"))
                            ))
                    ),
                    // Select Menu
                    section(s -> s.text(markdownText("*Static Select:*"))
                            .accessory(staticSelect(ss -> ss.actionId("block-static-select-action").options(asOptions(
                                    option(plainText("Option 1"), "1"),
                                    option(plainText("Option 2"), "2")
                            ))))),
                    section(s -> s.text(markdownText("*Users Select:*"))
                            .accessory(usersSelect(us -> us.actionId("block-users-action")))),
                    section(s -> s.text(markdownText("*Conversations Select:*"))
                            .accessory(conversationsSelect(us -> us.actionId("block-conversations-action")))),
                    section(s -> s.text(markdownText("*Channels Select:*"))
                            .accessory(channelsSelect(us -> us.actionId("block-channels-action")))),
                    section(s -> s.text(markdownText("*External Select:*"))
                            .accessory(externalSelect(us -> us.actionId("block-external-select-action").minQueryLength(0)))),
                    // Multi Select Menu
                    section(s -> s.text(markdownText("*Multi Static Select:*"))
                            .accessory(multiStaticSelect(ss -> ss.actionId("block-multi-static-select-action").options(asOptions(
                                    option(plainText("Option 1"), "1"),
                                    option(plainText("Option 2"), "2")
                            ))))),
                    section(s -> s.text(markdownText("*Multi Users Select:*"))
                            .accessory(multiUsersSelect(us -> us.actionId("block-multi-users-action")))),
                    section(s -> s.text(markdownText("*Multi Conversations Select:*"))
                            .accessory(multiConversationsSelect(us -> us.actionId("block-multi-conversations-action")))),
                    section(s -> s.text(markdownText("*Multi Channels Select:*"))
                            .accessory(multiChannelsSelect(us -> us.actionId("block-multi-channels-action")))),
                    section(s -> s.text(markdownText("*Multi External Select:*"))
                            .accessory(multiExternalSelect(us -> us.actionId("block-multi-external-select-action").minQueryLength(0))))
            ));
            return ctx.ack();
        });

        app.blockAction(Pattern.compile("block-.+"), (req, ctx) -> {
            ctx.logger.info("Action : {}", gson.toJson(req.getPayload().getActions().get(0)));
            return ctx.ack();
        });

        app.blockSuggestion(Pattern.compile("block-.+"), (req, ctx) -> {
            ctx.logger.info("payload: {}", req.getPayload());
            List<Option> options = Arrays.asList(
                    Option.builder().text(plainText("Option 1")).value("1").build(),
                    Option.builder().text(plainText("Option 2")).value("2").build(),
                    Option.builder().text(plainText("Option 3")).value("3").build()
            );
            return ctx.ack(r -> r.options(options));
        });

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
