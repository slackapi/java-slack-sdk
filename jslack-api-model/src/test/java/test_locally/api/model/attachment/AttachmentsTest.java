package test_locally.api.model.attachment;

import com.github.seratch.jslack.api.model.Message;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AttachmentsTest {

    // Thanks to https://github.com/seratch/jslack/issues/267
    String json = "{\n" +
            "  \"type\": \"message\",\n" +
            "  \"text\": \"\",\n" +
            "  \"files\": [\n" +
            "    {\n" +
            "      \"id\": \"FPU0Q2QE5\",\n" +
            "      \"created\": 1572605991,\n" +
            "      \"timestamp\": 1572605971,\n" +
            "      \"name\": \"Fwd: Research Newsletter October 2019\",\n" +
            "      \"title\": \"Fwd: Research Newsletter October 2019\",\n" +
            "      \"mimetype\": \"text\\/html\",\n" +
            "      \"filetype\": \"email\",\n" +
            "      \"pretty_type\": \"Email\",\n" +
            "      \"user\": \"USLACKBOT\",\n" +
            "      \"editable\": true,\n" +
            "      \"size\": 322064,\n" +
            "      \"mode\": \"email\",\n" +
            "      \"is_external\": false,\n" +
            "      \"external_type\": \"\",\n" +
            "      \"is_public\": false,\n" +
            "      \"public_url_shared\": false,\n" +
            "      \"display_as_bot\": true,\n" +
            "      \"username\": \"email\",\n" +
            "      \"url_private\": \"https:\\/\\/files.slack.com\\/files-pri\\/T7K35E469-FPU0Q2QE5\\/fwd__research_newsletter_october_2019\",\n" +
            "      \"url_private_download\": \"https:\\/\\/files.slack.com\\/files-pri\\/T7K35E469-FPU0Q2QE5\\/download\\/fwd__research_newsletter_october_2019\",\n" +
            "      \"permalink\": \"https:\\/\\/fnalab.slack.com\\/files\\/USLACKBOT\\/FPU0Q2QE5\\/fwd__research_newsletter_october_2019\",\n" +
            "      \"permalink_public\": \"https:\\/\\/slack-files.com\\/T7K35E469-FPU0Q2QE5-4d361ed9bd\",\n" +
            "      \"subject\": \"Fwd: Research Newsletter October 2019\",\n" +
            "      \"to\": [\n" +
            "        {\n" +
            "          \"address\": \"data_science@fna.fi\",\n" +
            "          \"name\": \"Data Science\",\n" +
            "          \"original\": \"Data Science <data_science@fna.fi>\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"from\": [\n" +
            "        {\n" +
            "          \"address\": \"kimmo@fna.fi\",\n" +
            "          \"name\": \"Kimmo Soram\\u00e4ki\",\n" +
            "          \"original\": \"Kimmo Soram\\u00e4ki <kimmo@fna.fi>\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"cc\": [],\n" +
            "      \"attachments\": [\n" +
            "        {\n" +
            "          \"filename\": \"image001.jpg\",\n" +
            "          \"size\": 3227,\n" +
            "          \"mimetype\": \"image\\/jpeg\",\n" +
            "          \"url\": \"https:\\/\\/files-origin.slack.com\\/files-email-priv\\/T7K35E469-FPU0Q2QE5-f0b03de283-f82ce67a\\/image001.jpg\",\n" +
            "          \"metadata\": {\n" +
            "            \"thumb_64\": true,\n" +
            "            \"original_w\": 206,\n" +
            "            \"original_h\": 67,\n" +
            "            \"thumb_360_w\": 206,\n" +
            "            \"thumb_360_h\": 67,\n" +
            "            \"format\": \"JPEG\",\n" +
            "            \"extension\": \".jpg\",\n" +
            "            \"rotation\": 1,\n" +
            "            \"thumb_tiny\": \"AwAPADCoEYjgZpfKfP3DWiNPQDAkk\\/Sl+wj\\/AJ7SfnW3OhWM3y3\\/ALpo8t\\/7prS+wrz+9k59xSNZqo\\/1sp\\/EUc6CxnGNx1U0hVgMkECtD7Iv9+X8xQLJGO0vJ+Yo50Fj\\/9k=\",\n" +
            "            \"thumb_80\": true,\n" +
            "            \"thumb_160\": true\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"filename\": \"image001.jpg\",\n" +
            "          \"size\": 3227,\n" +
            "          \"mimetype\": \"image\\/jpeg\",\n" +
            "          \"url\": \"https:\\/\\/files-origin.slack.com\\/files-email-priv\\/T7K35E469-FPU0Q2QE5-f0b03de283-4bc05bae\\/image001.jpg\",\n" +
            "          \"metadata\": {\n" +
            "            \"thumb_64\": true,\n" +
            "            \"original_w\": 206,\n" +
            "            \"original_h\": 67,\n" +
            "            \"thumb_360_w\": 206,\n" +
            "            \"thumb_360_h\": 67,\n" +
            "            \"format\": \"JPEG\",\n" +
            "            \"extension\": \".jpg\",\n" +
            "            \"rotation\": 1,\n" +
            "            \"thumb_tiny\": \"AwAPADCoEYjgZpfKfP3DWiNPQDAkk\\/Sl+wj\\/AJ7SfnW3OhWM3y3\\/ALpo8t\\/7prS+wrz+9k59xSNZqo\\/1sp\\/EUc6CxnGNx1U0hVgMkECtD7Iv9+X8xQLJGO0vJ+Yo50Fj\\/9k=\",\n" +
            "            \"thumb_80\": true,\n" +
            "            \"thumb_160\": true\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"filename\": \"Research Newsletter 42.pdf\",\n" +
            "          \"size\": 297507,\n" +
            "          \"mimetype\": \"application\\/pdf\",\n" +
            "          \"url\": \"https:\\/\\/files-origin.slack.com\\/files-email-priv\\/T7K35E469-FPU0Q2QE5-f0b03de283-ac5cb977\\/research_newsletter_42.pdf\",\n" +
            "          \"metadata\": null\n" +
            "        }\n" +
            "      ],\n" +
            "      \"plain_text\": \"\",\n" +
            "      \"preview\": \"\",\n" +
            "      \"preview_plain_text\": \"\",\n" +
            "      \"has_more\": true,\n" +
            "      \"sent_to_self\": false,\n" +
            "      \"bot_id\": \"BCS5JNV34\",\n" +
            "      \"is_starred\": false,\n" +
            "      \"has_rich_preview\": false\n" +
            "    }\n" +
            "  ],\n" +
            "  \"upload\": true,\n" +
            "  \"user\": \"USLACKBOT\",\n" +
            "  \"display_as_bot\": true,\n" +
            "  \"bot_id\": \"BCS5JNV34\",\n" +
            "  \"ts\": \"1572605991.115500\"\n" +
            "}\n";

    @Test
    public void parse() {
        Message message = GsonFactory.createSnakeCase().fromJson(json, Message.class);
        assertThat(message.getFiles().size(), is(1));
        assertThat(message.getFiles().get(0).getAttachments().size(), is(3));
        assertThat(message.getFiles().get(0).getAttachments().get(0).getMetadata(), is(notNullValue()));
    }

}
