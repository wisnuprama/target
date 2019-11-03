package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import androidx.room.TypeConverter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampConverter {

    @TypeConverter
    public static OffsetDateTime fromTimestamp(String value) {
        return value == null ? null : DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(value, OffsetDateTime::from);
    }

    @TypeConverter
    public static String dateToTimestamp(OffsetDateTime date) {
        return date == null ? null : date.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
