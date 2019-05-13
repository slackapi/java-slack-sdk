package test_locally.sample_json_generation;

import com.github.seratch.jslack.app_backend.dialogs.payload.DialogCancellationPayload;
import com.github.seratch.jslack.app_backend.dialogs.payload.DialogSubmissionPayload;
import com.github.seratch.jslack.app_backend.dialogs.payload.DialogSuggestionPayload;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.ObjectInitializer;
import util.sample_json_generation.ObjectToJsonDumper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class DialogsPayloadDumpTest {

    ObjectToJsonDumper dumper = new ObjectToJsonDumper("../json-logs/samples/app-backend/dialogs");

    @Test
    public void dumpAll() throws Exception {
        List<Object> payloads = Arrays.asList(
                buildDialogCancellationPayload(),
                buildDialogSubmissionPayload(),
                buildDialogSuggestionPayload()
        );
        for (Object payload : payloads) {
            ObjectInitializer.initProperties(payload);
            dumper.dump(payload.getClass().getSimpleName(), payload);
        }
    }

    private DialogCancellationPayload buildDialogCancellationPayload() {
        return DialogCancellationPayload.builder()
                .team(new DialogCancellationPayload.Team())
                .user(new DialogCancellationPayload.User())
                .channel(new DialogCancellationPayload.Channel())
                .build();
    }

    private DialogSubmissionPayload buildDialogSubmissionPayload() {
        return DialogSubmissionPayload.builder()
                .team(new DialogSubmissionPayload.Team())
                .user(new DialogSubmissionPayload.User())
                .channel(new DialogSubmissionPayload.Channel())
                // let users to call payload.submission["field_name"]
                .submission(new HashMap<>())
                .build();
    }

    private DialogSuggestionPayload buildDialogSuggestionPayload() {
        return DialogSuggestionPayload.builder()
                .team(new DialogSuggestionPayload.Team())
                .user(new DialogSuggestionPayload.User())
                .channel(new DialogSuggestionPayload.Channel())
                .build();
    }

}
