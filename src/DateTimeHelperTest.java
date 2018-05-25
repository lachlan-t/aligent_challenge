import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeHelperTest {

    @Test
    void parseDateTime() {
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
}