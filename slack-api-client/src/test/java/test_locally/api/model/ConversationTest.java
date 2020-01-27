package test_locally.api.model;

import com.slack.api.methods.response.conversations.ConversationsInfoResponse;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import org.junit.Test;

public class ConversationTest implements Verifier {

    @Test
    public void parseConversationsInfoResponse() throws Exception {
        verifyParsing("conversations.info", ConversationsInfoResponse.class);
    }

    @Test
    public void parseConversationsListResponse() throws Exception {
        verifyParsing("conversations.list", ConversationsListResponse.class);
    }

}
