package test_locally.context;

import com.slack.api.bolt.context.builtin.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EqualityTest {

    @Test
    public void test() {
        assertEquals(new ActionContext(), new ActionContext());
        assertEquals(new AttachmentActionContext(), new AttachmentActionContext());
        assertEquals(new BlockSuggestionContext(), new BlockSuggestionContext());
        assertEquals(new DefaultContext(), new DefaultContext());
        assertEquals(new DialogCancellationContext(), new DialogCancellationContext());
        assertEquals(new DialogSubmissionContext(), new DialogSubmissionContext());
        assertEquals(new DialogSuggestionContext(), new DialogSuggestionContext());
        assertEquals(new EventContext(), new EventContext());
        assertEquals(new GlobalShortcutContext(), new GlobalShortcutContext());
        assertEquals(new MessageShortcutContext(), new MessageShortcutContext());
        assertEquals(new OAuthCallbackContext(), new OAuthCallbackContext());
        assertEquals(new SlashCommandContext(), new SlashCommandContext());
        assertEquals(new ViewSubmissionContext(), new ViewSubmissionContext());
    }
}
