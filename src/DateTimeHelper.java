import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

import static java.time.temporal.ChronoField.OFFSET_SECONDS;

public class DateTimeHelper {

    private static DateTimeFormatter dateTimeFormatter;

    // static block to construct date time formatter
    static {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        dateTimeFormatter = builder.append(DateTimeFormatter.ISO_LOCAL_DATE_TIME).
                optionalStart(). // offset is optional
                appendOffset("+HH:MM:SS", "+00:00:00").
                optionalEnd().
                parseDefaulting(OFFSET_SECONDS, 0). // if user did not enter offset, default to 0 (UTC)
                toFormatter();
    }

    /**
     * Parses a string into an OffsetDateTime object
     * @param dateTime Input string to be parsed
     * @return OffsetDateTime object representing the given String, or null, if the String cannot be parsed
     */
    public static OffsetDateTime parseDateTime(String dateTime) {
        try {
            return dateTimeFormatter.parse(dateTime, OffsetDateTime::from);
        } catch (DateTimeParseException e) {
            System.err.println("Cannot parse date time '" + dateTime + "' - must match format: " + dateTimeFormatter.toString());
            return null;
        }
    }

    /**
     * Returns an ordered array of OffsetDateTime objects
     * @param dateTime1 First date time input parameter
     * @param dateTime2 Second date time input parameter
     * @return Array of OffsetDateTime objects ordered from earliest to latest
     */
    public static OffsetDateTime[] orderDateTimes(OffsetDateTime dateTime1, OffsetDateTime dateTime2) {
        if (dateTime1.isAfter(dateTime2)) {
            return new OffsetDateTime[] {dateTime2, dateTime1};
        } else {
            return new OffsetDateTime[] {dateTime1, dateTime2};
        }
    }

    public static String getFormattedDateTime(OffsetDateTime dateTime) {
        return dateTimeFormatter.format(dateTime);
    }
}
