package testing.json;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Slf4j
public class JsonDataRecorder {

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private String outputDirectory;

    public JsonDataRecorder(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void writeMergedResponse(Response response, String body) throws IOException {
        if (body == null || (!body.trim().startsWith("{") && !body.trim().startsWith("["))) {
            // given body is not in JSON format
            return;
        }
        JsonParser jsonParser = new JsonParser();
        String json = null;
        String path = response.request().url().url().getPath();
        try {
            Path jsonFilePath = new File(toFilePath(path)).toPath();
            json = Files.readAllLines(jsonFilePath, UTF_8).stream().collect(Collectors.joining());
        } catch (NoSuchFileException e) {
        }
        if (json == null || json.trim().isEmpty()) {
            json = "{}";
        }
        JsonElement obj = jsonParser.parse(json);
        JsonObject result = obj.getAsJsonObject();
        try {
            JsonObject jsonObj = jsonParser.parse(body).getAsJsonObject();
            MergeJsonBuilder.mergeJsonObjects(result, MergeJsonBuilder.ConflictStrategy.PREFER_NON_NULL, jsonObj);
        } catch (MergeJsonBuilder.JsonConflictException e) {
            log.warn("Failed to merge JSON objects because {}", e.getMessage(), e);
        }
        json = new GsonBuilder().setPrettyPrinting().create().toJson(result);
        Path filePath = new File(toFilePath(path)).toPath();
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, json.getBytes(UTF_8));
    }

    private String toFilePath(String path) {
        return outputDirectory + "/" + path + ".json";
    }

}
