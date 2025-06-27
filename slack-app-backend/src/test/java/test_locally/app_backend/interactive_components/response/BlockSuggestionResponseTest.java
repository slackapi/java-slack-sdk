package test_locally.app_backend.interactive_components.response;

import com.slack.api.app_backend.interactive_components.response.BlockSuggestionResponse;
import com.slack.api.app_backend.interactive_components.response.OptionGroup;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BlockSuggestionResponseTest {

    // https://docs.slack.dev/reference/block-kit/composition-objects/option-group-object
    @Test
    public void optionGroups() {
        String json = "{\n" +
                "  \"option_groups\": [\n" +
                "    {\n" +
                "      \"label\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Group 1\"\n" +
                "      },\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"text\": {\n" +
                "            \"type\": \"plain_text\",\n" +
                "            \"text\": \"*this is plain_text text*\"\n" +
                "          },\n" +
                "          \"value\": \"value-0\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"text\": {\n" +
                "            \"type\": \"plain_text\",\n" +
                "            \"text\": \"*this is plain_text text*\"\n" +
                "          },\n" +
                "          \"value\": \"value-1\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"text\": {\n" +
                "            \"type\": \"plain_text\",\n" +
                "            \"text\": \"*this is plain_text text*\"\n" +
                "          },\n" +
                "          \"value\": \"value-2\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"label\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Group 2\"\n" +
                "      },\n" +
                "      \"options\": [\n" +
                "        {\n" +
                "          \"text\": {\n" +
                "            \"type\": \"plain_text\",\n" +
                "            \"text\": \"*this is plain_text text*\"\n" +
                "          },\n" +
                "          \"value\": \"value-3\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
        BlockSuggestionResponse response = GsonFactory.createSnakeCase().fromJson(json, BlockSuggestionResponse.class);
        assertThat(response.getOptionGroups().size(), is(2));

        OptionGroup optionGroup = response.getOptionGroups().get(0);
        PlainTextObject label = (PlainTextObject) optionGroup.getLabel();
        assertThat(label.getText(), is("Group 1"));
    }

    @Test
    public void options() {
        String json = "{\n" +
                "  \"options\": [\n" +
                "    {\n" +
                "      \"text\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"*this is plain_text text*\"\n" +
                "      },\n" +
                "      \"value\": \"value-3\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";

        BlockSuggestionResponse response = GsonFactory.createSnakeCase().fromJson(json, BlockSuggestionResponse.class);
        assertThat(response.getOptions().size(), is(1));

        PlainTextObject text = (PlainTextObject) response.getOptions().get(0).getText();
        assertThat(text.getText(), is("*this is plain_text text*"));
    }

}
