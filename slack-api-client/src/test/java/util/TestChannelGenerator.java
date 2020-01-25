package util;

import com.slack.api.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsArchiveRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsCreateRequest;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsArchiveResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsCreateResponse;
import com.slack.api.model.Conversation;
import config.SlackTestConfig;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestChannelGenerator {

    private final String token;
    private final Slack slack = Slack.getInstance(SlackTestConfig.get());

    public TestChannelGenerator(String token) {
        this.token = token;
    }

    public Conversation createNewPublicChannel(String channelName) throws IOException, SlackApiException {
        return createNewChannel(channelName, false);
    }

    public Conversation createNewPrivateChannel(String channelName) throws IOException, SlackApiException {
        return createNewChannel(channelName, true);
    }

    public Conversation createNewChannel(String channelName, boolean isPrivate) throws IOException, SlackApiException {
        ConversationsCreateResponse createPublicResponse = slack.methods().conversationsCreate(
                ConversationsCreateRequest.builder()
                        .token(token)
                        .name(channelName)
                        .isPrivate(isPrivate)
                        .build());
        assertThat(createPublicResponse.getError(), is(nullValue()));
        assertThat(createPublicResponse.isOk(), is(true));
        assertThat(createPublicResponse.getChannel(), is(notNullValue()));
        assertThat(createPublicResponse.getChannel().isPrivate(), is(isPrivate));

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
