package test_locally.api.model.block;

import com.github.seratch.jslack.api.model.Message;
import com.github.seratch.jslack.api.model.block.RichTextBlock;
import com.github.seratch.jslack.api.model.block.element.RichTextSectionElement;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BlockKitTest {

    @Test
    public void parseRichTextOnes() throws Exception {
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
}
