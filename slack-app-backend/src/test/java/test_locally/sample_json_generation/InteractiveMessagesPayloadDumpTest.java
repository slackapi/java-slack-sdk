package test_locally.sample_json_generation;

import com.slack.api.app_backend.interactive_components.payload.AttachmentActionPayload;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.ObjectInitializer;
import util.sample_json_generation.ObjectToJsonDumper;
import util.sample_json_generation.SampleObjects;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class InteractiveMessagesPayloadDumpTest {

    ObjectToJsonDumper dumper = new ObjectToJsonDumper("../json-logs/samples/app-backend/interactive-messages");

    @Test
    public void dumpAll() throws Exception {
        List<Object> payloads = Arrays.asList(
                buildAttachmentActionPayload(),
                buildBlockActionPayload()
        );
        for (Object payload : payloads) {
            ObjectInitializer.initProperties(payload);
            dumper.dump(payload.getClass().getSimpleName(), payload);
        }
    }

    private AttachmentActionPayload buildAttachmentActionPayload() {
        List<AttachmentActionPayload.Action> actions = Arrays.asList(
                new AttachmentActionPayload.Action()
        );
        AttachmentActionPayload.OriginalMessage originalMessage = new AttachmentActionPayload.OriginalMessage();
        AttachmentActionPayload.Attachment attachment = new AttachmentActionPayload.Attachment();
        attachment.setActions(Arrays.asList(new AttachmentActionPayload.AttachmentAction()));
        attachment.setFields(Arrays.asList(new AttachmentActionPayload.AttachmentField()));
        originalMessage.setAttachments(Arrays.asList(attachment));
        return AttachmentActionPayload.builder()
                .actions(actions)
                .team(new AttachmentActionPayload.Team())
                .user(new AttachmentActionPayload.User())
                .channel(new AttachmentActionPayload.Channel())
                .originalMessage(originalMessage)
                .build();
    }

    private BlockActionPayload buildBlockActionPayload() {
        BlockActionPayload.Action action = new BlockActionPayload.Action();
        action.setConfirm(new ConfirmationDialogObject());
        action.getConfirm().setConfirm(new PlainTextObject());
        action.getConfirm().setDeny(new PlainTextObject());
        action.getConfirm().setTitle(new PlainTextObject());
        action.getConfirm().setText(new PlainTextObject());
        List<BlockActionPayload.Action> actions = Arrays.asList(action);
        return BlockActionPayload.builder()
                .team(new BlockActionPayload.Team())
                .user(new BlockActionPayload.User())
                .channel(new BlockActionPayload.Channel())
                .actions(actions)
                .container(new BlockActionPayload.Container())
                .message(SampleObjects.Message)
                .build();
    }

}
