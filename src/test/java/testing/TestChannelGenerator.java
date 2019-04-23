package testing;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsArchiveRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsCreateRequest;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsArchiveResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsCreateResponse;
import com.github.seratch.jslack.api.model.Conversation;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class TestChannelGenerator {

    private final String token;
    private final Slack slack = Slack.getInstance(SlackTestConfig.get());

    public TestChannelGenerator(String token) {
        this.token = token;
    }

    public Conversation createNewPublicChannel(String channelName) throws IOException, SlackApiException {
        ConversationsCreateResponse createPublicResponse = slack.methods().conversationsCreate(
                ConversationsCreateRequest.builder()
                        .token(token)
                        .name(channelName)
                        .isPrivate(false)
                        .build());
        assertThat(createPublicResponse.getError(), is(nullValue()));
        assertThat(createPublicResponse.isOk(), is(true));
        assertThat(createPublicResponse.getChannel(), is(notNullValue()));
        assertThat(createPublicResponse.getChannel().isPrivate(), is(false));

        return createPublicResponse.getChannel();

    }

    public void archiveChannel(Conversation channel) throws IOException, SlackApiException {
        ConversationsArchiveResponse archiveResponse = slack.methods().conversationsArchive(
                ConversationsArchiveRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .build());
        assertThat(archiveResponse.getError(), is(nullValue()));
        assertThat(archiveResponse.isOk(), is(true));
    }

}
