package com.slack.api.model.work_objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Combined date/time display data for calendar events.
 */
@Value
@Builder
@JsonAdapter(DateTimeRange.DateTimeRangeSerializer.class)
public class DateTimeRange {
    /**
     * Start as Unix timestamp or YYYY-MM-DD dates.
     */
    DateTimeImpl start;
    /**
     * End as Unix timestamp or YYYY-MM-DD dates
     */
    DateTimeImpl end;

    /**
     * Whether this is an all-day event.
     */
    Boolean allDay;

    /**
     * Recurrence description text.
     */
    String recurrence;

    /**
     * Since java doesn't support union types, this class represents a datetime input that can either be a unix
     * timestamp or a date string in YYYY-MM-DD format.
     */
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DateTimeImpl {
        private final Instant dateTime;
        @Getter @Setter
        private boolean inYearMonthDayFormat = false;

        public boolean isUnknown() {
            return dateTime.equals(Instant.EPOCH);
        }

        public static DateTimeImpl atStartOfDay() {
            Instant startOfDay = LocalDate.now(ZoneOffset.UTC).atStartOfDay(ZoneOffset.UTC).toInstant();
            return new DateTimeImpl(startOfDay);
        }

        public static DateTimeImpl atEndOfDay() {
            Instant endOfDay = LocalDate.now(ZoneOffset.UTC).plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();
            return new DateTimeImpl(endOfDay);
        }

        // Used during JSON serialization/deserialization when we can't infer what the date-time value is
        public static DateTimeImpl unknown() {
            return new DateTimeImpl(Instant.EPOCH);
        }

        public static DateTimeImpl from(String in) {
            // See if this is a number first
            try {
                return DateTimeImpl.from(Long.parseLong(in));
            } catch (NumberFormatException e) {
                // Swallow - means the input is not a unix timestamp, so try to parse this as a string
            }

            // Now make sure it's in YYYY-MM-DD format
            try {
                Instant dt = LocalDate.parse(in).atStartOfDay(ZoneOffset.UTC).toInstant();
                DateTimeImpl dateTime = new DateTimeImpl(dt);
                dateTime.setInYearMonthDayFormat(true);
                return dateTime;
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(String.format("Datetime input string not in a recognized format %s", in));
            }
        }

        public static DateTimeImpl from(int in) {
            return from(Long.valueOf(in));
        }

        public static DateTimeImpl from(long in) {
            Instant dateTime = Instant.ofEpochSecond(in);
            return new DateTimeImpl(dateTime);
        }

        public long getUnixTime() {
            return dateTime.getEpochSecond();
        }

        public String getDateTime() {
            return DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneOffset.UTC).format(dateTime);
        }
    }

    public static class DateTimeRangeSerializer implements JsonSerializer<DateTimeRange> {
        @Override
        public JsonElement serialize(DateTimeRange dateTimeRange, Type typeOfT, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            if (dateTimeRange.getStart() != null) {
                DateTimeImpl start = dateTimeRange.getStart();
                if (start.isInYearMonthDayFormat()) {
                    jsonObject.addProperty("start", start.getDateTime());
                } else {
                    jsonObject.addProperty("start", start.getUnixTime());
                }
            }
            if (dateTimeRange.getEnd() != null) {
                DateTimeImpl end = dateTimeRange.getEnd();
                if (end.isInYearMonthDayFormat()) {
                    jsonObject.addProperty("end", end.getDateTime());
                } else {
                    jsonObject.addProperty("end", end.getUnixTime());
                }
            }
            if (dateTimeRange.getAllDay() != null) {
                jsonObject.addProperty("all_day", dateTimeRange.getAllDay());
            }
            if (dateTimeRange.getRecurrence() != null && !dateTimeRange.getRecurrence().isEmpty()) {
                jsonObject.addProperty("recurrence", dateTimeRange.getRecurrence());
            }

            return jsonObject;
        }
    }
}
