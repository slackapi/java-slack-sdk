package test_locally.api.methods;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.MethodsConfig;
import com.slack.api.methods.response.conversations.ConversationsHistoryResponse;
import com.slack.api.model.ConversationType;
import com.slack.api.model.canvas.CanvasDocumentContent;
import com.slack.api.scim.metrics.MemoryMetricsDatastore;
import com.slack.api.util.json.GsonFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class ConversationsTest {

    MockSlackApiServer server = new MockSlackApiServer();
    Slack slack = null;

    @Before
    public void setup() throws Exception {
        server.start();
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
        config.setMethodsConfig(new MethodsConfig());
        // skip the burst traffic detection for these tests
        config.getMethodsConfig().setStatsEnabled(false);
        slack = Slack.getInstance(config);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void test() throws Exception {
        assertThat(slack.methods(ValidToken).conversationsArchive(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsClose(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsCreate(r -> r.name("foo"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsHistory(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsInfo(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsInvite(r -> r.channel("C123").users(Arrays.asList("U123")))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsJoin(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsKick(r -> r.channel("C123").user("U123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsLeave(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsList(r -> r
                .limit(1).cursor("xxx").types(Arrays.asList(ConversationType.PUBLIC_CHANNEL)))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsMark(r -> r.channel("C123").ts("111.222"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsMembers(r -> r.limit(1).cursor("xxx"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsOpen(r -> r.users(Arrays.asList("U123")).channel("D123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsRename(r -> r.channel("C123").name("new name"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsReplies(r -> r.channel("C123").limit(1).cursor("xxx"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsSetPurpose(r -> r.channel("C123").purpose("something"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsSetTopic(r -> r.channel("C123").topic("something"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsUnarchive(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsInviteShared(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsAcceptSharedInvite(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsApproveSharedInvite(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsDeclineSharedInvite(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsListConnectInvites(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsCanvasesCreate(r -> r.documentContent(new CanvasDocumentContent()))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsCanvasesCreate(r -> r.markdown("hey"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsExternalInvitePermissionsSet(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsRequestSharedInviteApprove(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsRequestSharedInviteDeny(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsRequestSharedInviteList(r -> r)
                .isOk(), is(true));
    }

    @Test
    public void test_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).conversationsArchive(r -> r.channel("C123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsClose(r -> r.channel("C123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsCreate(r -> r.name("foo"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsHistory(r -> r.channel("C123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsInfo(r -> r.channel("C123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsInvite(r -> r.channel("C123").users(Arrays.asList("U123")))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsJoin(r -> r.channel("C123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsKick(r -> r.channel("C123").user("U123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsLeave(r -> r.channel("C123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsList(r -> r
                .limit(1).cursor("xxx").types(Arrays.asList(ConversationType.PUBLIC_CHANNEL)))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsMark(r -> r.channel("C123").ts("111.222"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsMembers(r -> r.limit(1).cursor("xxx"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsOpen(r -> r.users(Arrays.asList("U123")).channel("D123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsRename(r -> r.channel("C123").name("new name"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsReplies(r -> r.channel("C123").limit(1).cursor("xxx"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsSetPurpose(r -> r.channel("C123").purpose("something"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsSetTopic(r -> r.channel("C123").topic("something"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsUnarchive(r -> r.channel("C123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsInviteShared(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsAcceptSharedInvite(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsApproveSharedInvite(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsDeclineSharedInvite(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsListConnectInvites(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsCanvasesCreate(r -> r.documentContent(new CanvasDocumentContent()))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsCanvasesCreate(r -> r.markdown("hey"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsExternalInvitePermissionsSet(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsRequestSharedInviteApprove(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsRequestSharedInviteDeny(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsRequestSharedInviteList(r -> r)
                .get().isOk(), is(true));
    }

    // https://github.com/slackapi/java-slack-sdk/issues/1207
    @Test
    public void issue_1207() {
        String jsonData = "{\n" +
                "  \"ok\": true,\n" +
                "  \"oldest\": \"1695106171.334469\",\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"client_msg_id\": \"xxx\",\n" +
                "      \"type\": \"message\",\n" +
                "      \"text\": \"test\\n<https://xxx.slack.com/archives/C123/p1695106171334469>\",\n" +
                "      \"user\": \"U123\",\n" +
                "      \"ts\": \"1695106171.334469\",\n" +
                "      \"blocks\": [\n" +
                "        {\n" +
                "          \"type\": \"rich_text\",\n" +
                "          \"block_id\": \"Qkc\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"rich_text_section\",\n" +
                "              \"elements\": [\n" +
                "                {\n" +
                "                  \"type\": \"text\",\n" +
                "                  \"text\": \"test\\n\"\n" +
                "                },\n" +
                "                {\n" +
                "                  \"type\": \"link\",\n" +
                "                  \"url\": \"https://xxx.slack.com/archives/C123/p1695106171334469\"\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ],\n" +
                "      \"team\": \"T123\",\n" +
                "      \"edited\": {\n" +
                "        \"user\": \"U123\",\n" +
                "        \"ts\": \"1695106197.000000\"\n" +
                "      },\n" +
                "      \"attachments\": [\n" +
                "        {\n" +
                "          \"from_url\": \"https://xxx.slack.com/archives/C123/p1695106171334469\",\n" +
                "          \"ts\": \"1695106171.334469\",\n" +
                "          \"author_id\": \"U123\",\n" +
                "          \"channel_id\": \"C123\",\n" +
                "          \"channel_team\": \"T123\",\n" +
                "          \"is_msg_unfurl\": true,\n" +
                "          \"message_blocks\": [\n" +
                "            {\n" +
                "              \"team\": \"T123\",\n" +
                "              \"channel\": \"C123\",\n" +
                "              \"ts\": \"1695106171.334469\",\n" +
                "              \"message\": {\n" +
                "                \"blocks\": [\n" +
                "                  {\n" +
                "                    \"type\": \"rich_text\",\n" +
                "                    \"block_id\": \"Qkc\",\n" +
                "                    \"elements\": [\n" +
                "                      {\n" +
                "                        \"type\": \"rich_text_section\",\n" +
                "                        \"elements\": [\n" +
                "                          {\n" +
                "                            \"type\": \"text\",\n" +
                "                            \"text\": \"test\\n\"\n" +
                "                          },\n" +
                "                          {\n" +
                "                            \"type\": \"link\",\n" +
                "                            \"url\": \"https://xxx.slack.com/archives/C123/p1695106171334469\"\n" +
                "                          }\n" +
                "                        ]\n" +
                "                      }\n" +
                "                    ]\n" +
                "                  }\n" +
                "                ]\n" +
                "              }\n" +
                "            }\n" +
                "          ],\n" +
                "          \"id\": 1,\n" +
                "          \"original_url\": \"https://xxx.slack.com/archives/C123/p1695106171334469\",\n" +
                "          \"fallback\": \"fallback\",\n" +
                "          \"text\": \"test\\n<https://xxx.slack.com/archives/C123/p1695106171334469>\",\n" +
                "          \"author_name\": \"Kaz\",\n" +
                "          \"author_link\": \"https://xxx.slack.com/team/U123\",\n" +
                "          \"author_icon\": \"https://avatars.slack-edge.com/2023-09-19/5927659638721_20cd515c6ad81ae7b17a_48.jpg\",\n" +
                "          \"author_subname\": \"Kaz Sera\",\n" +
                "          \"mrkdwn_in\": [\n" +
                "            \"text\"\n" +
                "          ],\n" +
                "          \"footer\": \"Slack conversation\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"has_more\": false,\n" +
                "  \"pin_count\": 0,\n" +
                "  \"channel_actions_ts\": 1638492970,\n" +
                "  \"channel_actions_count\": 0\n" +
                "}\n";
        Gson gson = GsonFactory.createSnakeCase();
        ConversationsHistoryResponse response = gson.fromJson(jsonData, ConversationsHistoryResponse.class);
        assertThat(response.getMessages().size(), is(1));
    }
}
