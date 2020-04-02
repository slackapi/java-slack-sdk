package test_locally.app_backend.outgoing_webhooks;

import com.slack.api.app_backend.outgoing_webhooks.response.WebhookResponse;
import com.slack.api.model.Attachment;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertNull;

public class WebhookResponseTest {

    @Test
    public void test() {
        Attachment attachment = Attachment.builder()
                .msgSubtype("bot_message")
                .fallback("This is test attachment.")
                .color("#36a64f")
                .pretext("This is pre text.")
                .authorName("Smiling Imp")
                .authorLink("https://slack.com/intl/ja-jp/")
                .channelId("C061EG9SL")
                .channelName("general")
                .id(1)
                .title("Slack API Documentation")
                .titleLink("https://api.slack.com/")
                .text("This is an *attachment*.")
                .footer("footer")
                .build();

        WebhookResponse response = WebhookResponse.builder()
                .text("Thanks!")
                .attachments(Collections.singletonList(attachment))
                .build();
        assertThat(response.getText(), is("Thanks!"));
        assertThat(response.getAttachments().size(), is(1));
        Attachment attachmentResponse = response.getAttachments().get(0);
        assertThat(attachmentResponse.getMsgSubtype(), is("bot_message"));
        assertThat(attachmentResponse.getFallback(), is("This is test attachment."));
        assertNull(attachmentResponse.getCallbackId());
        assertThat(attachmentResponse.getColor(), is("#36a64f"));
        assertThat(attachmentResponse.getPretext(), is("This is pre text."));
        assertNull(attachmentResponse.getServiceUrl());
        assertNull(attachmentResponse.getServiceName());
        assertNull(attachmentResponse.getServiceIcon());
        assertThat(attachmentResponse.getAuthorName(), is("Smiling Imp"));
        assertThat(attachmentResponse.getAuthorLink(), is("https://slack.com/intl/ja-jp/"));
        assertNull(attachmentResponse.getAuthorIcon());
        assertNull(attachmentResponse.getFromUrl());
        assertNull(attachmentResponse.getOriginalUrl());
        assertNull(attachmentResponse.getAuthorSubname());
        assertThat(attachmentResponse.getChannelId(), is("C061EG9SL"));
        assertThat(attachmentResponse.getChannelName(), is("general"));
        assertThat(attachmentResponse.getId(), is(1));
        assertNull(attachmentResponse.getBotId());
        assertNull(attachmentResponse.isIndent());
        assertNull(attachmentResponse.isMsgUnfurl());
        assertNull(attachmentResponse.isReplyUnfurl());
        assertNull(attachmentResponse.isThreadRootUnfurl());
        assertNull(attachmentResponse.isAppUnfurl());
        assertNull(attachmentResponse.getAppUnfurlUrl());
        assertThat(attachmentResponse.getTitle(), is("Slack API Documentation"));
        assertThat(attachmentResponse.getTitleLink(), is("https://api.slack.com/"));
        assertThat(attachmentResponse.getText(), is("This is an *attachment*."));
        assertThat(attachmentResponse.getFields(), is(nullValue()));
        assertNull(attachmentResponse.getImageUrl());
        assertNull(attachmentResponse.getImageWidth());
        assertNull(attachmentResponse.getImageHeight());
        assertNull(attachmentResponse.getImageBytes());
        assertNull(attachmentResponse.getThumbUrl());
        assertNull(attachmentResponse.getThumbWidth());
        assertNull(attachmentResponse.getThumbHeight());
        assertNull(attachmentResponse.getVideoHtml());
        assertNull(attachmentResponse.getVideoHtmlWidth());
        assertNull(attachmentResponse.getVideoHtmlHeight());
        assertThat(attachmentResponse.getFooter(), is("footer"));
        assertNull(attachmentResponse.getFooterIcon());
        assertNull(attachmentResponse.getTs());
        assertThat(attachmentResponse.getMrkdwnIn(), is(nullValue()));
        assertThat(attachmentResponse.getActions(), is(nullValue()));
        assertNull(attachmentResponse.getBlocks());
        assertNull(attachmentResponse.getFilename());
        assertNull(attachmentResponse.getSize());
        assertNull(attachmentResponse.getMimetype());
        assertNull(attachmentResponse.getUrl());
        assertNull(attachmentResponse.getMetadata());
    }
}
