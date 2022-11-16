package test_with_remote_apis.sample_json_generation;

import com.google.gson.*;
import com.slack.api.model.FileComment;
import com.slack.api.model.Message;
import com.slack.api.model.admin.AppRequest;
import com.slack.api.util.json.GsonFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@Slf4j
public class EventDataRecorder {

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private String outputDirectory;

    public EventDataRecorder(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    private static String toPayloadClassName(String eventType) {
        StringBuilder sb = new StringBuilder();
        char[] chars = eventType.toCharArray();
        sb.append(Character.toUpperCase(chars[0]));
        for (int i = 1; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_' && i + 1 < chars.length) {
                sb.append(Character.toUpperCase(chars[i + 1]));
                i++;
            } else {
                sb.append(c);
            }
        }
        return sb.toString() + "Payload";
    }

    private final List<String> writeMergedResponse_excludedClassNames = Arrays.asList(
            "AppMentionPayload",
            "MessagePayload",
            "PinAddedPayload",
            "PinRemovedPayload",
            "StarAddedPayload",
            "StarRemovedPayload"
    );

    public void writeMergedResponse(String eventType, String body) throws IOException {
        String className = toPayloadClassName(eventType);
        // TODO: This recorder doesn't generate JSON data that is compatible with
        // test_locally.app_backend.events.payload.SamplePayloadParsingTest
        if (writeMergedResponse_excludedClassNames.contains(className)) {
            return;
        }
        writeMergedJsonData(className, body);
    }

