package testing.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

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

        for (Map.Entry<String, JsonElement> rightEntry : rightObj.entrySet()) {
            String rightKey = rightEntry.getKey();
            JsonElement rightVal = rightEntry.getValue();
            if (leftObj.has(rightKey)) {
                //conflict
                JsonElement leftVal = leftObj.get(rightKey);
                if (leftVal.isJsonArray() && rightVal.isJsonArray()) {
                    JsonArray leftArr = leftVal.getAsJsonArray();
                    JsonArray rightArr = rightVal.getAsJsonArray();
                    //concat the arrays -- there cannot be a conflict in an array, it's just a collection of stuff
                    for (int i = 0; i < rightArr.size(); i++) {
                        leftArr.add(rightArr.get(i));
                    }
                } else if (leftVal.isJsonObject() && rightVal.isJsonObject()) {
                    //recursive merging
                    mergeJsonObjects(leftVal.getAsJsonObject(), rightVal.getAsJsonObject(), conflictStrategy);
                } else {//not both arrays or objects, normal merge with conflict resolution
                    handleMergeConflict(rightKey, leftObj, leftVal, rightVal, conflictStrategy);
                }
            } else {//no conflict, add to the object
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
                break;//do nothing, the right val gets thrown out
            case PREFER_SECOND_OBJ:
                leftObj.add(key, rightVal);//right side auto-wins, replace left val with its val
                break;
            case PREFER_NON_NULL:
                //check if right side is not null, and left side is null, in which case we use the right val
                if (leftVal.isJsonNull() && !rightVal.isJsonNull()) {
                    leftObj.add(key, rightVal);
                }//else do nothing since either the left value is non-null or the right value is null
                break;
            case THROW_EXCEPTION:
                throw new JsonConflictException("Key " + key + " exists in both objects and the conflict resolution strategy is " + conflictStrategy);
            default:
                throw new UnsupportedOperationException("The conflict strategy " + conflictStrategy + " is unknown and cannot be processed");
        }
    }

}
