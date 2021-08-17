package test_locally.sample_json_generation;

import com.slack.api.SlackConfig;
import com.slack.api.methods.MethodsRateLimits;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class RateLimitsDumpTest {
    // This test class generates JSON data covering the rate limits for all public endpoints.

    static String outputDirectory = "../metadata";

    public void dump(String path, Object obj) throws IOException {
        SlackConfig config = new SlackConfig();
        config.setPrettyResponseLoggingEnabled(true);
        String json = GsonFactory.createSnakeCase(config).toJson(obj);
        Path filePath = new File(toFilePath(path)).toPath();
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, json.getBytes(StandardCharsets.UTF_8));
    }

    private String toFilePath(String path) {
        return outputDirectory + "/" + path;
    }

    @Test
    public void webApi() throws Exception {
        dump("web-api/rate_limit_tiers.json", new MethodsRateLimits().toMap());
    }

}
