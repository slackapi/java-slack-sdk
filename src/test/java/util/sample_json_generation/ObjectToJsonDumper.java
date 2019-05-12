package util.sample_json_generation;

import com.github.seratch.jslack.SlackConfig;
import com.github.seratch.jslack.common.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class ObjectToJsonDumper {

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private String outputDirectory;

    public ObjectToJsonDumper(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void dump(String path, Object obj) throws IOException {
        SlackConfig config = new SlackConfig();
        config.setPrettyResponseLoggingEnabled(true);
        String json = GsonFactory.createSnakeCase(config).toJson(obj);
        Path filePath = new File(toFilePath(path)).toPath();
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, json.getBytes(UTF_8));
    }

    private String toFilePath(String path) {
        return outputDirectory + "/" + path + ".json";
    }

}
