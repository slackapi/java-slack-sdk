package test_locally;

import com.slack.api.bolt.App;
import org.junit.Test;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;

public class RespondTest {

    App app = new App();

    @Test
    public void event() {
        // doesn't support respond method
    }

    @Test
    public void command() {
        app.command("/foo", (req, ctx) -> {
            ctx.respond("bar");
            ctx.respond(asBlocks(section(s -> s.text(markdownText("*bold* _italic_"))), divider()));
            ctx.respond(r -> r.text("bar"));
            return ctx.ack();
        });
    }

    @Test
    public void messageShortcut() {
        app.messageShortcut("callback-id", (req, ctx) -> {
            ctx.respond("bar");
            ctx.respond(asBlocks(section(s -> s.text(markdownText("*bold* _italic_"))), divider()));
            ctx.respond(r -> r.text("bar"));
            return ctx.ack();
        });
    }

    @Test
    public void blockAction() {
        app.blockAction("action-id", (req, ctx) -> {
            ctx.respond("bar");
            ctx.respond(asBlocks(section(s -> s.text(markdownText("*bold* _italic_"))), divider()));
            ctx.respond(r -> r.text("bar"));
            return ctx.ack();
        });
    }

    @Test
    public void blockSuggestion() {
        // doesn't support respond method
    }

    @Test
    public void viewSubmission() {
        // doesn't support respond method
    }

    @Test
    public void viewClosed() {
        // doesn't support respond method
    }

    @Test
    public void dialogSubmission() {
        app.dialogSubmission("callback-id", (req, ctx) -> {
            ctx.respond("bar");
            ctx.respond(asBlocks(section(s -> s.text(markdownText("*bold* _italic_"))), divider()));
            ctx.respond(r -> r.text("bar"));
            return ctx.ack();
        });
    }

    @Test
    public void dialogSuggestion() {
        // doesn't support respond method
    }

    @Test
    public void dialogCancellation() {
        app.dialogCancellation("callback-id", (req, ctx) -> {
            ctx.respond("bar");
            ctx.respond(asBlocks(section(s -> s.text(markdownText("*bold* _italic_"))), divider()));
            ctx.respond(r -> r.text("bar"));
            return ctx.ack();
        });
    }

    @Test
    public void attachmentAction() {
        app.attachmentAction("callback-id", (req, ctx) -> {
            ctx.respond("bar");
            ctx.respond(asBlocks(section(s -> s.text(markdownText("*bold* _italic_"))), divider()));
            ctx.respond(r -> r.text("bar"));
            return ctx.ack();
        });
    }

    @Test
    public void webhook() {
        // doesn't support respond method
    }
}
