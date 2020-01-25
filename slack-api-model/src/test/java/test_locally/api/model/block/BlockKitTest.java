package test_locally.api.model.block;

import com.slack.api.model.Message;
import com.slack.api.model.block.InputBlock;
import com.slack.api.model.block.RichTextBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.element.RadioButtonsElement;
import com.slack.api.model.block.element.RichTextSectionElement;
import com.slack.api.model.block.element.RichTextUnknownElement;
import com.google.gson.JsonParseException;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

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

}
