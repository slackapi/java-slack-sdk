package test_locally.api.model;

import com.slack.api.model.*;
import com.slack.api.model.block.ActionsBlock;
import com.slack.api.model.block.ContextBlock;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.composition.*;
import com.slack.api.model.block.element.*;
import com.slack.api.model.dialog.DialogSubType;
import com.slack.api.model.event.MessageBotEvent;
import com.slack.api.model.event.MessageEvent;
import com.slack.api.model.view.ViewState;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.slack.api.model.Attachments.attachmentMetadata;
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;
import static com.slack.api.model.view.Views.viewState;
import static org.junit.Assert.*;

public class ModelsTest {

    @Test
    public void dialogSubTypes() {
        assertEquals("number", DialogSubType.NUMBER.value());
    }

    @Test
    public void actionTypes() {
        assertEquals("button", Action.Type.BUTTON.value());
        assertEquals("select", Action.Type.SELECT.value());
    }

    @Test
    public void attachment() {

        {
            Attachment attachment = new Attachment();
            assertNull(attachment.isIndent());
            attachment.setIndent(true);
            assertTrue(attachment.isIndent());
        }

        {
            Attachment attachment = new Attachment();
            assertNull(attachment.isMsgUnfurl());
            attachment.setMsgUnfurl(true);
            assertTrue(attachment.isMsgUnfurl());
        }

        {
            Attachment attachment = new Attachment();
            assertNull(attachment.isReplyUnfurl());
            attachment.setReplyUnfurl(true);
            assertTrue(attachment.isReplyUnfurl());
        }

        {
            Attachment attachment = new Attachment();
            assertNull(attachment.isAppUnfurl());
            attachment.setAppUnfurl(true);
            assertTrue(attachment.isAppUnfurl());
        }

        {
            Attachment attachment = new Attachment();
            assertNull(attachment.isThreadRootUnfurl());
            attachment.setThreadRootUnfurl(true);
            assertTrue(attachment.isThreadRootUnfurl());
        }
    }

    @Test
    public void attachments() {
        Attachment.AttachmentMetadata metadata = attachmentMetadata(r -> r.originalHeight(123).originalWidth(234));
        assertNotNull(metadata);
    }

    @Test
    public void file() {
        File file = new File();
        assertFalse(file.isPublic());
        file.setPublic(true);
        assertTrue(file.isPublic());
    }

    @Test
    public void matchedItem() {
        MatchedItem item = new MatchedItem();
        assertFalse(item.isPublic());
        item.setPublic(true);
        assertTrue(item.isPublic());
    }

    @Test
    public void messageItem() {
        Message.MessageItem item = new Message.MessageItem();
        assertFalse(item.isPublic());
        item.setPublic(true);
        assertTrue(item.isPublic());
    }

    @Test
    public void teamProfileOptions() {
        Team.ProfileOptions item = new Team.ProfileOptions();
        assertFalse(item.isProtected());
        item.setProtected(true);
        assertTrue(item.isProtected());
    }

    @Test
    public void event() {
        MessageEvent event = new MessageEvent();
        assertNull(event.getSubtype());
        MessageBotEvent messageBotEvent = new MessageBotEvent();
        assertEquals("bot_message", messageBotEvent.getSubtype());
    }

    @Test
    public void views() {
        Map<String, Map<String, ViewState.Value>> values = new HashMap<>();
        Map<String, ViewState.Value> block = new HashMap<>();
        ViewState.Value value = new ViewState.Value();
        value.setValue("something");
        block.put("action", value);
        values.put("block", block);
        ViewState state = viewState(r -> r.values(values));
        assertNotNull(state);
        assertNotNull(state.getValues());
        assertEquals("something", state.getValues().get("block").get("action").getValue());
    }

    @Test
    public void blocks() {
        ActionsBlock actions = actions(r -> r.blockId("block"));
        assertNotNull(actions);

        ContextBlock context = context(r -> r.blockId("block"));
        assertNotNull(context);

        DividerBlock divider = divider();
        assertNotNull(divider);

        DividerBlock divider2 = divider(r -> r.blockId("block"));
        assertNotNull(divider2);
    }

