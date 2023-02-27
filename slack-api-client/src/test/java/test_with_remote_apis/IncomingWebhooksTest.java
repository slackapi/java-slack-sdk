package test_with_remote_apis;

import com.slack.api.Slack;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import com.slack.api.model.Message;
import com.slack.api.model.block.ActionsBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.ButtonElement;
import com.slack.api.webhook.Payload;
import com.slack.api.webhook.WebhookResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Slf4j
public class IncomingWebhooksTest {

    // String url = "https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX";
    String url = System.getenv(Constants.SLACK_SDK_TEST_INCOMING_WEBHOOK_URL);
    String channel = System.getenv(Constants.SLACK_SDK_TEST_INCOMING_WEBHOOK_CHANNEL_NAME);
    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
    String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Before
    public void setup() {
        if (url == null) {
            throw new IllegalStateException("Environment variable SLACK_WEBHOOK_TEST_URL must be defined");
        }
    }

    @Test
    public void testWithSimplePayload() throws IOException {
        Payload payload = Payload.builder()
                .channel("#random") // still work if the webhook in part of a custom integration
                .iconEmoji(":smile_cat:") // still work if the webhook in part of a custom integration
                .username("Java Slack SDK") // still work if the webhook in part of a custom integration
                .text("Hello World!")
                .attachments(new ArrayList<>())
                .build();

        Attachment attachment = Attachment.builder()
                .text("This is an *attachment*.")
                .authorName("Smiling Imp")
                .color("#36a64f")
                .fallback("Required plain-text summary of the attachment.")
                .title("Slack API Documentation")
                .titleLink("https://api.slack.com/")
                .footer("footer")
                .fields(new ArrayList<>())
                .mrkdwnIn(new ArrayList<>())
                .build();

        attachment.getMrkdwnIn().add("text");

        {
            Field field = Field.builder()
                    .title("Long Title")
                    .value("Long Value........................................................")
                    .valueShortEnough(false).build();
            attachment.getFields().add(field);
            attachment.getFields().add(field);
        }
        {
            Field field = Field.builder()
                    .title("Short Title")
                    .value("Short Value")
                    .valueShortEnough(true).build();
            attachment.getFields().add(field);
            attachment.getFields().add(field);
        }
        payload.getAttachments().add(attachment);

        WebhookResponse response = slack.send(url, payload);
        log.info(response.toString());

        assertThat(response.getBody(), is("ok"));
        assertThat(response.getCode(), is(200));
        // The behavior has been change (Jun 2019)
        // assertThat(response.getMessage(), is("OK"));
    }

    @Test
    public void testWithMessageMetadata() throws IOException {
        Map<String, Object> eventPayload = new HashMap<>();
        eventPayload.put("something", "great");
        Message.Metadata metadata = Message.Metadata.builder().eventType("foo").eventPayload(eventPayload).build();
        Payload payload = Payload.builder()
                .text("Hello World!")
                .metadata(metadata)
                .build();
        WebhookResponse response = slack.send(url, payload);
        log.info(response.toString());

        assertThat(response.getBody(), is("ok"));
        assertThat(response.getCode(), is(200));
    }

    @Test
    public void testWithBlockKitPayload() throws IOException {
        Payload payload = Payload.builder()
                .channel("#random") // still work if the webhook in part of a custom integration
                .iconEmoji(":smile_cat:") // still work if the webhook in part of a custom integration
                .username("Java Slack SDK") // still work if the webhook in part of a custom integration
                .text("Hello World!")
                .blocks(new ArrayList<>())
                .build();

        ButtonElement button = ButtonElement.builder()
                .text(PlainTextObject.builder().emoji(true).text("Farmhouse").build())
                .value("click_me_123")
                .build();

        LayoutBlock block = ActionsBlock.builder().elements(Arrays.asList(button)).build();

        payload.getBlocks().add(block);

        WebhookResponse response = slack.send(url, payload);
        log.info(response.toString());

        assertThat(response.getBody(), is("ok"));
        assertThat(response.getCode(), is(200));
        // The behavior has been change (Jun 2019)
        // assertThat(response.getMessage(), is("OK"));
    }

    @Test
    public void testWithRawBodyContainingAttachments() throws IOException {
        String payload = "{\n" +
                "    \"text\": \"Robert DeSoto added a new task\",\n" +
                "    \"attachments\": [\n" +
                "        {\n" +
                "            \"fallback\": \"Plan a vacation\",\n" +
                "            \"author_name\": \"Owner: rdesoto\",\n" +
                "            \"title\": \"Plan a vacation\",\n" +
                "            \"text\": \"I've been working too hard, it's time for a break.\",\n" +
                "            \"actions\": [\n" +
                "                {\n" +
                "                    \"name\": \"action\",\n" +
                "                    \"type\": \"button\",\n" +
                "                    \"text\": \"Complete this task\",\n" +
                "                    \"style\": \"\",\n" +
                "                    \"value\": \"complete\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"tags_list\",\n" +
                "                    \"type\": \"select\",\n" +
                "                    \"text\": \"Add a tag...\",\n" +
                "                    \"data_source\": \"static\",\n" +
                "                    \"options\": [\n" +
                "                        {\n" +
                "                            \"text\": \"Launch Blocking\",\n" +
                "                            \"value\": \"launch-blocking\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"text\": \"Enhancement\",\n" +
                "                            \"value\": \"enhancement\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"text\": \"Bug\",\n" +
                "                            \"value\": \"bug\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        WebhookResponse response = slack.send(url, payload);
        log.info(response.toString());

        assertThat(response.getBody(), is("ok"));
        assertThat(response.getCode(), is(200));
    }

