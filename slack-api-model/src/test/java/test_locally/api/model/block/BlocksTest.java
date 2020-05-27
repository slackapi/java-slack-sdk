package test_locally.api.model.block;

import com.slack.api.model.Message;
import com.slack.api.model.block.*;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.block.element.ChannelsSelectElement;
import com.slack.api.model.block.element.ConversationsSelectElement;
import com.slack.api.model.block.element.MultiConversationsSelectElement;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import java.util.Arrays;
import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.asSectionFields;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class BlocksTest {

    @Test
    public void testActions() {
        List<BlockElement> elements = asElements(button(r -> r.value("v")));
        {
            ActionsBlock block = actions(elements);
            assertThat(block, is(notNullValue()));
        }
        {
            ActionsBlock block = actions("block-id", elements);
            assertThat(block, is(notNullValue()));
        }
    }

    @Test
    public void testContext() {
        List<ContextBlockElement> elements = asContextElements(imageElement(r -> r.imageUrl("https://www.example.com/logo.png")));
        {
            ContextBlock block = context(elements);
            assertThat(block, is(notNullValue()));
        }
        {
            ContextBlock block = context("block-id", elements);
            assertThat(block, is(notNullValue()));
        }
    }

    @Test
    public void testDivider() {
        assertThat(divider(), is(notNullValue()));
        assertThat(divider("block-id"), is(notNullValue()));
    }

    @Test
    public void testFile() {
        assertThat(file(f -> f.blockId("block-id")), is(notNullValue()));
    }

    @Test
    public void testCall() {
        assertThat(call(f -> f.blockId("block-id").callId("R111")), is(notNullValue()));
    }

    @Test
    public void testImage() {
        assertThat(Blocks.image(i -> i.blockId("block-id").imageUrl("https://www.example.com/")), is(notNullValue()));
    }

    @Test
    public void testInput() {
        assertThat(input(i -> i.blockId("block-id").element(button(b -> b.value("v")))), is(notNullValue()));
    }

    @Test
    public void testRichText() {
        assertThat(richText(i -> i
                .blockId("block-id")
                .elements(asElements(button(b -> b.value("v"))))
        ), is(notNullValue()));
    }

    String richTextSkinTone = "{ \"blocks\": [\n" +
            "  {\n" +
            "    \"type\": \"rich_text\",\n" +
            "    \"block_id\": \"b123\",\n" +
            "    \"elements\": [\n" +
            "      {\n" +
            "        \"type\": \"rich_text_section\",\n" +
            "        \"elements\": [\n" +
            "          {\n" +
            "            \"type\": \"text\",\n" +
            "            \"text\": \"Hello\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"emoji\",\n" +
            "            \"name\": \"wave\",\n" +
            "            \"skin_tone\": 3\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"emoji\",\n" +
            "            \"name\": \"rocket\"\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "]}";

    @Test
    public void testRichTextSkinToneEmoji() {
        Message message = GsonFactory.createSnakeCase().fromJson(richTextSkinTone, Message.class);
        assertNotNull(message);
    }

    @Test
    public void testSection() {
        assertThat(section(s -> s.blockId("block-id").fields(asSectionFields(plainText("foo")))), is(notNullValue()));
    }

    @Test
    public void testCheckboxes() {
        assertThat(section(s -> s.blockId("block-id").accessory(checkboxes(c -> c.actionId("foo")))), is(notNullValue()));
        assertThat(input(i -> i.element(checkboxes(c -> c.actionId("foo")))), is(notNullValue()));
    }

    @Test
    public void testConversationsFilter() {
        String json = "{\"blocks\":[\n" +
                "  {\n" +
                "    \"type\": \"input\",\n" +
                "    \"element\": {\n" +
                "      \"type\": \"conversations_select\",\n" +
                "      \"placeholder\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Select a conversation\",\n" +
                "        \"emoji\": true\n" +
                "      },\n" +
                "      \"filter\": {\n" +
                "        \"include\": [\n" +
                "          \"public\",\n" +
                "          \"mpim\"\n" +
                "        ],\n" +
                "        \"exclude_bot_users\": true\n" +
                "      }\n" +
                "    },\n" +
                "    \"label\": {\n" +
                "      \"type\": \"plain_text\",\n" +
                "      \"text\": \"Choose the conversation to publish your result to:\",\n" +
                "      \"emoji\": true\n" +
                "    }\n" +
                "  }\n" +
                "]}";
        Message message = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertNotNull(message);
        assertEquals(1, message.getBlocks().size());
        InputBlock block = (InputBlock) message.getBlocks().get(0);
        ConversationsSelectElement element = (ConversationsSelectElement) block.getElement();
        assertEquals(Arrays.asList("public", "mpim"), element.getFilter().getInclude());
        assertTrue(element.getFilter().getExcludeBotUsers());
        assertNull(element.getFilter().getExcludeExternalSharedChannels());
    }

    @Test
    public void testConversationsFilter_multi() {
        String json = "{\"blocks\":[\n" +
                "  {\n" +
                "    \"type\": \"input\",\n" +
                "    \"element\": {\n" +
                "      \"type\": \"multi_conversations_select\",\n" +
                "      \"placeholder\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Select a conversation\",\n" +
                "        \"emoji\": true\n" +
                "      },\n" +
                "      \"filter\": {\n" +
                "        \"include\": [\n" +
                "          \"public\",\n" +
                "          \"mpim\"\n" +
                "        ],\n" +
                "        \"exclude_bot_users\": true\n" +
                "      }\n" +
                "    },\n" +
                "    \"label\": {\n" +
                "      \"type\": \"plain_text\",\n" +
                "      \"text\": \"Choose the conversation to publish your result to:\",\n" +
                "      \"emoji\": true\n" +
                "    }\n" +
                "  }\n" +
                "]}";
        Message message = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertNotNull(message);
        assertEquals(1, message.getBlocks().size());
        InputBlock block = (InputBlock) message.getBlocks().get(0);
        MultiConversationsSelectElement element = (MultiConversationsSelectElement) block.getElement();
        assertEquals(Arrays.asList("public", "mpim"), element.getFilter().getInclude());
        assertTrue(element.getFilter().getExcludeBotUsers());
        assertNull(element.getFilter().getExcludeExternalSharedChannels());
    }

    @Test
    public void testResponseUrlEnabled_conversations() {
        {
            String json = "{\"blocks\":[\n" +
                    "  {\n" +
                    "    \"type\": \"input\",\n" +
                    "    \"element\": {\n" +
                    "      \"type\": \"conversations_select\",\n" +
                    "      \"placeholder\": {\n" +
                    "        \"type\": \"plain_text\",\n" +
                    "        \"text\": \"Select a conversation\",\n" +
                    "        \"emoji\": true\n" +
                    "      },\n" +
                    "      \"response_url_enabled\": true\n" +
                    "    },\n" +
                    "    \"label\": {\n" +
                    "      \"type\": \"plain_text\",\n" +
                    "      \"text\": \"Choose the conversation to publish your result to:\",\n" +
                    "      \"emoji\": true\n" +
                    "    }\n" +
                    "  }\n" +
                    "]}";
            Message message = GsonFactory.createSnakeCase().fromJson(json, Message.class);
            assertNotNull(message);
            assertEquals(1, message.getBlocks().size());
            InputBlock block = (InputBlock) message.getBlocks().get(0);
            ConversationsSelectElement element = (ConversationsSelectElement) block.getElement();
            assertTrue(element.getResponseUrlEnabled());
        }

        {
            String json = "{\"blocks\":[\n" +
                    "  {\n" +
                    "    \"type\": \"input\",\n" +
                    "    \"element\": {\n" +
                    "      \"type\": \"conversations_select\",\n" +
                    "      \"placeholder\": {\n" +
                    "        \"type\": \"plain_text\",\n" +
                    "        \"text\": \"Select a conversation\",\n" +
                    "        \"emoji\": true\n" +
                    "      }\n" +
                    "    },\n" +
                    "    \"label\": {\n" +
                    "      \"type\": \"plain_text\",\n" +
                    "      \"text\": \"Choose the conversation to publish your result to:\",\n" +
                    "      \"emoji\": true\n" +
                    "    },\n" +
                    "    \"hint\": {\n" +
                    "      \"type\": \"plain_text\",\n" +
                    "      \"text\": \"Choose the conversation to publish your result to:\",\n" +
                    "      \"emoji\": true\n" +
                    "    }\n" +
                    "  }\n" +
                    "]}";
            Message message = GsonFactory.createSnakeCase().fromJson(json, Message.class);
            assertNotNull(message);
            assertEquals(1, message.getBlocks().size());
            InputBlock block = (InputBlock) message.getBlocks().get(0);
            ConversationsSelectElement element = (ConversationsSelectElement) block.getElement();
            assertNull(element.getResponseUrlEnabled());
        }
    }

    @Test
    public void testResponseUrlEnabled_channels() {
        {
            String json = "{\"blocks\":[\n" +
                    "  {\n" +
                    "    \"type\": \"input\",\n" +
                    "    \"element\": {\n" +
                    "      \"type\": \"channels_select\",\n" +
                    "      \"placeholder\": {\n" +
                    "        \"type\": \"plain_text\",\n" +
                    "        \"text\": \"Select a conversation\",\n" +
                    "        \"emoji\": true\n" +
                    "      },\n" +
                    "      \"response_url_enabled\": true\n" +
                    "    },\n" +
                    "    \"label\": {\n" +
                    "      \"type\": \"plain_text\",\n" +
                    "      \"text\": \"Choose the conversation to publish your result to:\",\n" +
                    "      \"emoji\": true\n" +
                    "    }\n" +
                    "  }\n" +
                    "]}";
            Message message = GsonFactory.createSnakeCase().fromJson(json, Message.class);
            assertNotNull(message);
            assertEquals(1, message.getBlocks().size());
            InputBlock block = (InputBlock) message.getBlocks().get(0);
            ChannelsSelectElement element = (ChannelsSelectElement) block.getElement();
            assertTrue(element.getResponseUrlEnabled());
        }

        {
            String json = "{\"blocks\":[\n" +
                    "  {\n" +
                    "    \"type\": \"input\",\n" +
                    "    \"element\": {\n" +
                    "      \"type\": \"channels_select\",\n" +
                    "      \"placeholder\": {\n" +
                    "        \"type\": \"plain_text\",\n" +
                    "        \"text\": \"Select a conversation\",\n" +
                    "        \"emoji\": true\n" +
                    "      }\n" +
                    "    },\n" +
                    "    \"label\": {\n" +
                    "      \"type\": \"plain_text\",\n" +
                    "      \"text\": \"Choose the conversation to publish your result to:\",\n" +
                    "      \"emoji\": true\n" +
                    "    }\n" +
                    "  }\n" +
                    "]}";
            Message message = GsonFactory.createSnakeCase().fromJson(json, Message.class);
            assertNotNull(message);
            assertEquals(1, message.getBlocks().size());
            InputBlock block = (InputBlock) message.getBlocks().get(0);
            ChannelsSelectElement element = (ChannelsSelectElement) block.getElement();
            assertNull(element.getResponseUrlEnabled());
        }
    }

}