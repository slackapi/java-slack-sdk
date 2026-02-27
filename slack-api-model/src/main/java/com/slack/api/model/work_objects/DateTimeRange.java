package com.slack.api.model.work_objects;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Combined date/time display data for calendar events.
 */
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
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME.withZone(ZoneOffset.UTC);
        private final Instant dateTime;
        @Getter @Setter
        private boolean wasYYYYMMDDInput = false;
        @Getter @Setter
        private boolean wasUnixtimeInput = false;

        public static DateTimeImpl from(String in) {
            // See if this is a number first
            try {
                DateTimeImpl dateTime = DateTimeImpl.from(Long.parseLong(in));
                dateTime.setWasUnixtimeInput(true);
                return dateTime;
            } catch (NumberFormatException e) {
                // Swallow - means the input is not a unix timestamp, so try to parse this as a string
            }

            // Now make sure it's in YYYY-MM-DD format
            try {
                Instant dt = LocalDate.parse(in).atStartOfDay(ZoneOffset.UTC).toInstant();
                DateTimeImpl dateTime = new DateTimeImpl(dt);
                dateTime.setWasYYYYMMDDInput(true);
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
            return formatter.format(dateTime);
        }
    }
}
