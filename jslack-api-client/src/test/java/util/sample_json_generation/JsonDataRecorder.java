package util.sample_json_generation;

import com.github.seratch.jslack.SlackConfig;
import com.github.seratch.jslack.api.scim.model.User;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import util.ObjectInitializer;

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
import java.util.stream.Collectors;

import static util.sample_json_generation.SampleObjects.*;

@Slf4j
public class JsonDataRecorder {

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private SlackConfig config;
    private String outputDirectory;

    public JsonDataRecorder(SlackConfig config, String outputDirectory) {
        this.config = config;
        this.outputDirectory = outputDirectory;
    }

    public void writeMergedResponse(Response response, String body) throws IOException {
        String path = response.request().url().url().getPath();
        writeMergedJsonData(path, body);
    }

    public void writeMergedJsonData(String path, String body) throws IOException {
        if (body == null || (!body.trim().startsWith("{") && !body.trim().startsWith("["))) {
            // given body is not in JSON format
            return;
        }
        JsonParser jsonParser = new JsonParser();
        String json = null;
        if (path.startsWith("/scim")) {
            path = path.replaceFirst("/\\w{9}$", "/000000000");
        }
        try {
            Path jsonFilePath = new File(toRawFilePath(path)).toPath();
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
            MergeJsonBuilder.mergeJsonObjects(result, MergeJsonBuilder.ConflictStrategy.PREFER_FIRST_OBJ, jsonObj);
        } catch (MergeJsonBuilder.JsonConflictException e) {
            log.warn("Failed to merge JSON objects because {}", e.getMessage(), e);
        }
        Path rawFilePath = new File(toRawFilePath(path)).toPath();
        Files.createDirectories(rawFilePath.getParent());
        json = gson().toJson(result);
        Files.write(rawFilePath, json.getBytes(UTF_8));

        for (Map.Entry<String, JsonElement> entry : result.entrySet()) {
            scanToNormalizeStringValues(result, entry.getKey(), entry.getValue());
        }
        if (path.startsWith("/scim")) {
            if (result.get("Resources") != null) {
                for (JsonElement resource : result.get("Resources").getAsJsonArray()) {
                    JsonObject resourceObj = resource.getAsJsonObject();
                    if (resourceObj.get("userName") != null) {
                        initializeSCIMUser(resourceObj);
                    }
                }
            } else if (result.get("schemas") != null && result.get("userName") != null) {
                initializeSCIMUser(result);
            } else if (result.get("Errors") != null) {
                initializeSCIMUser(result);
            }
            json = gson().toJson(result);
            Path filePath = new File(toMaskedFilePath(path).replaceFirst("/\\w{9}.json$", "/000000000.json")).toPath();
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, json.getBytes(UTF_8));

        } else {
            addCommonPropertiesAtTopLevel(result);

            json = gson().toJson(result);
            Path filePath = new File(toMaskedFilePath(path)).toPath();
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, json.getBytes(UTF_8));
        }
    }

    private void initializeSCIMUser(JsonObject resourceObj) {
        if (resourceObj.get("addresses") == null) {
            resourceObj.add("addresses", new JsonArray());
        }
        {
            JsonArray objects = resourceObj.get("addresses").getAsJsonArray();
            clearAllElements(objects);
            User.Address sampleObject = ObjectInitializer.initProperties(new User.Address());
            objects.add(GsonFactory.createCamelCase(config).toJsonTree(sampleObject));
        }
        if (resourceObj.get("emails") == null) {
            resourceObj.add("emails", new JsonArray());
        }
        {
            JsonArray objects = resourceObj.get("emails").getAsJsonArray();
            clearAllElements(objects);
            User.Email sampleObject = ObjectInitializer.initProperties(new User.Email());
            objects.add(GsonFactory.createCamelCase(config).toJsonTree(sampleObject));
        }
        if (resourceObj.get("phoneNumbers") == null) {
            resourceObj.add("phoneNumbers", new JsonArray());
        }
        {
            JsonArray objects = resourceObj.get("phoneNumbers").getAsJsonArray();
            clearAllElements(objects);
            User.PhoneNumber sampleObject = ObjectInitializer.initProperties(new User.PhoneNumber());
            objects.add(GsonFactory.createCamelCase(config).toJsonTree(sampleObject));
        }
        if (resourceObj.get("photos") == null) {
            resourceObj.add("photos", new JsonArray());
        }
        {
            JsonArray objects = resourceObj.get("photos").getAsJsonArray();
            clearAllElements(objects);
            User.Photo sampleObject = ObjectInitializer.initProperties(new User.Photo());
            objects.add(GsonFactory.createCamelCase(config).toJsonTree(sampleObject));
        }
        if (resourceObj.get("roles") == null) {
            resourceObj.add("roles", new JsonArray());
        }
        {
            JsonArray objects = resourceObj.get("roles").getAsJsonArray();
            clearAllElements(objects);
            User.Role sampleObject = ObjectInitializer.initProperties(new User.Role());
            objects.add(GsonFactory.createCamelCase(config).toJsonTree(sampleObject));
        }
    }

    private static void clearAllElements(JsonArray objects) {
        for (int i = 0; i < objects.size(); i++) {
            objects.remove(i);
        }
    }

    private static final List<String> COMMON_TOP_LEVEL_PROPERTY_NAMES = Arrays.asList(
            "ok",
            "error",
            "needed",
            "provided"
    );

    private static void addCommonPropertiesAtTopLevel(JsonObject root) {
        List<String> missingPropNames = new ArrayList<>(COMMON_TOP_LEVEL_PROPERTY_NAMES);
        for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
            missingPropNames.remove(entry.getKey());
        }
        for (String missingPropName : missingPropNames) {
            if (missingPropName.equals("ok")) {
                root.add(missingPropName, new JsonPrimitive(false));
            } else {
                root.add(missingPropName, new JsonPrimitive(""));
            }
        }
    }


    private MergeJsonBuilder.ConflictStrategy CONFLICT_STRATEGY = MergeJsonBuilder.ConflictStrategy.PREFER_FIRST_OBJ;

    private void scanToNormalizeStringValues(JsonElement parent, String name, JsonElement element) {
        if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            if (name != null && name.equals("attachments")) {
                for (int idx = 0; idx < array.size(); idx++) {
                    array.remove(idx);
                }
                for (JsonElement attachment : Json.Attachments) {
                    array.add(attachment);
                }
            } else if (name != null && name.equals("blocks")) {
                for (int idx = 0; idx < array.size(); idx++) {
                    array.remove(idx);
                }
                for (JsonElement block : Json.Blocks) {
                    array.add(block);
                }
            }
            if (array.size() == 0) {
                List<String> addressNames = Arrays.asList("from", "to", "cc");
                if (addressNames.contains(name)) {
                    Address address = new Address();
                    address.setAddress("");
                    address.setName("");
                    address.setOriginal("");
                    JsonElement elem = GsonFactory.createSnakeCase().toJsonTree(address);
                    array.add(elem);
                } else {
                    array.add(""); // in most cases, empty array can have string values
                }
            } else {
                JsonElement first = array.get(0);
                if (first.isJsonPrimitive()) {
                    array.set(0, normalize(null, first.getAsJsonPrimitive()));
                    int size = array.size();
                    if (size > 1) {
                        for (int idx = size - 1; idx > 0; idx--) {
                            array.remove(idx);
                        }
                    }
                } else {
                    for (JsonElement child : array) {
                        scanToNormalizeStringValues(array, null, child);
                    }
                    if (array.size() >= 2) {
                        for (int idx = 1; idx < array.size(); idx++) {
                            JsonElement elem = array.get(idx);
                            if (elem.isJsonArray()) {
                                for (JsonElement child : elem.getAsJsonArray()) {
                                    scanToNormalizeStringValues(elem, null, child);
                                }
                            } else {
                                try {
                                    MergeJsonBuilder.mergeJsonObjects(first.getAsJsonObject(), CONFLICT_STRATEGY, elem.getAsJsonObject());
                                } catch (MergeJsonBuilder.JsonConflictException e) {
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
            if (entries.size() == 1 && entries.get(0).getKey().matches("^[A-Z].{8}$")) {
                // if the child element seems to be a Map object using identifiers (e.g., channel id, user id) as keys
                // the Map object should have 2+ elements to allow quicktype generate preferable code.
                Map.Entry<String, JsonElement> first = entries.get(0);
                element.getAsJsonObject().add(first.getKey() + "_", first.getValue());
            }
            for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                scanToNormalizeStringValues(element, entry.getKey(), entry.getValue());
            }
        } else if (element.isJsonNull()) {
            return;
        } else if (!parent.isJsonArray() && element.isJsonPrimitive()) {
            JsonPrimitive prim = element.getAsJsonPrimitive();
            parent.getAsJsonObject().add(name, normalize(name, prim));
        }
    }

    private JsonElement normalize(String name, JsonPrimitive original) {
        if (original.isString()) {
            return new JsonPrimitive(normalizeString(name, original.getAsString()));
        } else if (original.isBoolean()) {
            return new JsonPrimitive(false);
        } else if (original.isNumber()) {
            return new JsonPrimitive(12345);
        } else {
            return JsonNull.INSTANCE;
        }
    }

    private String normalizeString(String name, String value) {
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

    private String toRawFilePath(String path) {
        return outputDirectory + "/raw/" + path + ".json";
    }

    private String toMaskedFilePath(String path) {
        return outputDirectory + "/samples/" + path + ".json";
    }

    @Data
    public static class Address {
        private String address;
        private String name;
        private String original;
    }

}
