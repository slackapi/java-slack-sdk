package test_locally.api.model.work_objects;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.slack.api.model.work_objects.DateTimeRange;
import com.slack.api.model.work_objects.DateTimeRange.DateTimeImpl;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class DateTimeRangeTest {
    final Gson gson = GsonFactory.createSnakeCaseWithRequiredPropertyDetection();

    @Test
    public void testJsonSerialization() {
        // An empty object shout output an empty json object
        DateTimeRange dateTime = DateTimeRange.builder().build();
        assertThat(gson.toJson(dateTime), is("{}"));

        // Handles booleans
        dateTime = DateTimeRange.builder().allDay(true).build();
        assertThat(gson.toJson(dateTime), is("{\"all_day\":true}"));
        dateTime = DateTimeRange.builder().allDay(false).build();
        assertThat(gson.toJson(dateTime), is("{\"all_day\":false}"));

        // Handles the start/end times formatted in YYYY-MM-DD format
        DateTimeImpl start = DateTimeImpl.from("2026-02-26");
        DateTimeImpl end = DateTimeImpl.from("2026-02-27");
        dateTime = DateTimeRange.builder().start(start).end(end).build();
        assertThat(gson.toJson(dateTime), is("{\"start\":\"2026-02-26\",\"end\":\"2026-02-27\"}"));

        // Handles the start/end times as string inputs in unixtime format
        start = DateTimeImpl.from("1772215885694");
        end = DateTimeImpl.from("1772219309359");
        dateTime = DateTimeRange.builder().start(start).end(end).build();
        assertThat(gson.toJson(dateTime), is("{\"start\":1772215885694,\"end\":1772219309359}"));

        // Handles the start/end times as integer inputs in unixtime format
        start = DateTimeImpl.from(1772215885694L);
        end = DateTimeImpl.from(1772219309359L);
        dateTime = DateTimeRange.builder().start(start).end(end).build();
        assertThat(gson.toJson(dateTime), is("{\"start\":1772215885694,\"end\":1772219309359}"));
    }

    @Test
    public void throwsOnInvalidDateTimeInput() {
        assertThrows(IllegalArgumentException.class, () -> DateTimeImpl.from("this just isn't a date"));
    }

    @Test
    public void gracefullyHandles_malformedJson_whenFailOnUnknownProperties_isFalse() {
        // We should return null when the json can't be converted to an actual date time value
        String jsonMissingStartAndEnd = "{\"all_day\":false}";
        assertNull(gson.fromJson(jsonMissingStartAndEnd, DateTimeRange.class));

        // Should add the start/end fields if the json just indicates it's an all-day event
        String allDayOnly = "{\"all_day\":true}";
        DateTimeRange dateTime = gson.fromJson(allDayOnly, DateTimeRange.class);
        assertThat(dateTime.getAllDay(), is(true));
        assertNotNull(dateTime.getStart());
        assertNotNull(dateTime.getEnd());

        // We should skip adding the recurrence property if it can't be coerced to a string
        String malformedRecurrence = "{\"all_day\":true, \"recurrence\": null}";
        dateTime = gson.fromJson(malformedRecurrence, DateTimeRange.class);
        assertThat(dateTime.getAllDay(), is(true));
        assertNotNull(dateTime.getStart());
        assertNotNull(dateTime.getEnd());
        assertNull(dateTime.getRecurrence());

        // We should use "unknown" start/end values when the input isn't a valid unixtime or date string
        String malformedStartEnd = "{\"start\":hello, \"end\": world}";
        dateTime = gson.fromJson(malformedStartEnd, DateTimeRange.class);
        assertTrue(dateTime.getStart().isUnknown());
        assertTrue(dateTime.getEnd().isUnknown());
    }

    @Test
    public void throws_onInvalidJson_whenFailOnUnknownProperties_isTrue() {
        final Gson strictGson = GsonFactory.createSnakeCaseWithRequiredPropertyDetection(true);

        // Can't do anything with an empty object
        assertThrows(JsonParseException.class, () -> strictGson.fromJson("{}", DateTimeRange.class));

        // Should throw when the start/end properties are malformed
        String badJson = "{\"start\": hello, \"end\": world}";
        assertThrows(JsonParseException.class, () -> strictGson.fromJson(badJson, DateTimeRange.class));
    }
}
