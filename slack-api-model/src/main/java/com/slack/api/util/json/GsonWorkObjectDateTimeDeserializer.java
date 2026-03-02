package com.slack.api.util.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.slack.api.model.work_objects.DateTimeRange;
import com.slack.api.model.work_objects.DateTimeRange.DateTimeRangeBuilder;
import com.slack.api.model.work_objects.DateTimeRange.DateTimeImpl;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Type;

/**
 * Factory for serializing and deserializing {@link com.slack.api.model.work_objects.DateTimeRange} objects, namely
 * to handle start and end time inputs that are allowed to be in unix time OR YYYY-MM-DD format.
 */
@RequiredArgsConstructor
public class GsonWorkObjectDateTimeDeserializer implements JsonDeserializer<DateTimeRange> {
    private final boolean failOnUnknownProperties;

    @Override
    public DateTimeRange deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final boolean isAllDay = jsonObject.has("all_day") && jsonObject.get("all_day").getAsBoolean();
        final DateTimeRangeBuilder builder = DateTimeRange.builder().allDay(isAllDay);

        // If we have an all-day event, and the start/end times haven't been provided, set them to the start/end
        // of the today
        if (isAllDay) {
            if (!jsonObject.has("start")) {
                builder.start(DateTimeImpl.atStartOfDay());
            }
            if (!jsonObject.has("end")) {
                builder.end(DateTimeImpl.atEndOfDay());
            }
        }

        // If we don't have an all-day event, we need both start and end dates
        if (!isAllDay && (!jsonObject.has("start") || !jsonObject.has("end"))) {
            if (failOnUnknownProperties) {
                throw new JsonParseException("DateTimeRange object missing start and/or end times");
            }

            // We can't really do anything in this case, just return null
            return null;
        }

        if (jsonObject.has("start")) {
            JsonPrimitive startDate = jsonObject.getAsJsonPrimitive("start");
            builder.start(getDateTime(startDate));
        }
        if (jsonObject.has("end")) {
            JsonPrimitive endDate = jsonObject.getAsJsonPrimitive("end");
            builder.end(getDateTime(endDate));
        }
        if (jsonObject.has("recurrence") && !jsonObject.get("recurrence").isJsonNull()) {
            try {
                builder.recurrence(jsonObject.getAsJsonPrimitive("recurrence").getAsString());
            } catch (AssertionError e) {
                if (failOnUnknownProperties) {
                    throw new JsonParseException(e);
                }
            }
        }

        return builder.build();
    }

    private DateTimeImpl getDateTime(JsonPrimitive prim) {
        try {
            if (prim.isNumber()) {
                return DateTimeImpl.from(prim.getAsLong());
            } else if (prim.isString()) {
                return DateTimeImpl.from(prim.getAsString());
            }
            throw new IllegalArgumentException("Unrecognized JSON primitive type");
        } catch (IllegalArgumentException e) {
            if (failOnUnknownProperties) {
                throw new JsonParseException(e);
            }
            return DateTimeImpl.unknown();
        }
    }
}
