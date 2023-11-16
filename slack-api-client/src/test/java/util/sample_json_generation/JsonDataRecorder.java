package util.sample_json_generation;

import com.google.gson.*;
import com.slack.api.SlackConfig;
import com.slack.api.methods.response.admin.conversations.AdminConversationsBulkMoveResponse;
import com.slack.api.methods.response.admin.conversations.AdminConversationsSearchResponse;
import com.slack.api.methods.response.admin.users.AdminUsersSessionGetSettingsResponse;
import com.slack.api.methods.response.chat.scheduled_messages.ChatScheduledMessagesListResponse;
import com.slack.api.methods.response.team.profile.TeamProfileGetResponse;
import com.slack.api.model.*;
import com.slack.api.model.admin.*;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.scim.model.User;
import com.slack.api.status.v2.model.SlackIssue;
import com.slack.api.util.json.GsonFactory;
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
import java.util.*;
import java.util.stream.Collectors;

import static util.ObjectInitializer.initProperties;
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
        try {
            String path = response.request().url().url().getPath();
            String httpMethod = response.request().method();
            if (path.startsWith("/scim")) {
                if (httpMethod.toUpperCase(Locale.ENGLISH).equals("GET")) {
                    writeMergedJsonData(path, body);
                }
            } else if (path.equals("/audit/v1/logs")) {
                // As generating logs.json is not so easy,
                // test_with_remote_apis.audit.ApiTest generates the file
            } else {
                writeMergedJsonData(path, body);
            }
        } catch (Throwable e) {
            log.error("Failed to write merged response data: {}", body, e);
            throw e;
        }
    }

    public void writeMergedJsonData(String path, String body) throws IOException {
        if (body == null || (!body.trim().startsWith("{") && !body.trim().startsWith("["))) {
            // given body is not in JSON format
            return;
        }
        JsonElement newRawJson = JsonParser.parseString(body);

        // Adjust the path value for SCIM and Status API
        if (path.startsWith("/scim")) {
            path = path.replaceFirst("/\\w{9,11}$", "/00000000000");
        }
        // status api
        if (path.startsWith("/api/v1.0.0") || path.startsWith("/api/v2.0.0")) {
            path = "/status" + path;
        }

        // Read the existing JSON data
        String existingRawJson = null;
        try {
            Path jsonFilePath = new File(toRawFilePath(path)).toPath();
            existingRawJson = Files.readAllLines(jsonFilePath, UTF_8).stream().collect(Collectors.joining());
        } catch (NoSuchFileException e) {
        }
        if (existingRawJson == null || existingRawJson.trim().isEmpty()) {
            if (body.trim().startsWith("[")) {
                existingRawJson = body;
            } else {
                existingRawJson = "{}";
            }
        }
        JsonElement existingRawJsonElem = JsonParser.parseString(existingRawJson);
        JsonObject rawJsonObj = existingRawJsonElem.isJsonObject() ? existingRawJsonElem.getAsJsonObject().deepCopy() : null;

        // Merge the new JSON data into the existing raw data
        if (newRawJson.isJsonObject() && rawJsonObj != null) {
            try {
                JsonObject newJsonObj = newRawJson.getAsJsonObject().deepCopy();
                MergeJsonBuilder.mergeJsonObjects(rawJsonObj, MergeJsonBuilder.ConflictStrategy.PREFER_FIRST_OBJ, newJsonObj);
            } catch (MergeJsonBuilder.JsonConflictException e) {
                log.warn("Failed to merge JSON objects because {}", e.getMessage(), e);
            }
        }
        // Update the existing raw data
        Path rawFilePath = new File(toRawFilePath(path)).toPath();
        Files.createDirectories(rawFilePath.getParent());
        if (rawJsonObj != null) {
            Files.write(rawFilePath, GSON.toJson(rawJsonObj).getBytes(UTF_8));
        } else {
            Files.write(rawFilePath, GSON.toJson(existingRawJsonElem).getBytes(UTF_8));
        }

        if (existingRawJsonElem != null) {
            if (existingRawJsonElem.isJsonObject() && rawJsonObj != null) {
                // Normalize the property values in the JSON data
                for (Map.Entry<String, JsonElement> entry : rawJsonObj.entrySet()) {
                    scanToNormalizeValues(path, rawJsonObj, entry.getKey(), entry.getValue());
                }
                // Merge the raw data into the existing masked (sample) data
                String existingSampleJson = buildSampleJSONString(path);
                JsonElement existingSampleJsonElem = JsonParser.parseString(existingSampleJson);
                JsonObject mergedJsonObj = existingSampleJsonElem.isJsonObject() ?
                        existingSampleJsonElem.getAsJsonObject() : new JsonObject();
                try {
                    MergeJsonBuilder.mergeJsonObjects(mergedJsonObj, MergeJsonBuilder.ConflictStrategy.PREFER_FIRST_OBJ, rawJsonObj);
                } catch (MergeJsonBuilder.JsonConflictException e) {
                    log.warn("Failed to merge JSON objects because {}", e.getMessage(), e);
                }
                // Normalize the merged data (especially for cleaning up array data in nested JSON data)
                for (Map.Entry<String, JsonElement> entry : mergedJsonObj.entrySet()) {
                    scanToNormalizeValues(path, mergedJsonObj, entry.getKey(), entry.getValue());
                }

                if (path.startsWith("/scim")) {
                    writeSCIMResponseData(path, mergedJsonObj);
                } else {
                    if (!path.startsWith("/status")) {
                        // ok, error etc. do not exist in the Status (Current) API response
                        addCommonPropertiesAtTopLevel(mergedJsonObj);
                    }
                    // Write the masked (sample) JSON data
                    Path filePath = new File(toMaskedFilePath(path)).toPath();
                    Files.createDirectories(filePath.getParent());
                    Files.write(filePath, GSON.toJson(mergedJsonObj).getBytes(UTF_8));
                }
            } else if (existingRawJsonElem.isJsonArray()) {
                // The Status History API
                JsonArray jsonArray = existingRawJsonElem.getAsJsonArray();
                scanToNormalizeValues(path, null, null, jsonArray);
                Path filePath = new File(toMaskedFilePath(path)).toPath();
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, GSON.toJson(jsonArray).getBytes(UTF_8));
            }
        }
    }

    private String buildSampleJSONString(String path) throws IOException {
        String existingSampleJson = null;
        try {
            Path jsonFilePath = new File(toMaskedFilePath(path)).toPath();
            existingSampleJson = Files.readAllLines(jsonFilePath, UTF_8).stream().collect(Collectors.joining());
        } catch (NoSuchFileException e) {
        }
        if (existingSampleJson == null) {
            existingSampleJson = "{}";
        }
        return existingSampleJson;
    }

    private void writeSCIMResponseData(String path, JsonObject mergedJsonObj) throws IOException {
        // Manually build complete objects for SCIM API response
        if (mergedJsonObj.get("Resources") != null) {
            for (JsonElement resource : mergedJsonObj.get("Resources").getAsJsonArray()) {
                JsonObject resourceObj = resource.getAsJsonObject();
                if (resourceObj.get("userName") != null) {
                    initializeSCIMUser(resourceObj);
                }
                if (resourceObj.get("members") != null) {
                    initializeSCIMGroup(resourceObj);
                }
            }
        } else {
            if (mergedJsonObj.get("userName") != null) {
                initializeSCIMUser(mergedJsonObj);
            }
            if (mergedJsonObj.get("members") != null) {
                initializeSCIMGroup(mergedJsonObj);
            }
        }
        Path filePath = new File(toMaskedFilePath(path).replaceFirst("/\\w{9}.json$", "/000000000.json")).toPath();
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, GSON.toJson(mergedJsonObj).getBytes(UTF_8));
    }

    private void initializeSCIMGroup(JsonObject resourceObj) {
        if (resourceObj.get("members") == null) {
            resourceObj.add("members", new JsonArray());
        }
        {
            JsonArray objects = resourceObj.get("members").getAsJsonArray();
            clearAllElements(objects);
            com.slack.api.scim.model.Group.Member sampleObject =
                    initProperties(new com.slack.api.scim.model.Group.Member());
            objects.add(GsonFactory.createCamelCase(config).toJsonTree(sampleObject));
        }
    }

    private void initializeSCIMUser(JsonObject resourceObj) {
        if (resourceObj.get("addresses") == null) {
            resourceObj.add("addresses", new JsonArray());
        }
        {
            JsonArray objects = resourceObj.get("addresses").getAsJsonArray();
            clearAllElements(objects);
            User.Address sampleObject = initProperties(new User.Address());
            objects.add(GsonFactory.createCamelCase(config).toJsonTree(sampleObject));
        }
        if (resourceObj.get("emails") == null) {
            resourceObj.add("emails", new JsonArray());
        }
        {
            JsonArray objects = resourceObj.get("emails").getAsJsonArray();
            clearAllElements(objects);
            User.Email sampleObject = initProperties(new User.Email());
            objects.add(GsonFactory.createCamelCase(config).toJsonTree(sampleObject));
        }
        if (resourceObj.get("phoneNumbers") == null) {
            resourceObj.add("phoneNumbers", new JsonArray());
        }
        {
            JsonArray objects = resourceObj.get("phoneNumbers").getAsJsonArray();
            clearAllElements(objects);
            User.PhoneNumber sampleObject = initProperties(new User.PhoneNumber());
            objects.add(GsonFactory.createCamelCase(config).toJsonTree(sampleObject));
        }
        if (resourceObj.get("photos") == null) {
            resourceObj.add("photos", new JsonArray());
        }
        {
            JsonArray objects = resourceObj.get("photos").getAsJsonArray();
            clearAllElements(objects);
            User.Photo sampleObject = initProperties(new User.Photo());
            objects.add(GsonFactory.createCamelCase(config).toJsonTree(sampleObject));
        }
        if (resourceObj.get("roles") == null) {
            resourceObj.add("roles", new JsonArray());
        }
        {
            JsonArray objects = resourceObj.get("roles").getAsJsonArray();
            clearAllElements(objects);
            User.Role sampleObject = initProperties(new User.Role());
            objects.add(GsonFactory.createCamelCase(config).toJsonTree(sampleObject));
        }
        if (resourceObj.get("groups") == null) {
            resourceObj.add("groups", new JsonArray());
        }
        {
            JsonArray objects = resourceObj.get("groups").getAsJsonArray();
            clearAllElements(objects);
            User.Group sampleObject = initProperties(new User.Group());
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

    private void scanToNormalizeValues(String path, JsonElement parent, String name, JsonElement element) {
        Gson gson = GsonFactory.createSnakeCase();
        if (element != null && element.isJsonArray()) {
            boolean preparedSampleDataAttached = true;
            JsonArray array = element.getAsJsonArray();
            if (name != null && name.equals("attachments")) {
                parent.getAsJsonObject().remove(name);
                array = new JsonArray();
                parent.getAsJsonObject().add(name, array);
                for (JsonElement attachment : Json.Attachments) {
                    array.add(attachment);
                }
            } else if (name != null && (name.equals("blocks") || name.equals("title_blocks"))) {
                parent.getAsJsonObject().remove(name);
                array = new JsonArray();
                parent.getAsJsonObject().add(name, array);
                // This part replaces the blocks with a comprehensive set of blocks.
                for (JsonElement block : Json.Blocks) {
                    array.add(block);
                }
            } else if (name != null && name.equals("bookmarks")) {
                parent.getAsJsonObject().remove(name);
                array = new JsonArray();
                parent.getAsJsonObject().add(name, array);
                if (!array.isEmpty()) {
                    array.set(0, gson.toJsonTree(initProperties(new Bookmark())));
                } else {
                    array.add(gson.toJsonTree(initProperties(new Bookmark())));
                }
            } else if (path.equals("/api/admin.conversations.search") && name != null && name.equals("ownership_details")) {
                parent.getAsJsonObject().remove(name);
                array = new JsonArray();
                parent.getAsJsonObject().add(name, array);
                array.add(gson.toJsonTree(initProperties(new AdminConversationsSearchResponse.CanvasOwnershipDetail())));
            } else if (path.equals("/api/team.profile.get") &&
                    name != null && Arrays.asList("sections", "possible_values").contains(name)) {
                parent.getAsJsonObject().remove(name);
                array = new JsonArray();
                parent.getAsJsonObject().add(name, array);
                // possible_values
                if (name.equals("sections")) {
                    array.add(gson.toJsonTree(initProperties(
                            new TeamProfileGetResponse.Profiles.Section())));
                }
                if (name.equals("possible_values")) {
                    array.add("");
                }
            } else if (name != null && name.equals("replies")) {
                parent.getAsJsonObject().remove(name);
                array = new JsonArray();
                parent.getAsJsonObject().add(name, array);
                array.add(gson.toJsonTree(initProperties(new Message.MessageRootReply())));
            } else if (name != null && name.equals("comments")) {
                parent.getAsJsonObject().remove(name);
                array = new JsonArray();
                parent.getAsJsonObject().add(name, array);
                array.add(gson.toJsonTree(initProperties(new FileComment())));
            } else if (name != null && name.equals("files") && !path.equals("/api/files.completeUploadExternal")) {
                parent.getAsJsonObject().remove(name);
                array = new JsonArray();
                parent.getAsJsonObject().add(name, array);
                com.slack.api.model.File f = SampleObjects.initFileObject();
                f.setBlocks(null);
                f.setAttachments(null); // Trying to load data for this field can result in StackOverFlowError
                array.add(gson.toJsonTree(f));
            } else if (name != null && name.equals("status_emoji_display_info")) {
                parent.getAsJsonObject().remove(name);
                array = new JsonArray();
                parent.getAsJsonObject().add(name, array);
                array.add(gson.toJsonTree(
                        initProperties(new com.slack.api.model.User.Profile.StatusEmojiDisplayInfo())
                ));
            } else if (path.startsWith("/api/admin.conversations.bulk") && name != null && name.equals("not_added")) {
                parent.getAsJsonObject().remove(name);
                array = new JsonArray();
                parent.getAsJsonObject().add(name, array);
                AdminConversationsBulkMoveResponse.NotAdded notAdded = initProperties(new AdminConversationsBulkMoveResponse.NotAdded());
                array.add(gson.toJsonTree(notAdded));
            } else {
                preparedSampleDataAttached = false;
            }
            if (array.size() == 0) {
                List<String> addressNames = Arrays.asList("from", "to", "cc");
                if (path.startsWith("/scim")) {
                    // noop
                } else if (addressNames.contains(name)) {
                    Address address = new Address();
                    address.setAddress("");
                    address.setName("");
                    address.setOriginal("");
                    JsonElement elem = gson.toJsonTree(address);
                    array.add(elem);
                } else if (path.equals("/api/admin.users.session.getSettings") && name.equals("session_settings")) {
                    array.add(gson.toJsonTree(initProperties(
                            new AdminUsersSessionGetSettingsResponse.SessionSetting())));
                } else if (path.equals("/api/conversations.list") && name.equals("channels")) {
                    array.add(gson.toJsonTree(initProperties(new Conversation())));
                } else if (path.equals("/api/users.conversations") && name.equals("channels")) {
                    array.add(gson.toJsonTree(initProperties(new Conversation())));
                } else if (path.equals("/api/rtm.start") && name.equals("groups")) {
                    array.add(gson.toJsonTree(initProperties(new Group())));
                } else if (name.equals("app_requests")) {
                    array.add(gson.toJsonTree(initProperties(new AppRequest())));
                } else if (path.equals("/api/chat.scheduledMessages.list") && name.equals("scheduled_messages")) {
                    array.add(gson.toJsonTree(initProperties(new ChatScheduledMessagesListResponse.ScheduledMessage())));
                } else if (path.equals("/api/team.profile.get") && name.equals("possible_values")) {
                    array.add("");
                } else if (name.equals("replies")) {
                    array.add(gson.toJsonTree(initProperties(new Message.MessageRootReply())));
                } else if (name.equals("comments")) {
                    array.add(gson.toJsonTree(initProperties(new FileComment())));
                } else if (name.equals("active_incidents")) {
                    SlackIssue slackIssue = new SlackIssue();
                    slackIssue.setNotes(Arrays.asList(initProperties(new SlackIssue.Note())));
                    slackIssue.setServices(Arrays.asList(""));
                    slackIssue = initProperties(slackIssue);
                    array.add(gson.toJsonTree(slackIssue));
                } else {
                    array.add(""); // in most cases, empty array can have string values
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
                    if (!preparedSampleDataAttached) {
                        List<JsonElement> copiedArray = new ArrayList<>();
                        array.iterator().forEachRemaining(copiedArray::add);
                        for (JsonElement e : copiedArray) {
                            scanToNormalizeValues(path, array, null, e);
                        }
                    }
                    if (array.size() >= 2) {
                        for (int idx = 1; idx < array.size(); idx++) {
                            JsonElement elem = array.get(idx);
                            if (elem.isJsonArray()) {
                                for (JsonElement child : elem.getAsJsonArray()) {
                                    scanToNormalizeValues(path, elem, null, child);
                                }
                            } else {
                                try {
                                    JsonObject firstObj = first.isJsonObject() ? first.getAsJsonObject() : new JsonObject();
                                    JsonObject elemObj = elem.isJsonObject() ? elem.getAsJsonObject() : new JsonObject();
                                    MergeJsonBuilder.mergeJsonObjects(firstObj, CONFLICT_STRATEGY, elemObj);
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
                    try {
                        array.remove(idx);
                    } catch (Exception e)  {
                        log.info("Failed to remove an element ({})", idx);
                    }
                }
            }
        } else if (element != null && element.isJsonObject()) {
            if (name != null && name.equals("file")) {
                if (path.startsWith("/audit/v1/schemas") || path.startsWith("/audit/v1/actions")) {
                    return;
                }
                JsonObject file = element.getAsJsonObject();
                try {
                    // To avoid concurrent modification of the underlying objects
                    List<String> oldKeys = new ArrayList<>();
                    file.keySet().iterator().forEachRemaining(oldKeys::add);
                    for (String key : oldKeys) {
                        file.remove(key);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                try {
                    com.slack.api.model.File fileObject = SampleObjects.initFileObject();
                    fileObject.setBlocks(null);
                    fileObject.setAttachments(null);
                    JsonObject fullFile = GsonFactory.createSnakeCase().toJsonTree(fileObject).getAsJsonObject();
                    for (String newKey : fullFile.keySet()) {
                        file.add(newKey, fullFile.get(newKey));
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                return;
            }
            if (name != null && name.equals("inputs") && path.equals("/api/admin.apps.activities.list")) {
                JsonObject permissions = element.getAsJsonObject();
                try {
                    // To avoid concurrent modification of the underlying objects
                    List<String> oldKeys = new ArrayList<>();
                    permissions.keySet().iterator().forEachRemaining(oldKeys::add);
                    for (String key : oldKeys) {
                        permissions.remove(key);
                    }

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (name != null && name.equals("bookmark")) {
                if (path.startsWith("/audit/v1/schemas") || path.startsWith("/audit/v1/actions")) {
                    return;
                }
                try {
                    JsonObject bookmark = element.getAsJsonObject();
                    // To avoid concurrent modification of the underlying objects
                    List<String> oldKeys = new ArrayList<>(bookmark.keySet());
                    for (String key : oldKeys) {
                        bookmark.remove(key);
                    }
                    JsonObject fullFile = GsonFactory.createSnakeCase()
                            .toJsonTree(initProperties(new Bookmark()))
                            .getAsJsonObject();
                    for (String newKey : fullFile.keySet()) {
                        bookmark.add(newKey, fullFile.get(newKey));
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                return;
            }
            if (name != null && name.equals("room")) {
                if (path.startsWith("/audit/v1/schemas") || path.startsWith("/audit/v1/actions")) {
                    return;
                }
                JsonObject room = element.getAsJsonObject();
                try {
                    // To avoid concurrent modification of the underlying objects
                    List<String> oldKeys = new ArrayList<>();
                    room.keySet().iterator().forEachRemaining(oldKeys::add);
                    for (String key : oldKeys) {
                        room.remove(key);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                try {
                    JsonObject roomObj = GsonFactory.createSnakeCase().toJsonTree(SampleObjects.Room).getAsJsonObject();
                    for (String newKey : roomObj.keySet()) {
                        room.add(newKey, roomObj.get(newKey));
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                return;
            }
            if (name != null && name.equals("message")) {
                if (path.startsWith("/audit/v1/schemas") || path.startsWith("/audit/v1/actions")) {
                    return;
                }
                JsonObject message = element.getAsJsonObject();
                if (message.get("room") != null && !message.get("room").isJsonNull()) {
                    JsonObject room = message.get("room").getAsJsonObject();
                    try {
                        // To avoid concurrent modification of the underlying objects
                        List<String> oldKeys = new ArrayList<>();
                        room.keySet().iterator().forEachRemaining(oldKeys::add);
                        for (String key : oldKeys) {
                            room.remove(key);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                    try {
                        JsonObject roomObj = GsonFactory.createSnakeCase().toJsonTree(SampleObjects.Room).getAsJsonObject();
                        for (String newKey : roomObj.keySet()) {
                            room.add(newKey, roomObj.get(newKey));
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                } else {
                    message.add("room", GsonFactory.createSnakeCase().toJsonTree(SampleObjects.Room));
                }
            }
            if (name != null && name.equals("permissions") && path.equals("/api/admin.functions.permissions.lookup")) {
                JsonObject permissions = element.getAsJsonObject();
                try {
                    // To avoid concurrent modification of the underlying objects
                    List<String> oldKeys = new ArrayList<>();
                    permissions.keySet().iterator().forEachRemaining(oldKeys::add);
                    for (String key : oldKeys) {
                        permissions.remove(key);
                    }

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                AppFunctionPermissions appFunctionPermissions = new AppFunctionPermissions();
                appFunctionPermissions.setDistribution(initProperties(new AppFunctionPermissions.Distribution()));
                appFunctionPermissions.setAllowedEntities(initProperties(new AppFunctionPermissions.AllowedEntities()));
                appFunctionPermissions.setAllowedByAdmin(initProperties(new AppFunctionPermissions.AllowedByAdmin()));
                JsonObject p = GsonFactory.createSnakeCase().toJsonTree(appFunctionPermissions).getAsJsonObject();
                permissions.add("Fn0000000000", p);
                permissions.add("Fn0000000000_", p);
            }
            if (path.startsWith("/api/admin.workflows")) {
                if (name != null && name.equals("input_parameters")) {
                    JsonObject inputParameters = element.getAsJsonObject();
                    try {
                        // To avoid concurrent modification of the underlying objects
                        List<String> oldKeys = new ArrayList<>();
                        inputParameters.keySet().iterator().forEachRemaining(oldKeys::add);
                        for (String key : oldKeys) {
                            inputParameters.remove(key);
                        }

                    } catch (Exception e) {
                        log.error(e.getMessage(), e);

                    }
                    AppWorkflow.InputParameter inputParameter = initProperties(new AppWorkflow.InputParameter());
                    JsonObject p = GsonFactory.createSnakeCase().toJsonTree(inputParameter).getAsJsonObject();
                    inputParameters.add("0000000000", p);
                    inputParameters.add("0000000000_", p);
                }
                if (name != null && name.equals("inputs")) {
                    JsonObject inputs = element.getAsJsonObject();
                    try {
                        // To avoid concurrent modification of the underlying objects
                        List<String> oldKeys = new ArrayList<>();
                        inputs.keySet().iterator().forEachRemaining(oldKeys::add);
                        for (String key : oldKeys) {
                            inputs.remove(key);
                        }

                    } catch (Exception e) {
                        log.error(e.getMessage(), e);

                    }
                    AppWorkflow.StepInput input = initProperties(new AppWorkflow.StepInput());
                    input.setValue(null); // TODO: generate all patterns
                    JsonObject p = GsonFactory.createSnakeCase().toJsonTree(input).getAsJsonObject();
                    inputs.add("0000000000", p);
                    inputs.add("0000000000_", p);
                }
                if (name != null && name.equals("permissions")) {
                    JsonObject inputs = element.getAsJsonObject();
                    try {
                        // To avoid concurrent modification of the underlying objects
                        List<String> oldKeys = new ArrayList<>();
                        inputs.keySet().iterator().forEachRemaining(oldKeys::add);
                        for (String key : oldKeys) {
                            inputs.remove(key);
                        }

                    } catch (Exception e) {
                        log.error(e.getMessage(), e);

                    }
                    AppWorkflowPermissions input = initProperties(new AppWorkflowPermissions());
                    input.setWhoCanRun(initProperties(new AppWorkflowPermissions.WhoCanRun()));
                    input.getWhoCanRun().setChannelIds(Arrays.asList(""));
                    input.getWhoCanRun().setOrgIds(Arrays.asList(""));
                    input.getWhoCanRun().setTeamIds(Arrays.asList(""));
                    input.getWhoCanRun().setUserIds(Arrays.asList(""));
                    JsonObject p = GsonFactory.createSnakeCase().toJsonTree(input).getAsJsonObject();
                    inputs.add("0000000000", p);
                    inputs.add("0000000000_", p);
                }
            }
            if (path.equals("/api/users.profile.get") && name != null && name.equals("fields")) {
                JsonObject fields = element.getAsJsonObject();
                // To avoid concurrent modification of the underlying objects
                List<String> oldKeys = new ArrayList<>(fields.keySet());
                for (String key : oldKeys) {
                    fields.remove(key);
                }
                JsonElement field = gson.toJsonTree(initProperties(
                        new com.slack.api.model.User.Profile.Field()));
                fields.add("X00000000", field);
                fields.add("X00000001", field);
            }
            if (name != null && name.equals("metadata")
                    && parent.getAsJsonObject() != null
                    && (parent.getAsJsonObject().get("text") == null || !parent.getAsJsonObject().get("text").isJsonNull())
                    && (parent.getAsJsonObject().get("ts") == null || !parent.getAsJsonObject().get("ts").isJsonNull())) {
                JsonElement eventPayload = element.getAsJsonObject().get("event_payload");
                if (eventPayload != null) {
                    // message metadata
                    element.getAsJsonObject().remove("event_type");
                    element.getAsJsonObject().add("event_type", new JsonPrimitive(""));

                    JsonObject payload = eventPayload.getAsJsonObject();
                    List<String> oldKeys = new ArrayList<>(payload.keySet());
                    for (String key : oldKeys) {
                        payload.remove(key);
                    }
                }
                return;
            }
            if (path.startsWith("/api/apps.manifest.")) {
                if (name != null && name.equals("manifest")) {
                    JsonObject manifest = element.getAsJsonObject();
                    try {
                        // To avoid concurrent modification of the underlying objects
                        List<String> oldKeys = new ArrayList<>();
                        manifest.keySet().iterator().forEachRemaining(oldKeys::add);
                        for (String key : oldKeys) {
                            manifest.remove(key);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                    try {
                        JsonObject manifestObj = GsonFactory.createSnakeCase().toJsonTree(AppManifestObject).getAsJsonObject();
                        for (String newKey : manifestObj.keySet()) {
                            manifest.add(newKey, manifestObj.get(newKey));
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                    return;
                }
            }
            List<Map.Entry<String, JsonElement>> entries = new ArrayList<>(element.getAsJsonObject().entrySet());
            if (entries.size() > 0) {
                if (entries.get(0).getKey().matches("^[A-Z].{8,10}$")) {
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
                    if (path.equals("/api/admin.emoji.list")) {
                        JsonElement emojiElement = GsonFactory.createSnakeCase().toJsonTree(
                                initProperties(new Emoji()));
                        element.getAsJsonObject().add("333", emojiElement);
                        element.getAsJsonObject().add("444", emojiElement);
                    } else {
                        // https://github.com/slackapi/java-slack-sdk/pull/940
                        element.getAsJsonObject().add("333", first.getValue());
                        element.getAsJsonObject().add("444", first.getValue());
                    }
                }
            }
            // To avoid concurrent modification of the underlying objects
            List<Map.Entry<String, JsonElement>> copiedEntries = new ArrayList<>();
            element.getAsJsonObject().entrySet().iterator().forEachRemaining(copiedEntries::add);
            for (Map.Entry<String, JsonElement> entry : copiedEntries) {
                try {
                    scanToNormalizeValues(path, element, entry.getKey(), entry.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (element == null || element.isJsonNull()) {
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
        if (value.startsWith("http://") || value.startsWith("https://")) {
            return "https://www.example.com/";
        }
        if (value.matches("^[A-Z][A-Z0-9]{8,10}$")) {
            return value.substring(0, 1) + "00000000"; // identifier
        }
        if (value.matches("^\\d$")) {
            return "0"; // other numbers
        } else if (value.matches("^[\\d]+$")) {
            return "12345"; // other numbers
        }
        return "";
    }

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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
