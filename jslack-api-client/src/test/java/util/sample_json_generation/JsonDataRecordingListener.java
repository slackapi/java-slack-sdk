package util.sample_json_generation;

import com.github.seratch.jslack.common.http.listener.HttpResponseListener;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonDataRecordingListener extends HttpResponseListener {

    @Override
    public void accept(State state) {
        try {
            JsonDataRecorder recorder = new JsonDataRecorder(state.getConfig(),"../json-logs");
            recorder.writeMergedResponse(state.getResponse(), state.getParsedResponseBody());
        } catch (IOException e) {
            log.error("Failed to write JSON files because {}", e.getMessage(), e);
        }
    }
}