    @Test
    public void blockElements() {
        List<RichTextElement> richTextElements = asRichTextElements();
        assertNotNull(richTextElements);

        OverflowMenuElement overflowMenu = overflowMenu(r -> r.actionId("action"));
        assertNotNull(overflowMenu);

        PlainTextInputElement textInput = plainTextInput(r -> r.actionId("action"));
        assertNotNull(textInput);

        DatePickerElement datePicker = datePicker(r -> r.actionId("action"));
        assertNotNull(datePicker);

        RadioButtonsElement radioButtons = radioButtons(r -> r.actionId("action"));
        assertNotNull(radioButtons);

        ChannelsSelectElement channelsSelect = channelsSelect(r -> r.actionId("action"));
        assertNotNull(channelsSelect);

        ConversationsSelectElement conversationsSelect = conversationsSelect(r -> r.actionId("action"));
        assertNotNull(conversationsSelect);

        ExternalSelectElement externalSelect = externalSelect(r -> r.actionId("action"));
        assertNotNull(externalSelect);

        StaticSelectElement staticSelect = staticSelect(r -> r.actionId("action"));
        assertNotNull(staticSelect);

        UsersSelectElement usersSelect = usersSelect(r -> r.actionId("action"));
        assertNotNull(usersSelect);

        MultiChannelsSelectElement multiChannelsSelect = multiChannelsSelect(r -> r.actionId("action"));
        assertNotNull(multiChannelsSelect);

        MultiConversationsSelectElement multiConversationsSelect = multiConversationsSelect(r -> r.actionId("action"));
        assertNotNull(multiConversationsSelect);

        MultiExternalSelectElement multiExternalSelect = multiExternalSelect(r -> r.actionId("action"));
        assertNotNull(multiExternalSelect);

        MultiStaticSelectElement multiStaticSelect = multiStaticSelect(r -> r.actionId("action"));
        assertNotNull(multiStaticSelect);

        MultiUsersSelectElement multiUsersSelect = multiUsersSelect(r -> r.actionId("action"));
        assertNotNull(multiUsersSelect);

        RichTextListElement richTextList = richTextList(r -> r.style("link"));
        assertNotNull(richTextList);

        RichTextPreformattedElement richTextPreformatted = richTextPreformatted(r -> r);
        assertNotNull(richTextPreformatted);

        RichTextQuoteElement richTextQuote = richTextQuote(r -> r);
        assertNotNull(richTextQuote);

        RichTextSectionElement richTextSection = richTextSection(r -> r);
        assertNotNull(richTextSection);
    }

    @Test
    public void blockCompositions() {
        PlainTextObject pto = plainText("text", false);
        assertNotNull(pto);

        MarkdownTextObject mto = markdownText(r -> r.text("foo").verbatim(false));
        assertNotNull(mto);

        OptionObject opt = option(plainText("text"), "value");
        assertNotNull(opt);

        List<OptionGroupObject> optionGroups = asOptionGroups(BlockCompositions.optionGroup(r -> r.label(plainText("hi"))));
        assertNotNull(optionGroups);

        List<OptionObject> options = asOptions(BlockCompositions.option(r -> r.text(plainText("foo")).value("bar")));
        assertNotNull(options);

        ConfirmationDialogObject confirmationDialog = confirmationDialog(r -> r.text(markdownText("foo")));
        assertNotNull(confirmationDialog);
    }

    @Test
    public void blockCompositions_radio_buttons_checkboxes() {
        MarkdownTextObject mto = markdownText(r -> r.text("foo").verbatim(false));
        assertNotNull(mto);

        OptionObject opt = option(mto, "value");
        assertNotNull(opt);

        RadioButtonsElement radioButtons = radioButtons(r -> r.actionId("a").initialOption(opt).options(Arrays.asList(opt)));
        assertNotNull(radioButtons);

        CheckboxesElement checkboxes = checkboxes(r -> r.actionId("a").initialOptions(Arrays.asList(opt)).options(Arrays.asList(opt)));
        assertNotNull(checkboxes);
    }

}
