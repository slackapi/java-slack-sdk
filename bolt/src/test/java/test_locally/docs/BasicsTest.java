package test_locally.docs;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.util.JsonOps;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.search.SearchMessagesResponse;
import com.slack.api.webhook.WebhookResponse;
import org.junit.Test;

import java.util.Arrays;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;
import static java.util.stream.Collectors.joining;

public class BasicsTest {

    App app = new App(AppConfig.builder().signingSecret("foo").signingSecret("xoxb-xxx").build());

    @Test
    public void example() {
        app.command("/hello", (req, ctx) -> { // ctx: Context
            return ctx.ack(); // empty body, that means your bot won't post a reply this time
        });

        app.command("/ping", (req, ctx) -> {
            return ctx.ack(":wave: pong");
        });

        app.command("/ping", (req, ctx) -> {
            return ctx.ack(asBlocks(
                    section(section -> section.text(markdownText(":wave: pong"))),
                    actions(actions -> actions
                            .elements(asElements(
                                    button(b -> b.actionId("ping-again").text(plainText(pt -> pt.text("Ping"))).value("ping"))
                            ))
                    )
            ));
        });

        app.command("/ping", (req, ctx) -> {
            return ctx.ack(res -> res.responseType("in_channel").text(":wave: pong"));
        });

        app.command("/hello", (req, ctx) -> {
            // Post a message via response_url
            WebhookResponse result = ctx.respond(res -> res
                    .responseType("ephemeral") // or "in_channnel"
                    .text("Hi there!") // blocks, attachments are also available
            );
            return ctx.ack(); // ack() here doesn't post a message
        });

        app.command("/hello", (req, ctx) -> {
            // ctx.client() holds a valid bot token
            ChatPostMessageResponse response = ctx.client().chatPostMessage(r -> r
                    .channel("C1234567")
                    .text(":wave: How are you?")
            );
            return ctx.ack();
        });

        app.command("/hello", (req, ctx) -> {
            ChatPostMessageResponse response = ctx.say(":wave: How are you?");
            return ctx.ack();
        });

        app.command("/my-search", (req, ctx) -> {
            String query = req.getPayload().getText();
            if (query == null || query.trim().length() == 0) {
                return ctx.ack("Please give some query.");
            }

            String userToken = ctx.getRequestUserToken(); // enabling InstallationService required
            if (userToken != null) {
                SearchMessagesResponse response = ctx.client().searchMessages(r -> r
                        .token(userToken) // Overwrite underlying bot token with the given user token
                        .query(query));
                if (response.isOk()) {
                    String reply = response.getMessages().getTotal() + " results found for " + query;
                    return ctx.ack(reply);
                } else {
                    String reply = "Failed to search by " + query + " (error: " + response.getError() + ")";
                    return ctx.ack(reply);
                }
            } else {
                return ctx.ack("Please allow this Slack app to run search queries for you.");
            }
        });

        class WeatherResult {
            String toMessage() {
                return "sunny";
            }
        }
        class WeatherService {
            public WeatherResult find(String keyword) {
                return new WeatherResult();
            }
        }
        WeatherService weatherService = new WeatherService();

        app.command("/weather", (req, ctx) -> {
            String keyword = req.getPayload().getText();
            String userId = req.getPayload().getUserId();
            ctx.logger.info("Weather search by keyword: {} for user: {}", keyword, userId);
            return ctx.ack(weatherService.find(keyword).toMessage());
        });

        class DebugResponseBody {
            String responseType; // ephemeral, in_channel
            String text;
        }
        String debugMode = System.getenv("SLACK_APP_DEBUG_MODE");

        if (debugMode != null && debugMode.equals("1")) { // enable only when SLACK_APP_DEBUG_MODE=1
            app.use((req, _resp, chain) -> {
                Response resp = chain.next(req);
                if (resp.getStatusCode() != 200) {
                    resp.getHeaders().put("content-type", Arrays.asList(resp.getContentType()));
                    // dump all the headers as a single string
                    String headers = resp.getHeaders().entrySet().stream()
                            .map(e -> e.getKey() +  ": " + e.getValue() + "\n").collect(joining());

                    // set an ephemeral message with useful information
                    DebugResponseBody body = new DebugResponseBody();
                    body.responseType = "ephemeral";
                    body.text =
                            ":warning: *[DEBUG MODE] Something is technically wrong* :warning:\n" +
                                    "Below is a response the Slack app was going to send...\n" +
                                    "*Status Code*: " + resp.getStatusCode() + "\n" +
                                    "*Headers*: ```" + headers + "```" + "\n" +
                                    "*Body*: ```" + resp.getBody() + "```";
                    resp.setBody(JsonOps.toJsonString(body));

                    resp.setStatusCode(200);
                }
                return resp;
            });
        }
    }

}
