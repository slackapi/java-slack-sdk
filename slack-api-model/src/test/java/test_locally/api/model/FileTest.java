package test_locally.api.model;

import com.slack.api.model.File;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileTest {

    String ISSUE_1426_JSON = "{\n" +
            "  \"id\": \"F08AF7HUWQL\",\n" +
            "  \"created\": 1737949586,\n" +
            "  \"timestamp\": 1737949586,\n" +
            "  \"name\": \"sample.txt\",\n" +
            "  \"title\": \"sample.txt\",\n" +
            "  \"mimetype\": \"text/plain\",\n" +
            "  \"filetype\": \"text\",\n" +
            "  \"pretty_type\": \"Plain Text\",\n" +
            "  \"user\": \"U8P5K48E6\",\n" +
            "  \"user_team\": \"T03E94MJU\",\n" +
            "  \"editable\": true,\n" +
            "  \"size\": 57,\n" +
            "  \"mode\": \"snippet\",\n" +
            "  \"is_external\": false,\n" +
            "  \"external_type\": \"\",\n" +
            "  \"is_public\": true,\n" +
            "  \"public_url_shared\": false,\n" +
            "  \"display_as_bot\": false,\n" +
            "  \"username\": \"\",\n" +
            "  \"url_private\": \"https://files.slack.com/files-pri/T03E94MJU-F08AF7HUWQL/sample.txt\",\n" +
            "  \"url_private_download\": \"https://files.slack.com/files-pri/T03E94MJU-F08AF7HUWQL/download/sample.txt\",\n" +
            "  \"permalink\": \"https://seratch.slack.com/files/U8P5K48E6/F08AF7HUWQL/sample.txt\",\n" +
            "  \"permalink_public\": \"https://slack-files.com/T03E94MJU-F08AF7HUWQL-9d27bd3319\",\n" +
            "  \"edit_link\": \"https://seratch.slack.com/files/U8P5K48E6/F08AF7HUWQL/sample.txt/edit\",\n" +
            "  \"preview\": \"Hello, World!!!!!\\n\\nThis is a sample text file.\\n\\n日本語\",\n" +
            "  \"preview_highlight\": \"\\u003cdiv class\\u003d\\\"CodeMirror cm-s-default CodeMirrorServer\\\"\\u003e\\n\\u003cdiv class\\u003d\\\"CodeMirror-code\\\"\\u003e\\n\\u003cdiv\\u003e\\u003cpre\\u003eHello, World!!!!!\\u003c/pre\\u003e\\u003c/div\\u003e\\n\\u003cdiv\\u003e\\u003cpre\\u003e\\u003c/pre\\u003e\\u003c/div\\u003e\\n\\u003cdiv\\u003e\\u003cpre\\u003eThis is a sample text file.\\u003c/pre\\u003e\\u003c/div\\u003e\\n\\u003cdiv\\u003e\\u003cpre\\u003e\\u003c/pre\\u003e\\u003c/div\\u003e\\n\\u003cdiv\\u003e\\u003cpre\\u003e日本語\\u003c/pre\\u003e\\u003c/div\\u003e\\n\\u003c/div\\u003e\\n\\u003c/div\\u003e\\n\",\n" +
            "  \"lines\": 5,\n" +
            "  \"lines_more\": 0,\n" +
            "  \"preview_is_truncated\": false,\n" +
            "  \"favorites\": [],\n" +
            "  \"is_starred\": false,\n" +
            "  \"shares\": {\n" +
            "    \"public\": {\n" +
            "      \"C03E94MKU\": [\n" +
            "        {\n" +
            "          \"reply_users\": {},\n" + // unusual data structure here
            "          \"reply_users_count\": 0,\n" +
            "          \"reply_count\": 0,\n" +
            "          \"ts\": \"1737949587.842889\",\n" +
            "          \"channel_name\": \"random\",\n" +
            "          \"team_id\": \"T03E94MJU\",\n" +
            "          \"share_user_id\": \"U8P5K48E6\",\n" +
            "          \"source\": \"UNKNOWN\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"C03E94MKS\": [\n" +
            "        {\n" +
            "          \"reply_users\": [],\n" +
            "          \"reply_users_count\": 0,\n" +
            "          \"reply_count\": 0,\n" +
            "          \"ts\": \"1737949587.678069\",\n" +
            "          \"channel_name\": \"general\",\n" +
            "          \"team_id\": \"T03E94MJU\",\n" +
            "          \"share_user_id\": \"U8P5K48E6\",\n" +
            "          \"source\": \"UNKNOWN\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  \"channels\": [\n" +
            "    \"C03E94MKU\",\n" +
            "    \"C03E94MKS\"\n" +
            "  ],\n" +
            "  \"groups\": {},\n" + // unusual data structure here
            "  \"ims\": [],\n" +
            "  \"has_more_shares\": false,\n" +
            "  \"has_rich_preview\": false,\n" +
            "  \"file_access\": \"visible\",\n" +
            "  \"comments_count\": 0\n" +
            "}\n";

    @Test
    public void issue1426_parse() {
        // com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 65 column 14 path $.groups
        File message = GsonFactory.createSnakeCase().fromJson(ISSUE_1426_JSON, File.class);
        assertThat(message.getShares(), is(notNullValue()));
    }
}
