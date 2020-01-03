package test_locally.api.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

public class FileReader {

    private final String basePath;

    public FileReader(String basePath) {
        this.basePath = basePath;
    }

    public String readWholeAsString(String path) throws IOException {
        return Files.readAllLines(new File(basePath + path).toPath(), UTF_8).stream().collect(joining());
    }
}
