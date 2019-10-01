package test_locally.sample_json_generation;

import com.github.seratch.jslack.api.model.view.View;
import com.github.seratch.jslack.app_backend.views.payload.ViewClosedPayload;
import com.github.seratch.jslack.app_backend.views.payload.ViewSubmissionPayload;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.ObjectInitializer;
import util.sample_json_generation.ObjectToJsonDumper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class ViewPayloadDumpTest {

    ObjectToJsonDumper dumper = new ObjectToJsonDumper("../json-logs/samples/app-backend/views");

    @Test
    public void dumpAll() throws Exception {
        List<Object> payloads = Arrays.asList(
                buildViewSubmissionPayload(),
                buildViewClosedPayload()
        );
        for (Object payload : payloads) {
            ObjectInitializer.initProperties(payload);
            dumper.dump(payload.getClass().getSimpleName(), payload);
        }
    }

    private ViewSubmissionPayload buildViewSubmissionPayload() {
        return ViewSubmissionPayload.builder()
                .team(new ViewSubmissionPayload.Team())
                .user(new ViewSubmissionPayload.User())
                .view(new View())
                .build();
    }

    private ViewClosedPayload buildViewClosedPayload() {
        return ViewClosedPayload.builder()
                .team(new ViewClosedPayload.Team())
                .user(new ViewClosedPayload.User())
                .view(new View())
                .build();
    }
}
