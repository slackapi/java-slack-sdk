package test_locally.util;

import com.slack.api.bolt.util.ListenerCodeSuggestion;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ListenerCodeSuggestionTest {

    @Test
    public void toEventClassName() {
        assertEquals("AppMentionEvent", ListenerCodeSuggestion.toEventClassName("app_mention"));
        assertEquals("MessageEvent", ListenerCodeSuggestion.toEventClassName("message"));
        assertEquals("MessageBotEvent", ListenerCodeSuggestion.toEventClassName("message:bot_message"));
        assertEquals("MessageChangedEvent", ListenerCodeSuggestion.toEventClassName("message:message_changed"));
        assertEquals("MessageChannelPostingPermissionsEvent",
                ListenerCodeSuggestion.toEventClassName("message:channel_posting_permissions"));
    }

    @Test
    public void event() {
        String expected = "---\n" +
                "[Suggestion] You can handle this type of event with the following listener function:\n" +
                "\n" +
                "app.event(AppMentionEvent.class, (payload, ctx) -> {\n" +
                "  return ctx.ack();\n" +
                "});\n";
        String actual = ListenerCodeSuggestion.event("app_mention");
        assertEquals(expected, actual);
    }

    @Test
    public void command() {
        String expected = "---\n" +
                "[Suggestion] You can handle this type of event with the following listener function:\n" +
                "\n" +
                "app.command(\"/hello\", (req, ctx) -> {\n" +
                "  return ctx.ack();\n" +
                "});\n";
        String actual = ListenerCodeSuggestion.command("/hello");
        assertEquals(expected, actual);
    }

    @Test
    public void globalShortcut() {
        String expected = "---\n" +
                "[Suggestion] You can handle this type of event with the following listener function:\n" +
                "\n" +
                "app.globalShortcut(\"callback-id\", (req, ctx) -> {\n" +
                "  // Do something where\n" +
                "  return ctx.ack();\n" +
                "});\n";
        String actual = ListenerCodeSuggestion.globalShortcut("callback-id");
        assertEquals(expected, actual);
    }

    @Test
    public void messageShortcut() {
        String expected = "---\n" +
                "[Suggestion] You can handle this type of event with the following listener function:\n" +
                "\n" +
                "app.messageShortcut(\"callback-id\", (req, ctx) -> {\n" +
                "  // Do something where\n" +
                "  return ctx.ack();\n" +
                "});\n";
        String actual = ListenerCodeSuggestion.messageShortcut("callback-id");
        assertEquals(expected, actual);
    }

    @Test
    public void dialogSuggestion() {
        String expected = "---\n" +
                "[Suggestion] You can handle this type of event with the following listener function:\n" +
                "\n" +
                "app.dialogSubmission(\"the-id\", (req, ctx) -> {\n" +
                "  List<Option> options = Arrays.asList(Option.builder().label(\"label\").value(\"value\").build());\n" +
                "  return ctx.ack(r -> r.options(options));\n" +
                "});\n";
        String actual = ListenerCodeSuggestion.dialogSuggestion("the-id");
        assertEquals(expected, actual);
    }

    @Test
    public void dialogSubmission() {
        String expected = "---\n" +
                "[Suggestion] You can handle this type of event with the following listener function:\n" +
                "\n" +
                "app.dialogSubmission(\"the-id\", (req, ctx) -> {\n" +
                "  // Do something where\n" +
                "  return ctx.ack();\n" +
                "});\n";
        String actual = ListenerCodeSuggestion.dialogSubmission("the-id");
        assertEquals(expected, actual);
    }

    @Test
    public void dialogCancellation() {
        String expected = "---\n" +
                "[Suggestion] You can handle this type of event with the following listener function:\n" +
                "\n" +
                "app.dialogCancellation(\"the-id\", (req, ctx) -> {\n" +
                "  // Do something where\n" +
                "  return ctx.ack();\n" +
                "});\n";
        String actual = ListenerCodeSuggestion.dialogCancellation("the-id");
        assertEquals(expected, actual);
    }

    @Test
    public void blockAction() {
        String expected = "---\n" +
                "[Suggestion] You can handle this type of event with the following listener function:\n" +
                "\n" +
                "app.blockAction(\"the-id\", (req, ctx) -> {\n" +
                "  // Do something where\n" +
                "  return ctx.ack();\n" +
                "});\n";
        String actual = ListenerCodeSuggestion.blockAction("the-id");
        assertEquals(expected, actual);
    }

    @Test
    public void blockSuggestion() {
        String expected = "---\n" +
                "[Suggestion] You can handle this type of event with the following listener function:\n" +
                "\n" +
                "app.blockSuggestion(\"the-id\", (req, ctx) -> {\n" +
                "  List<Option> options = Arrays.asList(Option.builder().text(plainText(\"label\")).value(\"v\").build());\n" +
                "  return ctx.ack(r -> r.options(options));\n" +
                "});\n";
        String actual = ListenerCodeSuggestion.blockSuggestion("the-id");
        assertEquals(expected, actual);
    }

    @Test
    public void attachmentAction() {
        String expected = "---\n" +
                "[Suggestion] You can handle this type of event with the following listener function:\n" +
                "\n" +
                "app.attachmentAction(\"the-id\", (req, ctx) -> {\n" +
                "  // Do something where\n" +
                "  return ctx.ack();\n" +
                "});\n";
        String actual = ListenerCodeSuggestion.attachmentAction("the-id");
        assertEquals(expected, actual);
    }

    @Test
    public void viewSubmission() {
        String expected = "---\n" +
                "[Suggestion] You can handle this type of event with the following listener function:\n" +
                "\n" +
                "app.viewSubmission(\"the-id\", (req, ctx) -> {\n" +
                "  // Sent inputs: req.getPayload().getView().getState().getValues()\n" +
                "  return ctx.ack();\n" +
                "});\n";
        String actual = ListenerCodeSuggestion.viewSubmission("the-id");
        assertEquals(expected, actual);
    }

    @Test
    public void viewClosed() {
        String expected = "---\n" +
                "[Suggestion] You can handle this type of event with the following listener function:\n" +
                "\n" +
                "app.viewClosed(\"the-id\", (req, ctx) -> {\n" +
                "  // Do something where\n" +
                "  return ctx.ack();\n" +
                "});\n";
        String actual = ListenerCodeSuggestion.viewClosed("the-id");
        assertEquals(expected, actual);
    }

}