    @Test
    public void testWithRawBodyContainingBlocks() throws IOException {
        String payload = "{\n" +
                "    \"blocks\": [\n" +
                "        {\n" +
                "            \"type\": \"section\",\n" +
                "            \"text\": {\n" +
                "                \"type\": \"mrkdwn\",\n" +
                "                \"text\": \"You have a new request:\\n*<fakeLink.toEmployeeProfile.com|Fred Enriquez - New device request>*\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"section\",\n" +
                "            \"fields\": [\n" +
                "                {\n" +
                "                    \"type\": \"mrkdwn\",\n" +
                "                    \"text\": \"*Type:*\\nComputer (laptop)\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": \"mrkdwn\",\n" +
                "                    \"text\": \"*When:*\\nSubmitted Aut 10\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": \"mrkdwn\",\n" +
                "                    \"text\": \"*Last Update:*\\nMar 10, 2015 (3 years, 5 months)\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": \"mrkdwn\",\n" +
                "                    \"text\": \"*Reason:*\\nAll vowel keys aren't working.\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": \"mrkdwn\",\n" +
                "                    \"text\": \"*Specs:*\\n\\\"Cheetah Pro 15\\\" - Fast, really fast\\\"\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"actions\",\n" +
                "            \"elements\": [\n" +
                "                {\n" +
                "                    \"type\": \"button\",\n" +
                "                    \"text\": {\n" +
                "                        \"type\": \"plain_text\",\n" +
                "                        \"emoji\": true,\n" +
                "                        \"text\": \"Approve\"\n" +
                "                    },\n" +
                "                    \"style\": \"primary\",\n" +
                "                    \"value\": \"click_me_123\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": \"button\",\n" +
                "                    \"text\": {\n" +
                "                        \"type\": \"plain_text\",\n" +
                "                        \"emoji\": true,\n" +
                "                        \"text\": \"Deny\"\n" +
                "                    },\n" +
                "                    \"style\": \"danger\",\n" +
                "                    \"value\": \"click_me_123\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        WebhookResponse response = slack.send(url, payload);
        log.info(response.toString());

        assertThat(response.getBody(), is("ok"));
        assertThat(response.getCode(), is(200));
    }

    @Test
    public void testWithThreads_bot() throws Exception {
        ChatPostMessageResponse chatPostMessageResponse = slack.methods().chatPostMessage(r ->
                r.token(botToken).channel(channel).text("[thread] Hello"));
        assertThat(chatPostMessageResponse.getError(), is(nullValue()));

        Payload payload = Payload.builder()
                .threadTs(chatPostMessageResponse.getMessage().getTs())
                .text("Reply via Incoming Webhook!").build();

        WebhookResponse response = slack.send(url, payload);
        log.info(response.toString());

        assertThat(response.getBody(), is("ok"));
        assertThat(response.getCode(), is(200));
    }

    @Test
    public void testWithThreads_user() throws Exception {
        ChatPostMessageResponse chatPostMessageResponse = slack.methods().chatPostMessage(r ->
                r.token(userToken).channel(channel).text("[thread] Hello"));
        assertThat(chatPostMessageResponse.getError(), is(nullValue()));

        Payload payload = Payload.builder()
                .threadTs(chatPostMessageResponse.getMessage().getTs())
                .text("Reply via Incoming Webhook!").build();

        WebhookResponse response = slack.send(url, payload);
        log.info(response.toString());

        assertThat(response.getBody(), is("ok"));
        assertThat(response.getCode(), is(200));
    }

    @Test
    public void testInvalidBlocksError() throws Exception {
        List<LayoutBlock> blocks = Arrays.asList(SectionBlock.builder().blockId("B").build());
        Payload payload = Payload.builder().blocks(blocks).text("Hi there!").build();
        WebhookResponse response = slack.send(url, payload);
        assertThat(response.getBody(), is("invalid_blocks"));
        assertThat(response.getCode(), is(400));
    }

    @Test
    public void headers() throws IOException {
        WebhookResponse response = slack.send(url, Payload.builder().text("Hello world!").build());
        assertThat(response.getBody(), is("ok"));
        assertThat(response.getCode(), is(200));
        assertThat(response.getHeaders().size(),is(greaterThan(0)));
    }

    @Test
    public void issue_1122_invalid_url() throws Exception {
        String invalidUrl = "https://hooks.slack.com/services/invalid-url";
        WebhookResponse response = slack.send(invalidUrl, Payload.builder().text("Hello world!").build());
        assertThat(response.getCode(), is(302));
    }
}
