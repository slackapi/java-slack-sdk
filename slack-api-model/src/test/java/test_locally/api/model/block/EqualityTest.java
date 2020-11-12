package test_locally.api.model.block;

import com.slack.api.model.block.UnknownBlockElement;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.composition.UnknownTextObject;
import com.slack.api.model.block.element.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EqualityTest {

    @Test
    public void equality() {
        assertEquals(new UnknownTextObject(), new UnknownTextObject());
        assertEquals(new MarkdownTextObject(), new MarkdownTextObject());
        assertEquals(new PlainTextObject(), new PlainTextObject());

        assertEquals(new UnknownBlockElement(), new UnknownBlockElement());
        assertEquals(new ButtonElement(), new ButtonElement());
        assertEquals(new ChannelsSelectElement(), new ChannelsSelectElement());
        assertEquals(new CheckboxesElement(), new CheckboxesElement());
        assertEquals(new ConversationsSelectElement(), new ConversationsSelectElement());
        assertEquals(new DatePickerElement(), new DatePickerElement());
        assertEquals(new TimePickerElement(), new TimePickerElement());
        assertEquals(new ExternalSelectElement(), new ExternalSelectElement());
        assertEquals(new ImageElement(), new ImageElement());
        assertEquals(new MultiChannelsSelectElement(), new MultiChannelsSelectElement());
        assertEquals(new MultiConversationsSelectElement(), new MultiConversationsSelectElement());
        assertEquals(new MultiExternalSelectElement(), new MultiExternalSelectElement());
        assertEquals(new MultiStaticSelectElement(), new MultiStaticSelectElement());
        assertEquals(new MultiUsersSelectElement(), new MultiUsersSelectElement());
        assertEquals(new OverflowMenuElement(), new OverflowMenuElement());
        assertEquals(new PlainTextInputElement(), new PlainTextInputElement());
        assertEquals(new RadioButtonsElement(), new RadioButtonsElement());
        assertEquals(new RichTextListElement(), new RichTextListElement());
        assertEquals(new RichTextPreformattedElement(), new RichTextPreformattedElement());
        assertEquals(new RichTextQuoteElement(), new RichTextQuoteElement());
        assertEquals(new RichTextSectionElement(), new RichTextSectionElement());
        assertEquals(new RichTextUnknownElement(), new RichTextUnknownElement());
        assertEquals(new StaticSelectElement(), new StaticSelectElement());
        assertEquals(new UsersSelectElement(), new UsersSelectElement());
    }
}