    public void writeMergedJsonData(String payloadClassName, String body) throws IOException {
        JsonElement newJson = JsonParser.parseString(body);
        String existingJson = null;
        try {
            Path jsonFilePath = new File(toRawFilePath(payloadClassName)).toPath();
            existingJson = Files.readAllLines(jsonFilePath, UTF_8).stream().collect(joining());
        } catch (NoSuchFileException e) {
        }
        if (existingJson == null || existingJson.trim().isEmpty()) {
            if (body.trim().startsWith("[")) {
                existingJson = body;
            } else {
                existingJson = "{}";
            }
        }
        JsonElement jsonElem = JsonParser.parseString(existingJson);
        JsonObject jsonObj = jsonElem.isJsonObject() ? jsonElem.getAsJsonObject() : null;

        if (newJson.isJsonObject()) {
            try {
                JsonObject newJsonObj = newJson.getAsJsonObject();
                MergeJsonBuilder.mergeJsonObjects(jsonObj, MergeJsonBuilder.ConflictStrategy.PREFER_FIRST_OBJ, newJsonObj);
            } catch (MergeJsonBuilder.JsonConflictException e) {
                log.warn("Failed to merge JSON objects because {}", e.getMessage(), e);
            }
        }
        Path rawFilePath = new File(toRawFilePath(payloadClassName)).toPath();
        Files.createDirectories(rawFilePath.getParent());
        existingJson = gson().toJson(jsonElem);
        Files.write(rawFilePath, existingJson.getBytes(UTF_8));

        if (jsonElem.isJsonObject() && jsonObj != null) {
            for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
                scanToNormalizeValues(jsonObj, entry.getKey(), entry.getValue());
            }
            JsonElement eventContext = jsonElem.getAsJsonObject().get("event_context");
            if (eventContext == null) {
                // event_context should always exist. But it's not always true in real data.
                jsonElem.getAsJsonObject().add("event_context", new JsonPrimitive(""));
            }
            existingJson = gson().toJson(jsonObj);
            Path filePath = new File(toMaskedFilePath(payloadClassName)).toPath();
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, existingJson.getBytes(UTF_8));
        } else if (jsonElem.isJsonArray()) {
            JsonArray jsonArray = jsonElem.getAsJsonArray();
            scanToNormalizeValues(null, null, jsonArray);
            existingJson = gson().toJson(jsonArray);
            Path filePath = new File(toMaskedFilePath(payloadClassName)).toPath();
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, existingJson.getBytes(UTF_8));
        }
    }

    private static void clearAllElements(JsonArray objects) {
        for (int i = 0; i < objects.size(); i++) {
            objects.remove(i);
        }
    }

    private MergeJsonBuilder.ConflictStrategy CONFLICT_STRATEGY = MergeJsonBuilder.ConflictStrategy.PREFER_FIRST_OBJ;

    private void scanToNormalizeValues(JsonElement parent, String name, JsonElement element) {
        if (name != null && name.equals("type")) {
            return; // keep it as-is
        }
        Gson gson = GsonFactory.createSnakeCase();
        if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            if (name != null && name.equals("attachments")) {
                for (int idx = 0; idx < array.size(); idx++) {
                    array.remove(idx);
                }
                for (JsonElement attachment : SampleObjects.Json.Attachments) {
                    array.add(attachment);
                }
            } else if (name != null && name.equals("blocks")) {
                for (int idx = 0; idx < array.size(); idx++) {
                    array.remove(idx);
                }
                for (JsonElement block : SampleObjects.Json.Blocks) {
                    array.add(block);
                }
            } else if (name != null && name.equals("replies")) {
                for (int idx = 0; idx < array.size(); idx++) {
                    array.remove(idx);
                }
                array.add(gson.toJsonTree(ObjectInitializer.initProperties(new Message.MessageRootReply())));
            } else if (name != null && name.equals("comments")) {
                for (int idx = 0; idx < array.size(); idx++) {
                    array.remove(idx);
                }
                array.add(gson.toJsonTree(ObjectInitializer.initProperties(new FileComment())));
            }
            if (array.size() == 0) {
                List<String> addressNames = Arrays.asList("from", "to", "cc");
                if (name != null) {
                    if (addressNames.contains(name)) {
                        Address address = new Address();
                        address.setAddress("");
                        address.setName("");
                        address.setOriginal("");
                        JsonElement elem = gson.toJsonTree(address);
                        array.add(elem);
                    } else if (name.equals("app_requests")) {
                        array.add(gson.toJsonTree(ObjectInitializer.initProperties(new AppRequest())));
                    } else if (name.equals("replies")) {
                        array.add(gson.toJsonTree(ObjectInitializer.initProperties(new Message.MessageRootReply())));
                    } else if (name.equals("comments")) {
                        array.add(gson.toJsonTree(ObjectInitializer.initProperties(new FileComment())));
                    } else {
                        array.add(""); // in most cases, empty array can have string values
                    }
                }
            } else {
                JsonElement first = array.get(0);
                if (first.isJsonPrimitive()) {
                    array.set(0, normalize(first.getAsJsonPrimitive()));
                    int size = array.size();
                    if (size > 1) {
                        for (int idx = size - 1; idx > 0; idx--) {
                            array.remove(idx);
                        }
                    }
                } else {
                    for (JsonElement child : array) {
                        scanToNormalizeValues(array, null, child);
                    }
                    if (array.size() >= 2) {
                        for (int idx = 1; idx < array.size(); idx++) {
                            JsonElement elem = array.get(idx);
                            if (elem.isJsonArray()) {
                                for (JsonElement child : elem.getAsJsonArray()) {
                                    scanToNormalizeValues(elem, null, child);
                                }
                            } else {
                                try {
                                    MergeJsonBuilder.mergeJsonObjects(first.getAsJsonObject(), CONFLICT_STRATEGY, elem.getAsJsonObject());
                                } catch (MergeJsonBuilder.JsonConflictException e) {
                                    log.error("Failed to merge {} into {}", elem, first);
                                } catch (IllegalStateException e) {
                                    log.error("Failed to merge {} into {}", elem, first);
                                }
                            }
                        }
                    }
                }
            }
            int size = array.size();
            if (size > 1) {
                for (int idx = size - 1; idx > 0; idx--) {
                    array.remove(idx);
                }
            }
        } else if (element.isJsonObject()) {
            List<Map.Entry<String, JsonElement>> entries = new ArrayList<>(element.getAsJsonObject().entrySet());
            if (entries.size() > 0) {
                if (entries.get(0).getKey().matches("^[A-Z].{8}$")) {
                    Map.Entry<String, JsonElement> first = entries.get(0);
                    for (Map.Entry<String, JsonElement> entry : entries) {
                        element.getAsJsonObject().remove(entry.getKey());
                    }
                    // if the child element seems to be a Map object using identifiers (e.g., channel id, user id) as keys
                    // the Map object should have 2+ elements to allow quicktype generate preferable code.
                    element.getAsJsonObject().add(first.getKey().substring(0, 1) + "00000000", first.getValue());
                    element.getAsJsonObject().add(first.getKey().substring(0, 1) + "00000001", first.getValue());
                }
                if (name != null && name.equals("emoji")) {
                    Map.Entry<String, JsonElement> first = entries.get(0);
                    for (Map.Entry<String, JsonElement> entry : entries) {
                        element.getAsJsonObject().remove(entry.getKey());
                    }
                    element.getAsJsonObject().add("emoji", first.getValue());
                    element.getAsJsonObject().add("emoji_", first.getValue());
                }
            }
            for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                scanToNormalizeValues(element, entry.getKey(), entry.getValue());
            }
        } else if (element.isJsonNull()) {
            return;
        } else if (!parent.isJsonArray() && element.isJsonPrimitive()) {
            JsonPrimitive prim = element.getAsJsonPrimitive();
            parent.getAsJsonObject().add(name, normalize(prim));
        }
    }

    private JsonElement normalize(JsonPrimitive original) {
        if (original.isString()) {
            return new JsonPrimitive(normalizeString(original.getAsString()));
        } else if (original.isBoolean()) {
            return new JsonPrimitive(false);
        } else if (original.isNumber()) {
            return new JsonPrimitive(12345);
        } else {
            return JsonNull.INSTANCE;
        }
    }

    private String normalizeString(String value) {
        if (value == null) {
            return null;
        }
        if (value.matches("^[\\d]+\\.[\\d]+$")) {
            return "0000000000.000000"; // ts
        }
        if (value.matches("^[\\d]{10}$")) {
            return "1234567890"; // epoch
        }
        // bmV4dF90czoxNTU2MDYwMTAzMDAwNDAw
        if (value.startsWith("http://") || value.startsWith("https://")) {
            return "https://www.example.com/";
        }
        if (value.matches("^[A-Z][A-Z0-9]{8}$")) {
            return value.substring(0, 1) + "00000000"; // identifier
        }
        if (value.matches("^\\d$")) {
            return "0"; // other numbers
        } else if (value.matches("^[\\d]+$")) {
            return "12345"; // other numbers
        }
        return "";
    }

    private Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    private String toRawFilePath(String payloadClassName) {
        return outputDirectory + "/raw/events/" + payloadClassName + ".json";
    }

    private String toMaskedFilePath(String payloadClassName) {
        return outputDirectory + "/samples/events/" + payloadClassName + ".json";
    }

    @Data
    public static class Address {
        private String address;
        private String name;
        private String original;
    }

}
