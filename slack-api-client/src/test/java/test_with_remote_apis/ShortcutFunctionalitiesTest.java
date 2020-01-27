package test_with_remote_apis;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.reactions.ReactionsAddResponse;
import com.slack.api.methods.response.search.SearchAllResponse;
import com.slack.api.methods.shortcut.Shortcut;
import com.slack.api.methods.shortcut.model.ApiToken;
import com.slack.api.methods.shortcut.model.ChannelId;
import com.slack.api.methods.shortcut.model.ChannelName;
import com.slack.api.methods.shortcut.model.ReactionName;
import com.slack.api.model.Attachment;
import com.slack.api.model.Message;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.element.ImageElement;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class ShortcutFunctionalitiesTest {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    ApiToken token = ApiToken.of(System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN));

    @Test
    public void chatOps() throws IOException, SlackApiException {
        Shortcut shortcut = slack.shortcut(token);
        ChannelName channelName = ChannelName.of("random");

        Optional<ChannelId> channelId = shortcut.findChannelIdByName(channelName);
        assertThat(channelId.isPresent(), is(true));

        Optional<ChannelName> maybeChannelName = shortcut.findChannelNameById(channelId.get());
        assertThat(maybeChannelName.isPresent(), is(true));
        assertThat(maybeChannelName.get(), is(channelName));

        List<Message> messages = shortcut.findRecentMessagesByName(channelName, 10);
        assertThat(messages, is(notNullValue()));

        SearchAllResponse searchResult = shortcut.search("hello");
        assertThat(searchResult, is(notNullValue()));

        ReactionName reactionName = ReactionName.of("smile");
        ReactionsAddResponse addReaction = shortcut.addReaction(messages.get(0), reactionName);
        assertThat(addReaction.getError(), is(nullValue()));
        assertThat(addReaction.isOk(), is(true));
    }

    @Test
    public void postMessage() throws IOException, SlackApiException {
        Shortcut shortcut = slack.shortcut(token);
        Attachment attachment = Attachment.builder().text("text").footer("footer").build();
        List<Attachment> attachments = Arrays.asList(attachment);

        {
            ChatPostMessageResponse response = shortcut.post(ChannelName.of("random"), "hello, hello!");
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            ChatPostMessageResponse response = shortcut.postAsBot(ChannelName.of("random"), "hello, hello!");
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            ChatPostMessageResponse response = shortcut.post(ChannelName.of("random"), "Hi, these are my attachments!", attachments);
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        {
            ChatPostMessageResponse response = shortcut.postAsBot(ChannelName.of("random"), "Hi, these are my attachments!", attachments);
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
    }

    @Test
    public void postMessage_blocks() throws IOException, SlackApiException {
        Shortcut shortcut = slack.shortcut(token);

        {
            MarkdownTextObject text = MarkdownTextObject.builder()
                    .text("*Ler Ros*\n:star::star::star::star: 2082 reviews\n I would really recommend the  Yum Koh Moo Yang - Spicy lime dressing and roasted quick marinated pork shoulder, basil leaves, chili & rice powder.")
                    .build();
            ImageElement accessory = ImageElement.builder()
                    .imageUrl("https://s3-media2.fl.yelpcdn.com/bphoto/DawwNigKJ2ckPeDeDM7jAg/o.jpg")
                    .altText("alt text for image")
                    .build();

            SectionBlock section = SectionBlock.builder()
                    .text(text)
                    .accessory(accessory)
                    .build();

            DividerBlock divider = new DividerBlock();

            ChatPostMessageResponse response = shortcut.post(ChannelName.of("random"), Arrays.asList(section, divider));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
    }
}
