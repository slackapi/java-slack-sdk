package test_locally.api.model.block;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.slack.api.model.Message;
import com.slack.api.model.block.*;
import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.element.*;
import com.slack.api.model.view.View;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import java.util.Arrays;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.asSectionFields;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.checkboxes;
import static com.slack.api.model.block.element.BlockElements.conversationsSelect;
import static com.slack.api.model.view.Views.view;
import static com.slack.api.model.view.Views.viewSubmit;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class BlockKitTest {

    @Test
    public void parseInputOnes() {
        String json = "{blocks: [{\n" +
                "  \"type\": \"input\",\n" +
                "  \"block_id\": \"input123\",\n" +
                "    \"label\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Label of input\"\n" +
                "    },\n" +
                "    \"hint\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Hint of input\"\n" +
                "    },\n" +
                "  \"element\": {\n" +
                "    \"type\": \"plain_text_input\",\n" +
                "    \"action_id\": \"plain_input\",\n" +
                "    \"placeholder\": {\n" +
                "      \"type\": \"plain_text\",\n" +
                "      \"text\": \"Enter some plain text\"\n" +
                "    }\n" +
                "  }\n" +
                "}]}";
        Message message = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertThat(message, is(notNullValue()));
        InputBlock inputBlock = (InputBlock) message.getBlocks().get(0);
        assertThat(inputBlock.getLabel(), is(notNullValue()));
        assertThat(inputBlock.getElement(), is(notNullValue()));
        assertThat(inputBlock.getHint(), is(notNullValue()));
    }

    @Test
    public void parseMultiSelectOnes() {
        String blocks = "[\n" +
                "    {\n" +
                "        \"type\": \"section\",\n" +
                "        \"block_id\": \"section678\",\n" +
                "        \"text\": {\n" +
                "            \"type\": \"mrkdwn\",\n" +
                "            \"text\": \"Pick items from the list\"\n" +
                "        },\n" +
                "        \"accessory\": {\n" +
                "            \"action_id\": \"text1234\",\n" +
                "            \"type\": \"multi_static_select\",\n" +
                "            \"placeholder\": {\n" +
                "                \"type\": \"plain_text\",\n" +
                "                \"text\": \"Select items\"\n" +
                "            },\n" +
                "            \"options\": [\n" +
                "                {\n" +
                "                    \"text\": {\n" +
                "                        \"type\": \"plain_text\",\n" +
                "                        \"text\": \"*this is plain_text text*\"\n" +
                "                    },\n" +
                "                    \"value\": \"value-0\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"text\": {\n" +
                "                        \"type\": \"plain_text\",\n" +
                "                        \"text\": \"*this is plain_text text*\"\n" +
                "                    },\n" +
                "                    \"value\": \"value-1\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"text\": {\n" +
                "                        \"type\": \"plain_text\",\n" +
                "                        \"text\": \"*this is plain_text text*\"\n" +
                "                    },\n" +
                "                    \"value\": \"value-2\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"type\": \"section\",\n" +
                "        \"block_id\": \"section678\",\n" +
                "        \"text\": {\n" +
                "            \"type\": \"mrkdwn\",\n" +
                "            \"text\": \"Pick items from the list\"\n" +
                "        },\n" +
                "        \"accessory\": {\n" +
                "            \"action_id\": \"text1234\",\n" +
                "            \"type\": \"multi_external_select\",\n" +
                "            \"placeholder\": {\n" +
                "                \"type\": \"plain_text\",\n" +
                "                \"text\": \"Select items\"\n" +
                "      },\n" +
                "      \"min_query_length\": 3\n" +
                "        }\n" +
                "    }," +
                "    {\n" +
                "        \"type\": \"section\",\n" +
                "        \"block_id\": \"section678\",\n" +
                "        \"text\": {\n" +
                "            \"type\": \"mrkdwn\",\n" +
                "            \"text\": \"Pick users from the list\"\n" +
                "        },\n" +
                "        \"accessory\": {\n" +
                "            \"action_id\": \"text1234\",\n" +
                "            \"type\": \"multi_users_select\",\n" +
                "            \"placeholder\": {\n" +
                "                \"type\": \"plain_text\",\n" +
                "                \"text\": \"Select users\"\n" +
                "      }\n" +
                "        }\n" +
                "    }," +
                "    {\n" +
                "        \"type\": \"section\",\n" +
                "        \"block_id\": \"section678\",\n" +
                "        \"text\": {\n" +
                "            \"type\": \"mrkdwn\",\n" +
                "            \"text\": \"Pick conversations from the list\"\n" +
                "        },\n" +
                "        \"accessory\": {\n" +
                "            \"action_id\": \"text1234\",\n" +
                "            \"type\": \"multi_conversations_select\",\n" +
                "            \"placeholder\": {\n" +
                "                \"type\": \"plain_text\",\n" +
                "                \"text\": \"Select conversations\"\n" +
                "      }\n" +
                "        }\n" +
                "    }," +
                "    {\n" +
                "        \"type\": \"section\",\n" +
                "        \"block_id\": \"section678\",\n" +
                "        \"text\": {\n" +
                "            \"type\": \"mrkdwn\",\n" +
                "            \"text\": \"Pick channels from the list\"\n" +
                "        },\n" +
                "        \"accessory\": {\n" +
                "            \"action_id\": \"text1234\",\n" +
                "            \"type\": \"multi_channels_select\",\n" +
                "            \"placeholder\": {\n" +
                "                \"type\": \"plain_text\",\n" +
                "                \"text\": \"Select channels\"\n" +
                "      }\n" +
                "        }\n" +
                "    }" +
                "]";
        String json = "{blocks: " + blocks + "}";
        Message message = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertThat(message, is(notNullValue()));
        assertThat(message.getBlocks().size(), is(5));
    }

    @Test
    public void parseRichTextOnes() {
        // https://api.slack.com/changelog/2019-09-what-they-see-is-what-you-get-and-more-and-less
        String json = "{\n" +
                "  \"client_msg_id\": \"70c82df9-9db9-48b0-bf4e-9c43db3ed097\",\n" +
                "  \"type\": \"message\",\n" +
                "  \"text\": \"This is a *rich text *message that uses _italics and _~strikethrough ~and looks :sparkles: _*fabulous*_ :sparkles:\",\n" +
                "  \"user\": \"U0JD3BPNC\",\n" +
                "  \"ts\": \"1565629075.001000\",\n" +
                "  \"team\": \"T0JD3BPMW\",\n" +
                "  \"blocks\": [\n" +
                "    {\n" +
                "      \"type\": \"rich_text\",\n" +
                "      \"block_id\": \"hUBz\",\n" +
                "      \"elements\": [\n" +
                "        {\n" +
                "          \"type\": \"rich_text_section\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"This is a \"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"rich text \",\n" +
                "              \"style\": {\n" +
                "                \"bold\": true\n" +
                "              }\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"message that uses \"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"italics and \",\n" +
                "              \"style\": {\n" +
                "                \"italic\": true\n" +
                "              }\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"strikethrough \",\n" +
                "              \"style\": {\n" +
                "                \"strike\": true\n" +
                "              }\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"and looks \"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"emoji\",\n" +
                "              \"name\": \"sparkles\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \" \"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"fabulous\",\n" +
                "              \"style\": {\n" +
                "                \"bold\": true,\n" +
                "                \"italic\": true\n" +
                "              }\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \" \"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"emoji\",\n" +
                "              \"name\": \"sparkles\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        Message view = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertThat(view.getBlocks().size(), is(1));

        RichTextBlock richTextBlock = (RichTextBlock) view.getBlocks().get(0);
        assertThat(richTextBlock.getElements().size(), is(1));

        RichTextSectionElement richTextSection = (RichTextSectionElement) richTextBlock.getElements().get(0);
        assertThat(richTextSection.getElements().size(), is(11));

        RichTextSectionElement.Text text = (RichTextSectionElement.Text) richTextSection.getElements().get(1);
        assertThat(text.getType(), is("text"));
        assertThat(text.getText(), is("rich text "));
        assertThat(text.getStyle().isBold(), is(true));
        assertThat(text.getStyle().isItalic(), is(false));
        assertThat(text.getStyle().isStrike(), is(false));
    }

    @Test
    public void parseRichTextOnes2() {
        // https://api.slack.com/changelog/2019-09-what-they-see-is-what-you-get-and-more-and-less
        String json = "{\n" +
                "  \"client_msg_id\": \"0e7bdef4-27a9-4bfa-bf16-c65683ff382b\",\n" +
                "  \"type\": \"message\",\n" +
                "  \"text\": \"dummy\"," +
                "  \"user\": \"W12345678\",\n" +
                "  \"ts\": \"1573737632.000800\",\n" +
                "  \"team\": \"T12345678\",\n" +
                "  \"edited\": {\n" +
                "    \"user\": \"W12345678\",\n" +
                "    \"ts\": \"1573737939.000000\"\n" +
                "  },\n" +
                "  \"blocks\": [\n" +
                "    {\n" +
                "      \"type\": \"rich_text\",\n" +
                "      \"block_id\": \"BMpCU\",\n" +
                "      \"elements\": [\n" +
                "        {\n" +
                "          \"type\": \"rich_text_section\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"broadcast\",\n" +
                "              \"range\": \"channel\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \" test a\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"aaa\",\n" +
                "              \"style\": {\n" +
                "                \"strike\": true\n" +
                "              }\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"\\n\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"channel\",\n" +
                "              \"channel_id\": \"C123456678\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"\\ntest\\n\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"link\",\n" +
                "              \"url\": \"http://www.example.com\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"\\n\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"user\",\n" +
                "              \"user_id\": \"W12345678\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Message view = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertThat(view.getBlocks().size(), is(1));
        RichTextBlock block = (RichTextBlock) view.getBlocks().get(0);
        assertThat(block.getElements().size(), is(1));
    }

    @Test
    public void parseRichTextOnes3() {
        // https://api.slack.com/changelog/2019-09-what-they-see-is-what-you-get-and-more-and-less
        String json = "{\n" +
                "  \"client_msg_id\": \"0e7bdef4-27a9-4bfa-bf16-c65683ff382b\",\n" +
                "  \"type\": \"message\",\n" +
                "  \"text\": \"@channel test a~aa~\\n#platform-feedback\\ntest\\nhttp://www.example.com\\n@kaz\\n\\n• test\\n• aaaa\\n\\n\\n1. foo\\n2. bar\\n3. baz\\n\\n\\n> something important\\n\\n\\n/`/`/`package example\\n\\nclass Foo/`/`/`\\n\\n*bold* something _italic_\",\n" +
                "  \"user\": \"W12345678\",\n" +
                "  \"ts\": \"1573737632.000800\",\n" +
                "  \"team\": \"T5J4Q04QG\",\n" +
                "  \"edited\": {\n" +
                "    \"user\": \"W12345678\",\n" +
                "    \"ts\": \"1573776943.000000\"\n" +
                "  },\n" +
                "  \"blocks\": [\n" +
                "    {\n" +
                "      \"type\": \"rich_text\",\n" +
                "      \"block_id\": \"nZTe+\",\n" +
                "      \"elements\": [\n" +
                "        {\n" +
                "          \"type\": \"rich_text_section\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"user\",\n" +
                "              \"user_id\": \"W12345678\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"\\n\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"rich_text_list\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"rich_text_section\",\n" +
                "              \"elements\": [\n" +
                "                {\n" +
                "                  \"type\": \"text\",\n" +
                "                  \"text\": \"test\"\n" +
                "                }\n" +
                "              ]\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"rich_text_section\",\n" +
                "              \"elements\": [\n" +
                "                {\n" +
                "                  \"type\": \"text\",\n" +
                "                  \"text\": \"aaaa\"\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ],\n" +
                "          \"style\": \"bullet\",\n" +
                "          \"indent\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"rich_text_section\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"\\n\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"rich_text_list\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"rich_text_section\",\n" +
                "              \"elements\": [\n" +
                "                {\n" +
                "                  \"type\": \"text\",\n" +
                "                  \"text\": \"foo\"\n" +
                "                }\n" +
                "              ]\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"rich_text_section\",\n" +
                "              \"elements\": [\n" +
                "                {\n" +
                "                  \"type\": \"text\",\n" +
                "                  \"text\": \"bar\"\n" +
                "                }\n" +
                "              ]\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"rich_text_section\",\n" +
                "              \"elements\": [\n" +
                "                {\n" +
                "                  \"type\": \"text\",\n" +
                "                  \"text\": \"baz\"\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ],\n" +
                "          \"style\": \"ordered\",\n" +
                "          \"indent\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"rich_text_quote\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"something important\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"rich_text_preformatted\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"package example\\n\\nclass Foo\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"rich_text_section\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"\\n\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"bold\",\n" +
                "              \"style\": {\n" +
                "                \"bold\": true\n" +
                "              }\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \" something \"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"italic\",\n" +
                "              \"style\": {\n" +
                "                \"italic\": true\n" +
                "              }\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Message view = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertThat(view.getBlocks().size(), is(1));
        RichTextBlock block = (RichTextBlock) view.getBlocks().get(0);
        assertThat(block.getElements().size(), is(7));
    }

    @Test
    public void parseRichTextOnes4() {
        String json = "{\n" +
                "  \"blocks\": [\n" +
                "    {\n" +
                "      \"type\": \"rich_text\",\n" +
                "      \"block_id\": \"UXJk\",\n" +
                "      \"elements\": [\n" +
                "        {\n" +
                "          \"type\": \"rich_text_list\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"rich_text_section\",\n" +
                "              \"elements\": [\n" +
                "                {\n" +
                "                  \"type\": \"text\",\n" +
                "                  \"text\": \"1\"\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ],\n" +
                "          \"style\": \"bullet\",\n" +
                "          \"indent\": 0,\n" +
                "          \"border\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"rich_text_list\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"rich_text_section\",\n" +
                "              \"elements\": [\n" +
                "                {\n" +
                "                  \"type\": \"text\",\n" +
                "                  \"text\": \"11\"\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ],\n" +
                "          \"style\": \"bullet\",\n" +
                "          \"indent\": 1,\n" +
                "          \"border\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"rich_text_list\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"rich_text_section\",\n" +
                "              \"elements\": [\n" +
                "                {\n" +
                "                  \"type\": \"text\",\n" +
                "                  \"text\": \"111\"\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ],\n" +
                "          \"style\": \"bullet\",\n" +
                "          \"indent\": 2,\n" +
                "          \"border\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"rich_text_list\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"rich_text_section\",\n" +
                "              \"elements\": [\n" +
                "                {\n" +
                "                  \"type\": \"text\",\n" +
                "                  \"text\": \"1\"\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ],\n" +
                "          \"style\": \"ordered\",\n" +
                "          \"indent\": 0,\n" +
                "          \"border\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"rich_text_list\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"rich_text_section\",\n" +
                "              \"elements\": [\n" +
                "                {\n" +
                "                  \"type\": \"text\",\n" +
                "                  \"text\": \"11\"\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ],\n" +
                "          \"style\": \"ordered\",\n" +
                "          \"indent\": 1,\n" +
                "          \"border\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"rich_text_list\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"rich_text_section\",\n" +
                "              \"elements\": [\n" +
                "                {\n" +
                "                  \"type\": \"text\",\n" +
                "                  \"text\": \"111\"\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ],\n" +
                "          \"style\": \"ordered\",\n" +
                "          \"indent\": 2,\n" +
                "          \"border\": 0\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // verify if Gson can parse the JSON data
        Message view = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertThat(view.getBlocks().size(), is(1));
    }

    @Test
    public void parseRadioButtons() {
        String json = "{" +
                "  \"blocks\": [\n" +
                "    {\n" +
                "      \"type\": \"section\",\n" +
                "      \"text\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Check out these rad radio buttons\"\n" +
                "      },\n" +
                "      \"accessory\": {\n" +
                "        \"type\": \"radio_buttons\",\n" +
                "        \"action_id\": \"this_is_an_action_id\",\n" +
                "        \"initial_option\": {\n" +
                "          \"value\": \"A1\",\n" +
                "          \"text\": {\n" +
                "            \"type\": \"plain_text\",\n" +
                "            \"text\": \"Radio 1\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"options\": [\n" +
                "          {\n" +
                "            \"value\": \"A1\",\n" +
                "            \"text\": {\n" +
                "              \"type\": \"plain_text\",\n" +
                "              \"text\": \"Radio 1\"\n" +
                "            }\n" +
                "          },\n" +
                "          {\n" +
                "            \"value\": \"A2\",\n" +
                "            \"text\": {\n" +
                "              \"type\": \"plain_text\",\n" +
                "              \"text\": \"Radio 2\"\n" +
                "            }\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        Message message = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertThat(message, is(notNullValue()));
        SectionBlock block = (SectionBlock) message.getBlocks().get(0);
        RadioButtonsElement radioButtons = (RadioButtonsElement) block.getAccessory();
        assertThat(radioButtons.getActionId(), is("this_is_an_action_id"));
    }

    @Test
    public void rich_text_color_code() {
        String json = "{\n" +
                "  \"blocks\": [\n" +
                "    {\n" +
                "      \"type\": \"rich_text\",\n" +
                "      \"block_id\": \"d77H\",\n" +
                "      \"elements\": [\n" +
                "        {\n" +
                "          \"type\": \"rich_text_section\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"Hello\\n\\n\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"color\",\n" +
                "              \"value\": \"#FFFFFF\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"\\n\\n\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"emoji\",\n" +
                "              \"name\": \"wave\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        Message message = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertThat(message, is(notNullValue()));
        RichTextBlock b = (RichTextBlock) message.getBlocks().get(0);
        RichTextSectionElement elm = (RichTextSectionElement) b.getElements().get(0);
        RichTextSectionElement.Color c = (RichTextSectionElement.Color) elm.getElements().get(1);
        assertThat(c.getType(), is(RichTextSectionElement.Color.TYPE));
        assertThat(c.getValue(), is("#FFFFFF"));

    }

    String unknownRichElementJson = "{\n" +
            "  \"blocks\": [\n" +
            "    {\n" +
            "      \"type\": \"rich_text\",\n" +
            "      \"block_id\": \"d77H\",\n" +
            "      \"elements\": [\n" +
            "        {\n" +
            "          \"type\": \"rich_text_section\",\n" +
            "          \"elements\": [\n" +
            "            {\n" +
            "              \"type\": \"text\",\n" +
            "              \"text\": \"Hello\\n\\n\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"type\": \"unknown_type\"\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void rich_text_default_behavior_with_unknown() {
        Message message = GsonFactory.createSnakeCase().fromJson(unknownRichElementJson, Message.class);
        assertThat(message, is(notNullValue()));
        RichTextBlock b = (RichTextBlock) message.getBlocks().get(0);
        RichTextSectionElement elm = (RichTextSectionElement) b.getElements().get(0);
        RichTextUnknownElement c = (RichTextUnknownElement) elm.getElements().get(1);
        assertThat(c.getType(), is("unknown_type"));
    }

    @Test
    public void rich_text_fail_on_unknown() {
        try {
            GsonFactory.createSnakeCaseWithoutUnknownPropertyDetection(true)
                    .fromJson(unknownRichElementJson, Message.class);
            fail("throwing JsonParseException is expected here");
        } catch (JsonParseException e) {
            assertThat(e.getMessage(), is("Unknown RichTextSectionElement type: unknown_type"));
        }
    }

    @Test
    public void richText_emoji_with_style() {
        String json = "{\n" +
                "  \"blocks\": [\n" +
                "    {\n" +
                "      \"type\": \"rich_text\",\n" +
                "      \"block_id\": \"UiW\",\n" +
                "      \"elements\": [\n" +
                "        {\n" +
                "          \"type\": \"rich_text_section\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \"This is a text part.\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"emoji\",\n" +
                "              \"name\": \"house\",\n" +
                "              \"style\": {\n" +
                "                \"bold\": true\n," +
                "                \"strike\": true" +
                "              }\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"link\",\n" +
                "              \"url\": \"https://medium.com/slack-developer-blog\",\n" +
                "              \"text\": \"Slack Platform Blog\",\n" +
                "              \"style\": {\n" +
                "                \"bold\": true\n," +
                "                \"strike\": true" +
                "              }\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        Message message = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertThat(message, is(notNullValue()));
    }

    String unknownBlocksJson = "{blocks: [{\n" +
            "  \"type\": \"unknown_block\",\n" +
            "  \"block_id\": \"input123\",\n" +
            "    \"label\": {\n" +
            "        \"type\": \"plain_text\",\n" +
            "        \"text\": \"Label of input\"\n" +
            "    },\n" +
            "  \"element\": {\n" +
            "    \"type\": \"unknown_element\",\n" +
            "    \"action_id\": \"plain_input\",\n" +
            "    \"placeholder\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Enter some plain text\"\n" +
            "    }\n" +
            "  }\n" +
            "}]}";

    @Test
    public void parse_unknown_blocks() {
        Message message = GsonFactory.createSnakeCaseWithoutUnknownPropertyDetection(false)
                .fromJson(unknownBlocksJson, Message.class);
        assertThat(message, is(notNullValue()));
    }

    @Test
    public void parse_fail_on_unknown_blocks() {
        try {
            GsonFactory.createSnakeCaseWithoutUnknownPropertyDetection(true)
                    .fromJson(unknownBlocksJson, Message.class);
        } catch (JsonParseException e) {
            assertThat(e.getMessage(), is("Unsupported layout block type: unknown_block"));
        }
    }

    String unknownBlockElementsJson = "{blocks: [{\n" +
            "  \"type\": \"input\",\n" +
            "  \"block_id\": \"input123\",\n" +
            "    \"label\": {\n" +
            "        \"type\": \"plain_text\",\n" +
            "        \"text\": \"Label of input\"\n" +
            "    },\n" +
            "  \"element\": {\n" +
            "    \"type\": \"unknown_element\",\n" +
            "    \"action_id\": \"plain_input\",\n" +
            "    \"placeholder\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Enter some plain text\"\n" +
            "    }\n" +
            "  }\n" +
            "}]}";

    @Test
    public void parse_unknown_block_elements() {
        Message message = GsonFactory.createSnakeCaseWithoutUnknownPropertyDetection(false)
                .fromJson(unknownBlockElementsJson, Message.class);
        assertThat(message, is(notNullValue()));
    }

    @Test
    public void parse_fail_on_unknown_block_elements() {
        try {
            GsonFactory.createSnakeCaseWithoutUnknownPropertyDetection(true)
                    .fromJson(unknownBlockElementsJson, Message.class);
            fail();
        } catch (JsonParseException e) {
            assertThat(e.getMessage(), is("Unknown block element type: unknown_element"));
        }
    }

    String unknownContextBlockElementsJson = "{blocks: [{\n" +
            "  \"type\": \"context\",\n" +
            "  \"elements\": [\n" +
            "    {\n" +
            "      \"type\": \"image\",\n" +
            "      \"image_url\": \"https://image.freepik.com/free-photo/red-drawing-pin_1156-445.jpg\",\n" +
            "      \"alt_text\": \"images\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"unknown_element\",\n" +
            "      \"text\": \"something\"\n" +
            "    }\n" +
            "  ]\n" +
            "}]}";

    @Test
    public void parse_unknown_context_block_elements() {
        Message message = GsonFactory.createSnakeCaseWithoutUnknownPropertyDetection(false)
                .fromJson(unknownContextBlockElementsJson, Message.class);
        assertThat(message, is(notNullValue()));
    }

    @Test
    public void parse_fail_on_unknown_context_block_elements() {
        try {
            GsonFactory.createSnakeCaseWithoutUnknownPropertyDetection(true)
                    .fromJson(unknownContextBlockElementsJson, Message.class);
            fail();
        } catch (JsonParseException e) {
            assertThat(e.getMessage(), is("Unknown context block element type: unknown_element"));
        }
    }

    String unknownTextObjectsJson = "{blocks: [{\n" +
            "  \"type\": \"section\",\n" +
            "  \"text\": {\n" +
            "    \"type\": \"unknown_text\",\n" +
            "    \"text\": \"This is a section block with a button.\"\n" +
            "  },\n" +
            "  \"accessory\": {\n" +
            "    \"type\": \"button\",\n" +
            "    \"text\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Click Me\"\n" +
            "    },\n" +
            "    \"value\": \"click_me_123\",\n" +
            "    \"action_id\": \"button\"\n" +
            "  }\n" +
            "}]}";

    @Test
    public void parse_unknown_text_objects() {
        Message message = GsonFactory.createSnakeCaseWithoutUnknownPropertyDetection(false)
                .fromJson(unknownTextObjectsJson, Message.class);
        assertThat(message, is(notNullValue()));
    }

    @Test
    public void parse_fail_on_unknown_text_objects() {
        try {
            GsonFactory.createSnakeCaseWithoutUnknownPropertyDetection(true)
                    .fromJson(unknownTextObjectsJson, Message.class);
            fail();
        } catch (JsonParseException e) {
            assertThat(e.getMessage(), is("Unknown text object type: unknown_text"));
        }
    }

    String unknownTextObjectsInInputBlocksJson = "{blocks: [{\n" +
            "  \"type\": \"input\",\n" +
            "  \"block_id\": \"input123\",\n" +
            "  \"label\": {\n" +
            "    \"type\": \"plain_text2\",\n" +
            "    \"text\": \"Label of input\"\n" +
            "  },\n" +
            "  \"element\": {\n" +
            "    \"type\": \"plain_text_input\",\n" +
            "    \"action_id\": \"plain_input\",\n" +
            "    \"placeholder\": {\n" +
            "      \"type\": \"plain_text2\",\n" +
            "      \"text\": \"Enter some plain text\"\n" +
            "    }\n" +
            "  }\n" +
            "}]}";

    @Test
    public void parse_unknown_text_objects_input() {
        Message message = GsonFactory.createSnakeCaseWithoutUnknownPropertyDetection(false)
                .fromJson(unknownTextObjectsInInputBlocksJson, Message.class);
        assertThat(message, is(notNullValue()));
    }

    @Test
    public void parse_never_fails_on_unknown_text_objects_input() {
        // never fails - invalid types are just skipped
        GsonFactory.createSnakeCaseWithoutUnknownPropertyDetection(true)
                .fromJson(unknownTextObjectsInInputBlocksJson, Message.class);
    }

    @Test
    public void parseCheckboxes() {
        String json = "{\n" +
                "  \"blocks\": [\n" +
                "    {\n" +
                "      \"type\": \"input\",\n" +
                "      \"element\": {\n" +
                "        \"type\": \"checkboxes\",\n" +
                "        \"action_id\": \"input-action-id\",\n" +
                "        \"options\": [\n" +
                "          {\n" +
                "            \"text\": {\n" +
                "              \"type\": \"plain_text\",\n" +
                "              \"text\": \"*this is plain_text text*\",\n" +
                "              \"emoji\": true\n" +
                "            },\n" +
                "            \"value\": \"value-0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"text\": {\n" +
                "              \"type\": \"plain_text\",\n" +
                "              \"text\": \"*this is plain_text text*\",\n" +
                "              \"emoji\": true\n" +
                "            },\n" +
                "            \"value\": \"value-1\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"text\": {\n" +
                "              \"type\": \"plain_text\",\n" +
                "              \"text\": \"*this is plain_text text*\",\n" +
                "              \"emoji\": true\n" +
                "            },\n" +
                "            \"value\": \"value-2\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"label\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Label\",\n" +
                "        \"emoji\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"section\",\n" +
                "      \"text\": {\n" +
                "        \"type\": \"mrkdwn\",\n" +
                "        \"text\": \"This is a section block with checkboxes.\"\n" +
                "      },\n" +
                "      \"accessory\": {\n" +
                "        \"type\": \"checkboxes\",\n" +
                "        \"action_id\": \"section-action-id\",\n" +
                "        \"options\": [\n" +
                "          {\n" +
                "            \"text\": {\n" +
                "              \"type\": \"mrkdwn\",\n" +
                "              \"text\": \"*this is mrkdwn text*\"\n" +
                "            },\n" +
                "            \"description\": {\n" +
                "              \"type\": \"mrkdwn\",\n" +
                "              \"text\": \"*this is mrkdwn text*\"\n" +
                "            },\n" +
                "            \"value\": \"value-0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"text\": {\n" +
                "              \"type\": \"mrkdwn\",\n" +
                "              \"text\": \"*this is mrkdwn text*\"\n" +
                "            },\n" +
                "            \"description\": {\n" +
                "              \"type\": \"mrkdwn\",\n" +
                "              \"text\": \"*this is mrkdwn text*\"\n" +
                "            },\n" +
                "            \"value\": \"value-1\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"text\": {\n" +
                "              \"type\": \"mrkdwn\",\n" +
                "              \"text\": \"*this is mrkdwn text*\"\n" +
                "            },\n" +
                "            \"description\": {\n" +
                "              \"type\": \"mrkdwn\",\n" +
                "              \"text\": \"*this is mrkdwn text*\"\n" +
                "            },\n" +
                "            \"value\": \"value-2\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        Message message = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertThat(message, is(notNullValue()));

        InputBlock input = (InputBlock) message.getBlocks().get(0);
        CheckboxesElement checkboxes1 = (CheckboxesElement) input.getElement();
        assertThat(checkboxes1.getActionId(), is("input-action-id"));

        SectionBlock block = (SectionBlock) message.getBlocks().get(1);
        CheckboxesElement checkboxes2 = (CheckboxesElement) block.getAccessory();
        assertThat(checkboxes2.getActionId(), is("section-action-id"));
    }

    @Test
    public void confirm_style() {
        String json = "{\n" +
                "  \"title\": {\n" +
                "    \"type\": \"plain_text\",\n" +
                "    \"text\": \"Are you sure?\"\n" +
                "  },\n" +
                "  \"text\": {\n" +
                "    \"type\": \"mrkdwn\",\n" +
                "    \"text\": \"Wouldn't you prefer a good game of _chess_?\"\n" +
                "  },\n" +
                "  \"confirm\": {\n" +
                "    \"type\": \"plain_text\",\n" +
                "    \"text\": \"Do it\"\n" +
                "  },\n" +
                "  \"deny\": {\n" +
                "    \"type\": \"plain_text\",\n" +
                "    \"text\": \"Stop, I've changed my mind!\"\n" +
                "  },\n" +
                "  \"style\":\"primary\"\n" +
                "}";

        ConfirmationDialogObject obj = GsonFactory.createSnakeCase().fromJson(json, ConfirmationDialogObject.class);
        assertThat(obj, is(notNullValue()));
        assertThat(obj.getStyle(), is("primary"));
    }

    @Test
    public void parseHeader() {
        String json = "{\n" +
                "  \"blocks\": [{\n" +
                "    \"type\": \"header\",\n" +
                "    \"block_id\": \"b\",\n" +
                "    \"text\": {\n" +
                "      \"type\": \"plain_text\",\n" +
                "      \"text\": \"Budget Performance\"\n" +
                "    }\n" +
                "  }]\n" +
                "}";
        Message message = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertThat(message, is(notNullValue()));

        HeaderBlock header = (HeaderBlock) message.getBlocks().get(0);
        assertThat(header.getBlockId(), is("b"));
        assertThat(header.getText().getType(), is("plain_text"));
        assertThat(header.getText().getText(), is("Budget Performance"));
    }

    @Test
    public void parseCallBlock() {
        String json = "{\n" +
                "  \"type\": \"call\",\n" +
                "  \"call_id\": \"R111\",\n" +
                "  \"block_id\": \"hH9L\",\n" +
                "  \"api_decoration_available\": false,\n" +
                "  \"call\": {\n" +
                "    \"v1\": {\n" +
                "      \"id\": \"R111\",\n" +
                "      \"app_id\": \"A111\",\n" +
                "      \"app_icon_urls\": {\n" +
                "        \"image_32\": \"https://avatars.slack-edge.com/xxx_32.jpg\",\n" +
                "        \"image_36\": \"https://avatars.slack-edge.com/xxx_36.jpg\",\n" +
                "        \"image_48\": \"https://avatars.slack-edge.com/xxx_48.jpg\",\n" +
                "        \"image_64\": \"https://avatars.slack-edge.com/xxx_64.jpg\",\n" +
                "        \"image_72\": \"https://avatars.slack-edge.com/xxx_72.jpg\",\n" +
                "        \"image_96\": \"https://avatars.slack-edge.com/xxx_96.jpg\",\n" +
                "        \"image_128\": \"https://avatars.slack-edge.com/xxx_128.jpg\",\n" +
                "        \"image_192\": \"https://avatars.slack-edge.com/xxx_192.jpg\",\n" +
                "        \"image_512\": \"https://avatars.slack-edge.com/xxx_512.jpg\",\n" +
                "        \"image_1024\": \"https://avatars.slack-edge.com/xxx_1024.jpg\",\n" +
                "        \"image_original\": \"https://avatars.slack-edge.com/xxx_original.jpg\"\n" +
                "      },\n" +
                "      \"date_start\": 1596711312,\n" +
                "      \"active_participants\": [\n" +
                "        {\n" +
                "          \"external_id\": \"anon-111\",\n" +
                "          \"avatar_url\": \"https://assets.brandfolder.com/xxx/slackbot.png\",\n" +
                "          \"display_name\": \"anonymous user 1\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"slack_id\": \"USLACKBOT\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"all_participants\": [\n" +
                "        {\n" +
                "          \"external_id\": \"anon-111\",\n" +
                "          \"avatar_url\": \"https://assets.brandfolder.com/xxx/slackbot.png\",\n" +
                "          \"display_name\": \"anonymous user 1\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"slack_id\": \"USLACKBOT\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"display_id\": \"ext-display-id-111\",\n" +
                "      \"join_url\": \"https://www.example.com/calls-test\",\n" +
                "      \"desktop_app_join_url\": \"https://www.example.com/calls-test\",\n" +
                "      \"name\": \"Test call\",\n" +
                "      \"created_by\": \"U111\",\n" +
                "      \"date_end\": 0,\n" +
                "      \"channels\": [\n" +
                "        \"C03E94MKU\"\n" +
                "      ],\n" +
                "      \"is_dm_call\": false,\n" +
                "      \"was_rejected\": false,\n" +
                "      \"was_missed\": false,\n" +
                "      \"was_accepted\": false,\n" +
                "      \"has_ended\": false\n" +
                "    },\n" +
                "    \"media_backend_type\": \"platform_call\",\n" +
                "    \"unknown_one_in_the_future\": 123\n" +
                "  }\n" +
                "}\n";
        Gson prodGson = GsonFactory.createSnakeCase(false, false);
        CallBlock block = prodGson.fromJson(json, CallBlock.class);
        assertThat(block, is(notNullValue()));
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

    @Test
    public void defaultToCurrentConversation() {
        String json = "{\n" +
                "  \"blocks\": [\n" +
                "    {\n" +
                "      \"type\": \"section\",\n" +
                "      \"text\": {\n" +
                "        \"type\": \"mrkdwn\",\n" +
                "        \"text\": \"Test block with multi conversations select\"\n" +
                "      },\n" +
                "      \"accessory\": {\n" +
                "        \"type\": \"multi_conversations_select\",\n" +
                "        \"placeholder\": {\n" +
                "          \"type\": \"plain_text\",\n" +
                "          \"text\": \"Select conversations\",\n" +
                "          \"emoji\": true\n" +
                "        },\n" +
                "        \"default_to_current_conversation\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"section\",\n" +
                "      \"text\": {\n" +
                "        \"type\": \"mrkdwn\",\n" +
                "        \"text\": \"Test block with multi conversations select\"\n" +
                "      },\n" +
                "      \"accessory\": {\n" +
                "        \"type\": \"conversations_select\",\n" +
                "        \"placeholder\": {\n" +
                "          \"type\": \"plain_text\",\n" +
                "          \"text\": \"Select conversations\",\n" +
                "          \"emoji\": true\n" +
                "        },\n" +
                "        \"default_to_current_conversation\": true\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        Message message = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertNotNull(message);
        assertEquals(2, message.getBlocks().size());
        SectionBlock section1 = (SectionBlock) message.getBlocks().get(0);
        MultiConversationsSelectElement elem1 = (MultiConversationsSelectElement) (section1).getAccessory();
        assertTrue(elem1.getDefaultToCurrentConversation());
        SectionBlock section2 = (SectionBlock) message.getBlocks().get(1);
        ConversationsSelectElement elem2 = (ConversationsSelectElement) (section2).getAccessory();
        assertTrue(elem2.getDefaultToCurrentConversation());
    }

    @Test
    public void codeInDocs() {
        View modalView = view(v -> v
                .type("modal")
                .callbackId("view-id")
                .submit(viewSubmit(vs -> vs.type("plain_text").text("Submit")))
                .blocks(asBlocks(
                        section(s -> s
                                .text(plainText("Conversation to post the result"))
                                .accessory(conversationsSelect(conv -> conv
                                        .actionId("a")
                                        .defaultToCurrentConversation(true)
                                        .responseUrlEnabled(true))
                                )
                        )
                )));
        assertNotNull(modalView);
    }

    @Test
    public void timePickerElement() {
        // https://api.slack.com/reference/block-kit/block-elements#timepicker
        String json = "{\n" +
                "  \"type\": \"section\",\n" +
                "  \"block_id\": \"section1234\",\n" +
                "  \"text\": {\n" +
                "    \"type\": \"mrkdwn\",\n" +
                "    \"text\": \"Pick a date for the deadline.\"\n" +
                "  },\n" +
                "  \"accessory\": {\n" +
                "    \"type\": \"timepicker\",\n" +
                "    \"action_id\": \"timepicker123\",\n" +
                "    \"initial_time\": \"11:40\",\n" +
                "    \"placeholder\": {\n" +
                "      \"type\": \"plain_text\",\n" +
                "      \"text\": \"Select a time\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Gson gson = GsonFactory.createSnakeCase();
        SectionBlock section = gson.fromJson(json, SectionBlock.class);
        assertThat(section.getBlockId(), is("section1234"));
        TimePickerElement accessory = (TimePickerElement) section.getAccessory();
        assertThat(accessory.getActionId(), is("timepicker123"));
        assertThat(accessory.getInitialTime(), is("11:40"));
        assertThat(accessory.getPlaceholder().getText(), is("Select a time"));
        assertThat(accessory.getConfirm(), is(nullValue()));

        String output = gson.toJson(section);
        assertThat(output, is("{\"type\":\"section\",\"text\":{\"type\":\"mrkdwn\",\"text\":\"Pick a date for the deadline.\"},\"block_id\":\"section1234\",\"accessory\":{\"type\":\"timepicker\",\"action_id\":\"timepicker123\",\"placeholder\":{\"type\":\"plain_text\",\"text\":\"Select a time\"},\"initial_time\":\"11:40\"}}"));
    }

    @Test
    public void focusOnLoad() {
        String json = "{\"blocks\":[\n" +
                "  {\n" +
                "    \"type\": \"input\",\n" +
                "    \"element\": {\n" +
                "      \"type\": \"conversations_select\",\n" +
                "      \"placeholder\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Select a conversation\"\n" +
                "      },\n" +
                "      \"focus_on_load\": true\n" +
                "    },\n" +
                "    \"label\": {\n" +
                "      \"type\": \"plain_text\",\n" +
                "      \"text\": \"Choose the conversation to publish your result to:\"\n" +
                "    }\n" +
                "  }\n" +
                "]}";
        View view = GsonFactory.createSnakeCase().fromJson(json, View.class);
        assertNotNull(view);
        assertEquals(1, view.getBlocks().size());
        InputBlock block = (InputBlock) view.getBlocks().get(0);
        ConversationsSelectElement element = (ConversationsSelectElement) block.getElement();
        assertTrue(element.getFocusOnLoad());
    }

    @Test
    public void noFocusOnLoad() {
        String json = "{\"blocks\":[\n" +
                "  {\n" +
                "    \"type\": \"input\",\n" +
                "    \"element\": {\n" +
                "      \"type\": \"conversations_select\",\n" +
                "      \"placeholder\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Select a conversation\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"label\": {\n" +
                "      \"type\": \"plain_text\",\n" +
                "      \"text\": \"Choose the conversation to publish your result to:\"\n" +
                "    }\n" +
                "  }\n" +
                "]}";
        View view = GsonFactory.createSnakeCase().fromJson(json, View.class);
        assertNotNull(view);
        assertEquals(1, view.getBlocks().size());
        InputBlock block = (InputBlock) view.getBlocks().get(0);
        ConversationsSelectElement element = (ConversationsSelectElement) block.getElement();
        assertNull(element.getFocusOnLoad());
    }

    @Test
    public void parseVideoBlocks() {
        // https://api.slack.com/reference/block-kit/blocks#video
        String json = "{\n" +
                "  \"blocks\": [\n" +
                "    {\n" +
                "      \"type\": \"video\",\n" +
                "      \"block_id\": \"bid\",\n" +
                "      \"title\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"How to use Slack.\",\n" +
                "        \"emoji\": true\n" +
                "      },\n" +
                "      \"title_url\": \"https://www.youtube.com/watch?v=RRxQQxiM7AA\",\n" +
                "      \"description\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Slack is a new way to communicate with your team. It's faster, better organized and more secure than email.\",\n" +
                "        \"emoji\": true\n" +
                "      },\n" +
                "      \"video_url\": \"https://www.youtube.com/embed/RRxQQxiM7AA?feature=oembed&autoplay=1\",\n" +
                "      \"alt_text\": \"How to use Slack?\",\n" +
                "      \"thumbnail_url\": \"https://i.ytimg.com/vi/RRxQQxiM7AA/hqdefault.jpg\",\n" +
                "      \"author_name\": \"Arcado Buendia\",\n" +
                "      \"provider_name\": \"YouTube\",\n" +
                "      \"provider_icon_url\": \"https://a.slack-edge.com/80588/img/unfurl_icons/youtube.png\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        View view = GsonFactory.createSnakeCase().fromJson(json, View.class);
        assertNotNull(view);
        assertEquals(1, view.getBlocks().size());
        VideoBlock block = (VideoBlock) view.getBlocks().get(0);
        assertEquals("bid", block.getBlockId());
        assertEquals("https://www.youtube.com/embed/RRxQQxiM7AA?feature=oembed&autoplay=1", block.getVideoUrl());
    }

    @Test
    public void parseLinkTriggerMessages() {
        // https://api.slack.com/future
        String json = "{\n" +
                "  \"client_msg_id\": \"xxx\",\n" +
                "  \"type\": \"message\",\n" +
                "  \"text\": \"https://slack.com/shortcuts/Ft111/xxx\",\n" +
                "  \"user\": \"U111\",\n" +
                "  \"ts\": \"1663233138.852489\",\n" +
                "  \"team\": \"T111\",\n" +
                "  \"attachments\": [\n" +
                "    {\n" +
                "      \"fallback\": \"Workflow\",\n" +
                "      \"from_url\": \"https://slack.com/shortcuts/Ft111/xxx\",\n" +
                "      \"blocks\": [\n" +
                "        {\n" +
                "          \"block_id\": \"shortcut-block\",\n" +
                "          \"type\": \"share_shortcut\",\n" +
                "          \"function_trigger_id\": \"Ft111\",\n" +
                "          \"app_id\": \"A111\",\n" +
                "          \"is_workflow_app\": false,\n" +
                "          \"app_collaborators\": [\n" +
                "            \"U111\"\n" +
                "          ],\n" +
                "          \"button_label\": \"\",\n" +
                "          \"title\": \"Sample trigger\",\n" +
                "          \"description\": \"A sample trigger\",\n" +
                "          \"bot_user_id\": \"U111\",\n" +
                "          \"url\": \"https://slack.com/shortcuts/Ft111/xxx\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"id\": 1,\n" +
                "      \"original_url\": \"https://slack.com/shortcuts/Ft111/xxx\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"blocks\": [\n" +
                "    {\n" +
                "      \"type\": \"rich_text\",\n" +
                "      \"block_id\": \"JZy\",\n" +
                "      \"elements\": [\n" +
                "        {\n" +
                "          \"type\": \"rich_text_section\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"link\",\n" +
                "              \"url\": \"https://slack.com/shortcuts/Ft111/xxx\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        Message view = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertNotNull(view);
        assertEquals(1, view.getAttachments().get(0).getBlocks().size());
        assertEquals(1, view.getBlocks().size());
    }
}
