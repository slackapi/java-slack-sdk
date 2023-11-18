package test_locally.sample_json_generation;

import com.slack.api.app_backend.dialogs.payload.DialogCancellationPayload;
import com.slack.api.app_backend.dialogs.payload.DialogSubmissionPayload;
import com.slack.api.app_backend.dialogs.payload.DialogSuggestionPayload;
import com.slack.api.app_backend.dialogs.payload.Team;
import com.slack.api.app_backend.dialogs.payload.Channel;
import com.slack.api.app_backend.dialogs.payload.User;

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
                .team(new Team())
                .user(new User())
                .channel(new Channel())
                .build();
    }

    private DialogSubmissionPayload buildDialogSubmissionPayload() {
        return DialogSubmissionPayload.builder()
                .team(new Team())
                .user(new User())
                .channel(new Channel())
                // let users to call payload.submission["field_name"]
                .submission(new HashMap<>())
                .build();
    }

    private DialogSuggestionPayload buildDialogSuggestionPayload() {
        return DialogSuggestionPayload.builder()
                .team(new Team())
                .user(new User())
                .channel(new Channel())
                .build();
    }

}
