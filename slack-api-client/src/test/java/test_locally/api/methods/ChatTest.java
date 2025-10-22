package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.model.Attachment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import static com.slack.api.model.Attachments.asAttachments;
import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class ChatTest {

    MockSlackApiServer server = new MockSlackApiServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    @Before
    public void setup() throws Exception {
        server.start();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void chatAppendStream() throws Exception {
        assertThat(
                slack.methods(ValidToken).chatAppendStream(r -> r.channel("C123").ts("123.123").markdownText("**hello!**")
                ).isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatAppendStream(r -> r.channel("C123").ts("123.123").markdownText("**hello!**")
                ).get().isOk(), is(true));
    }

    @Test
    public void postMessage() throws Exception {
        assertThat(
                slack.methods(ValidToken).chatPostMessage(r -> r.channel("C123").text("Hi!")
                ).isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatPostMessage(r -> r.channel("C123").text("Hi!")
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatPostMessage(r -> r.channel("C123").blocksAsString("[]")
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatPostMessage(r -> r.channel("C123").blocks(asBlocks())
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatPostMessage(r -> r.channel("C123").blocksAsString("[]").blocks(asBlocks())
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatPostMessage(r -> r.channel("C123").attachmentsAsString("[]")
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatPostMessage(r -> r.channel("C123").attachments(asAttachments())
                ).get().isOk(), is(true));
    }

    @Test
    public void chatPostEphemeral() throws Exception {
        assertThat(
                slack.methods(ValidToken).chatPostEphemeral(r -> r.channel("C123").user("U123").text("Hi!")
                ).isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatPostEphemeral(r -> r.channel("C123").user("U123").text("Hi!")
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatPostEphemeral(r -> r.channel("C123").user("U123").blocksAsString("[]")
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatPostEphemeral(r -> r.channel("C123").user("U123").blocks(asBlocks())
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatPostEphemeral(r -> r.channel("C123").user("U123").blocksAsString("[]").blocks(asBlocks())
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatPostEphemeral(r -> r.channel("C123").user("U123").attachmentsAsString("[]")
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatPostEphemeral(r -> r.channel("C123").user("U123").attachments(asAttachments())
                ).get().isOk(), is(true));
    }

    @Test
    public void chatDelete() throws Exception {
        assertThat(
                slack.methods(ValidToken).chatDelete(r -> r.channel("C123").ts("123.123")
                ).isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatDelete(r -> r.channel("C123").ts("123.123")
                ).get().isOk(), is(true));
    }

    @Test
    public void chatDeleteScheduledMessage() throws Exception {
        assertThat(
                slack.methods(ValidToken).chatDeleteScheduledMessage(r ->
                        r.channel("C123").scheduledMessageId("id")
                ).isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatDeleteScheduledMessage(r ->
                        r.channel("C123").scheduledMessageId("id")
                ).get().isOk(), is(true));
    }

    @Test
    public void chatGetPermalink() throws Exception {
        assertThat(
                slack.methods(ValidToken).chatGetPermalink(r ->
                        r.channel("C123").messageTs("123.123")
                ).isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatGetPermalink(r ->
                        r.channel("C123").messageTs("123.123")
                ).get().isOk(), is(true));
    }

    @Test
    public void chatMeMessage() throws Exception {
        assertThat(
                slack.methods(ValidToken).chatMeMessage(r ->
                        r.channel("C123").text("Hi")
                ).isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatMeMessage(r ->
                        r.channel("C123").text("Hi")
                ).get().isOk(), is(true));
    }

    @Test
    public void chatScheduleMessage() throws Exception {
        assertThat(
                slack.methods(ValidToken).chatScheduleMessage(r ->
                        r.channel("C123").text("Hi").postAt(123)
                ).isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatScheduleMessage(r ->
                        r.channel("C123").text("Hi").postAt(123)
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatScheduleMessage(r -> r.channel("C123").blocksAsString("[]").postAt(123)
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatScheduleMessage(r -> r.channel("C123").blocks(asBlocks()).postAt(123)
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatScheduleMessage(r -> r.channel("C123").blocksAsString("[]").blocks(asBlocks()).postAt(123)
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatScheduleMessage(r -> r.channel("C123").attachmentsAsString("[]").postAt(123)
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatScheduleMessage(r -> r.channel("C123").attachments(asAttachments()).postAt(123)
                ).get().isOk(), is(true));
    }

    @Test
    public void chatStartStream() throws Exception {
        assertThat(
                slack.methods(ValidToken).chatStartStream(r -> r.channel("C123").threadTs("123.123")
                ).isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatStartStream(r -> r.channel("C123").threadTs("123.123")
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatStartStream(r -> r.channel("C123").threadTs("123.123").markdownText("**hello!**")
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatStartStream(r -> r.channel("C123").threadTs("123.123").recipientUserId("U123").recipientTeamId("T123")
                ).get().isOk(), is(true));
    }

    @Test
    public void chatStopStream() throws Exception {
        assertThat(
                slack.methods(ValidToken).chatStopStream(r -> r.channel("C123").ts("123.123")
                ).isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatStopStream(r -> r.channel("C123").ts("123.123")
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatStopStream(r -> r.channel("C123").ts("123.123").markdownText("**hello!**")
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatStopStream(r -> r.channel("C123").ts("123.123").blocksAsString("[]")
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatStopStream(r -> r.channel("C123").ts("123.123").blocks(asBlocks())
                ).get().isOk(), is(true));
    }

    @Test
    public void chatUnfurl() throws Exception {
        assertThat(
                slack.methods(ValidToken).chatUnfurl(r ->
                        r.channel("C123").ts("123.123")
                ).isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatUnfurl(r ->
                        r.channel("C123").ts("123.123")
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatUnfurl(r ->
                        r.channel("C123").ts("123.123").rawUnfurls("https://www.example.com")
                ).get().isOk(), is(true));
    }

    @Test
    public void chatUpdate() throws Exception {
        assertThat(
                slack.methods(ValidToken).chatUpdate(r ->
                        r.channel("C123").ts("123.123").text("(edited)")
                ).isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatUpdate(r ->
                        r.channel("C123").ts("123.123").text("(edited)")
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatUpdate(r -> r.channel("C123").ts("123.123").blocksAsString("[]")
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatUpdate(r -> r.channel("C123").ts("123.123").blocks(asBlocks())
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatUpdate(r -> r.channel("C123").ts("123.123").blocksAsString("[]").blocks(asBlocks())
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatUpdate(r -> r.channel("C123").ts("123.123").attachmentsAsString("[]")
                ).get().isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatUpdate(r -> r.channel("C123").ts("123.123").attachments(asAttachments())
                ).get().isOk(), is(true));
    }

    @Test
    public void chatScheduledMessagesList() throws Exception {
        assertThat(
                slack.methods(ValidToken).chatScheduledMessagesList(r -> r.channel("C123"))
                        .isOk(), is(true));
        assertThat(
                slack.methodsAsync(ValidToken).chatScheduledMessagesList(r -> r.channel("C123"))
                        .get().isOk(), is(true));
    }

    @Test
    public void warnings() throws Exception {
        // missing text
        slack.methods(ValidToken).chatPostMessage(r -> r.blocks(asBlocks(
                section(s -> s.blockId("b").text(plainText(t -> t.text("Hi"))))
        )));
        slack.methods(ValidToken).chatPostMessage(r -> r.blocksAsString("[\n" +
                "  {\n" +
                "    \"type\": \"section\",\n" +
                "    \"text\": {\n" +
                "      \"type\": \"mrkdwn\",\n" +
                "      \"text\": \"This is a section block with a button.\"\n" +
                "    }\n" +
                "  }\n" +
                "]"));
        // missing fallback
        slack.methods(ValidToken).chatPostMessage(r -> r.attachments(asAttachments(
                Attachment.builder()
                        .text("Hi there!")
                        .color("#FF5733")
                        .build()
        )));
        slack.methods(ValidToken).chatPostMessage(r -> r.attachmentsAsString("[\n" +
                "  {\n" +
                "    \"color\": \"#FF5733\",\n" +
                "    \"blocks\": [\n" +
                "      {\n" +
                "        \"type\": \"section\",\n" +
                "        \"text\": {\n" +
                "          \"type\": \"mrkdwn\",\n" +
                "          \"text\": \"test*\"\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]"));
        slack.methods(ValidToken).chatPostMessage(r -> r.attachments(asAttachments(
                Attachment.builder()
                        .text("Hi there!")
                        .color("#FF5733")
                        .build()
        )).text("fallback-for-blocks"));
    }

    @Test
    public void noWarnings() throws Exception {
        // missing text
        slack.methods(ValidToken).chatPostMessage(r -> r.blocks(asBlocks(
                section(s -> s.blockId("b").text(plainText(t -> t.text("Hi"))))
        )).text("fallback"));
        slack.methods(ValidToken).chatPostMessage(r -> r.blocksAsString("[\n" +
                "  {\n" +
                "    \"type\": \"section\",\n" +
                "    \"text\": {\n" +
                "      \"type\": \"mrkdwn\",\n" +
                "      \"text\": \"This is a section block with a button.\"\n" +
                "    }\n" +
                "  }\n" +
                "]").text("fallback"));
        // missing fallback
        slack.methods(ValidToken).chatPostMessage(r -> r.attachments(asAttachments(
                Attachment.builder()
                        .fallback("fallback")
                        .text("Hi there!")
                        .color("#FF5733")
                        .build()
        )));
        slack.methods(ValidToken).chatPostMessage(r -> r.attachmentsAsString("[\n" +
                "  {\n" +
                "    \"color\": \"#FF5733\",\n" +
                "    \"fallback\": \"fallback\",\n" +
                "    \"blocks\": [\n" +
                "      {\n" +
                "        \"type\": \"section\",\n" +
                "        \"text\": {\n" +
                "          \"type\": \"mrkdwn\",\n" +
                "          \"text\": \"test*\"\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]"));
    }

}
