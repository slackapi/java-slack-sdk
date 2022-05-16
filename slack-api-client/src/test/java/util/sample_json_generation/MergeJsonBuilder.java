package util.sample_json_generation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;

/**
 * https://stackoverflow.com/a/34092374/840108
 */
public class MergeJsonBuilder {

    private MergeJsonBuilder() {
    }

    public enum ConflictStrategy {
        THROW_EXCEPTION,
        PREFER_FIRST_OBJ,
        PREFER_SECOND_OBJ,
        PREFER_NON_NULL
    }

    public static class JsonConflictException extends Exception {

        public JsonConflictException(String message) {
            super(message);
        }

    }

    public static void mergeJsonObjects(
            JsonObject destinationObject,
            ConflictStrategy conflictResolutionStrategy,
            JsonObject... jsonObjects) throws JsonConflictException {

        for (JsonObject obj : jsonObjects) {
            mergeJsonObjects(destinationObject, obj, conflictResolutionStrategy);
        }
    }

    private static void mergeJsonObjects(
            JsonObject leftObj,
            JsonObject rightObj,
            ConflictStrategy conflictStrategy) throws JsonConflictException {

        List<Map.Entry<String, JsonElement>> rightObjEntries = new ArrayList<>();
        if (rightObj != null) {
            new ArrayList<>(rightObj.entrySet()).iterator().forEachRemaining(rightObjEntries::add);
        }
        for (Map.Entry<String, JsonElement> rightEntry : rightObjEntries) {
            String rightKey = rightEntry.getKey();
            JsonElement rightVal = rightEntry.getValue();
            if (leftObj.has(rightKey)) {
                // Conflict
                JsonElement leftVal = leftObj.get(rightKey);
                if (leftVal != null && leftVal.isJsonArray()
                        && rightVal != null && rightVal.isJsonArray()) {
                    JsonArray leftArr = leftVal.getAsJsonArray();
                    JsonArray rightArr = rightVal.getAsJsonArray();
                    for (int i = 0; i < rightArr.size(); i++) {
                        JsonElement rightArrayElem = rightArr.get(i);
                        if (!leftArr.contains(rightArrayElem)) {
                            // Remove temporarily added an empty string
                            if (rightArrayElem.isJsonObject()
                                    && leftArr.size() > i
                                    && leftArr.get(i).isJsonPrimitive()
                                    && leftArr.get(i).getAsString().equals("")) {
                                leftArr.remove(0);
                            }
                            leftArr.add(rightArrayElem);
                        }
                    }
                } else if (leftVal != null && leftVal.isJsonObject() && rightVal != null &&  rightVal.isJsonObject()) {
                    // Recursive merging
                    mergeJsonObjects(leftVal.getAsJsonObject(), rightVal.getAsJsonObject(), conflictStrategy);
                } else {// Not both arrays or objects, normal merge with conflict resolution
                    handleMergeConflict(rightKey, leftObj, leftVal, rightVal, conflictStrategy);
                }
            } else {// No conflict, add to the object
                leftObj.add(rightKey, rightVal);
            }
        }
    }

    private static void handleMergeConflict(
            String key,
            JsonObject leftObj,
            JsonElement leftVal,
            JsonElement rightVal,
            ConflictStrategy conflictStrategy) throws JsonConflictException {

        switch (conflictStrategy) {
            case PREFER_FIRST_OBJ:
                break; // Do nothing, the right val gets thrown out
            case PREFER_SECOND_OBJ:
                leftObj.add(key, rightVal); // Right side auto-wins, replace left val with its val
                break;
            case PREFER_NON_NULL:
                // Check if right side is not null, and left side is null, in which case we use the right val
                if (leftVal.isJsonNull() && !rightVal.isJsonNull()) {
                    leftObj.add(key, rightVal);
                }// Else do nothing since either the left value is non-null or the right value is null
                break;
            case THROW_EXCEPTION:
                throw new JsonConflictException("Key " + key + " exists in both objects and the conflict resolution strategy is " + conflictStrategy);
            default:
                throw new UnsupportedOperationException("The conflict strategy " + conflictStrategy + " is unknown and cannot be processed");
        }
    }

}
