package test_locally.sample_json_generation;

import com.github.seratch.jslack.app_backend.message_actions.payload.MessageActionPayload;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.ObjectInitializer;
import util.sample_json_generation.ObjectToJsonDumper;
import util.sample_json_generation.SampleObjects;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class MessageActionsPayloadDumpTest {

    ObjectToJsonDumper dumper = new ObjectToJsonDumper("./json-logs/samples/app-backend/message-actions");

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

    private MessageActionPayload buildMessageActionPayload() {
        return MessageActionPayload.builder()
                .team(new MessageActionPayload.Team())
                .user(new MessageActionPayload.User())
                .channel(new MessageActionPayload.Channel())
                .message(SampleObjects.Message)
                .build();
    }

}
