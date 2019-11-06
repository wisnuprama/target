package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import androidx.room.TypeConverter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Room by default doesn't support datetime and timezone.
 * To support datetime, we create a converter.
 * We use {@link OffsetDateTime} to support timezone.
 */
public class TimestampConverter {

    /**
     * Convert from room supported data type to runtime data type.
     * @param value
     * @return
     */
    @TypeConverter
    public static OffsetDateTime fromTimestamp(String value) {
        return value == null ? null : DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(value, OffsetDateTime::from);
    }

    /**
     * Convert from runtime to room supported data type (String).
     * @param date
     * @return
     */
    @TypeConverter
    public static String dateToTimestamp(OffsetDateTime date) {
        return date == null ? null : date.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
