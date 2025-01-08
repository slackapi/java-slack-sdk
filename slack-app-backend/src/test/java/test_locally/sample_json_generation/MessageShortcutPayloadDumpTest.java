package test_locally.sample_json_generation;

import com.slack.api.app_backend.interactive_components.payload.MessageShortcutPayload;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import util.ObjectInitializer;
import util.sample_json_generation.ObjectToJsonDumper;
import util.sample_json_generation.SampleObjects;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class MessageShortcutPayloadDumpTest {

    ObjectToJsonDumper dumper = new ObjectToJsonDumper("../json-logs/samples/app-backend/interactive-components");

    @Ignore
    @Test
    public void dumpAll() throws Exception {
        List<Object> payloads = Arrays.asList(
                buildMessageActionPayload()
        );
        for (Object payload : payloads) {
            ObjectInitializer.initProperties(payload);
            dumper.dump(payload.getClass().getSimpleName(), payload);
        }
    }

    private MessageShortcutPayload buildMessageActionPayload() {
        return MessageShortcutPayload.builder()
                .team(new MessageShortcutPayload.Team())
                .user(new MessageShortcutPayload.User())
                .channel(new MessageShortcutPayload.Channel())
                .message(SampleObjects.Message)
                .build();
    }

}
