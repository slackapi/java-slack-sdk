package util.sample_json_generation;

import com.slack.api.util.http.listener.HttpResponseListener;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.stream.Collectors.joining;

@Slf4j
public class JsonDataRecordingListener extends HttpResponseListener {

    private final CopyOnWriteArrayList<String> remaining = new CopyOnWriteArrayList<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public boolean isAllDone() {
        if (remaining.size() > 0) {
            log.debug("remaining: {} ({})",
                    remaining.size(),
                    remaining.stream().map(r -> "`" + r.substring(0, 30) + "...`").collect(joining("[", ",", "]")));
        }
        return remaining.size() == 0;
    }

    @Override
    public void accept(State state) {
        executorService.submit(() -> {
            String bodyPrefix = state.getParsedResponseBody().substring(0, 100) + "...";
            if (remaining.contains(bodyPrefix)) {
                // skip the same content
                return;
            }

            try {
                remaining.add(bodyPrefix);
                log.debug("Started for {} - remaining: {}", bodyPrefix, remaining.size());
                JsonDataRecorder recorder = new JsonDataRecorder(state.getConfig(), "../json-logs");
                recorder.writeMergedResponse(state.getResponse(), state.getParsedResponseBody());
            } catch (IOException e) {
                log.error("Failed to write JSON files because {}", e.getMessage(), e);
            } finally {
                remaining.remove(bodyPrefix);
                log.debug("Finished for {} - remaining: {}", bodyPrefix, remaining.size());
            }
        });
    }
}
