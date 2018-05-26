import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeHelperTest {

    @Test
    void parseDateTime() {
        OffsetDateTime dateTime = DateTimeHelper.parseDateTime("2018-01-01T12:00:00");
        assertNotNull(dateTime);

        dateTime = DateTimeHelper.parseDateTime("2018-01-01T12:00:00+05:00:00");
        assertNotNull(dateTime);

        dateTime = DateTimeHelper.parseDateTime("2018/01/01T12:00:00");
        assertNull(dateTime);

        dateTime = DateTimeHelper.parseDateTime("2018-01-01T12:00");
        assertNotNull(dateTime);

        dateTime = DateTimeHelper.parseDateTime("2018-01-01T12:00:00+05");
        assertNull(dateTime);
    }

    @Test
    void orderDateTimes() {
        OffsetDateTime first = OffsetDateTime.of(2018, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime second = OffsetDateTime.of(2018, 1, 1, 12, 0, 0, 0, ZoneOffset.ofHours(1));

        OffsetDateTime[] dateTimeArray = DateTimeHelper.orderDateTimes(first, second);
        assertEquals(first, dateTimeArray[1]);
        assertEquals(second, dateTimeArray[0]);

        dateTimeArray = DateTimeHelper.orderDateTimes(second, first);
        assertEquals(first, dateTimeArray[1]);
        assertEquals(second, dateTimeArray[0]);
    }

    @Test
    void getConvertedValue() {
        long value = DateTimeHelper.getConvertedValue(1, ChronoUnit.HOURS, ChronoUnit.SECONDS);
        assertEquals(value, 3600);

        value = DateTimeHelper.getConvertedValue(3601, ChronoUnit.SECONDS, ChronoUnit.HOURS);
        assertEquals(1, value);

        value = DateTimeHelper.getConvertedValue(3599, ChronoUnit.SECONDS, ChronoUnit.HOURS);
        assertEquals(0, value);

        value = DateTimeHelper.getConvertedValue(73, ChronoUnit.DAYS, ChronoUnit.WEEKS);
        assertEquals(10, value);
    }
}