package test_locally.api.model;

import com.slack.api.methods.response.conversations.ConversationsInfoResponse;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConversationTest implements Verifier {

    @Test
    public void parseConversationsInfoResponse() throws Exception {
        verifyParsing("conversations.info", ConversationsInfoResponse.class);
    }

    @Test
    public void parseConversationsListResponse() throws Exception {
        verifyParsing("conversations.list", ConversationsListResponse.class);
    }

    @Test
    public void parseFileConversation() {
        String json = "{\"ok\":true,\"channel\":{\"id\":\"C0123456789\",\"is_channel\":true,\"is_private\":true,"
                + "\"is_file\":true,\"is_thread_only\":true,\"name_normalized\":\"FC:F0123456789:Project notes\"}}";
        ConversationsInfoResponse resp = GsonFactory.createSnakeCase().fromJson(json, ConversationsInfoResponse.class);
        assertThat(resp.getChannel().isFile(), is(true));
    }

}
