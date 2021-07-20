package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.Attachment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonMessageAttachmentVideoHtmlFactory implements JsonDeserializer<Attachment.VideoHtml>, JsonSerializer<Attachment.VideoHtml> {

    private static final String REPORT_THIS = "Please report this issue at https://github.com/slackapi/java-slack-sdk/issues";

    private final boolean failOnUnknownProperties;

    public GsonMessageAttachmentVideoHtmlFactory() {
        this(false);
    }

    public GsonMessageAttachmentVideoHtmlFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public Attachment.VideoHtml deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Attachment.VideoHtml result = new Attachment.VideoHtml();
        if (json.isJsonPrimitive()) {
            result.setHtml(json.getAsString());
            return result;
        } else if (json.isJsonObject()) {
            JsonObject videoHtmlObject = json.getAsJsonObject();
            if (videoHtmlObject.has("source")) {
                result.setSource(videoHtmlObject.get("source").getAsString());
            }
            return result;
        } else {
            if (failOnUnknownProperties) {
                String message = "The whole value (" + json + ") is unsupported. " + REPORT_THIS;
                throw new JsonParseException(message);
            }
        }
        return result;
    }

    private List<String> parseStringArray(JsonElement json) throws JsonParseException {
        List<String> values = new ArrayList<>();
        for (JsonElement elem : json.getAsJsonArray()) {
            if (elem.isJsonPrimitive()) {
                values.add(elem.getAsString());
            } else {
                if (failOnUnknownProperties) {
                    String message = "An unexpected element (" + elem + ") in an array is detected. " + REPORT_THIS;
                    throw new JsonParseException(message);
                }
            }
        }
        return values;
    }

    @Override
    public JsonElement serialize(Attachment.VideoHtml src, Type typeOfSrc, JsonSerializationContext context) {
        if (src.getHtml() != null) {
            return new JsonPrimitive(src.getHtml());
        } else if (src.getSource() != null) {
            JsonObject json = new JsonObject();
            json.add("source", new JsonPrimitive(src.getSource()));
            return json;
        } else {
            return JsonNull.INSTANCE;
        }
    }
}
