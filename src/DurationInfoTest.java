import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class DurationInfoTest {

    private DurationInfo durationInfo;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        durationInfo = new DurationInfo();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        durationInfo = null;
    }

    @org.junit.jupiter.api.Test
    void getDaysBetweenDateTimes() {
        // create date times for january 1 and february 1 at noon
        OffsetDateTime january1st = OffsetDateTime.of(2018, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime feb1st = OffsetDateTime.of(2018, 2, 1, 12, 0, 0, 0, ZoneOffset.UTC);
        // also create date time for january 1 at 5pm
        OffsetDateTime january1st5pm = OffsetDateTime.of(2018, 1, 1, 17, 0, 0, 0, ZoneOffset.UTC);

        // create date times with offsets
        OffsetDateTime dtCompare1 = OffsetDateTime.of(2018, 1, 1, 1, 0, 0, 0, ZoneOffset.ofHours(10));
        OffsetDateTime dtCompare2 = OffsetDateTime.of(2018, 1, 1, 20, 0, 0, 0, ZoneOffset.ofHours(1));

        durationInfo.setStartDateTime(january1st);
        durationInfo.setEndDateTime(january1st);
        long days = durationInfo.getDaysBetweenDateTimes();
        assertEquals(0, days);

        durationInfo.setStartDateTime(january1st);
        durationInfo.setEndDateTime(feb1st);
        days = durationInfo.getDaysBetweenDateTimes();
        // 31 days in January, so expect 31 as result
        assertEquals(31, days);

        durationInfo.setStartDateTime(january1st);
        durationInfo.setEndDateTime(january1st5pm);
        days = durationInfo.getDaysBetweenDateTimes();
        // less than a full day should be 0 days
        assertEquals(0, days);

        durationInfo.setStartDateTime(dtCompare1);
        durationInfo.setEndDateTime(dtCompare2);
        days = durationInfo.getDaysBetweenDateTimes();
        // equivalent UTC times are 2017-12-31 15:00 and 2018-01-01 19:00, so should return 1
        assertEquals(1, days);
    }

    @org.junit.jupiter.api.Test
    void getWeekdaysBetweenDateTimes() {
        // create date times for january 1 and february 1 at noon
        OffsetDateTime january1st = OffsetDateTime.of(2018, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime feb1st = OffsetDateTime.of(2018, 2, 1, 12, 0, 0, 0, ZoneOffset.UTC);
        // also create date time for january 1 at 5pm
        OffsetDateTime january1st5pm = OffsetDateTime.of(2018, 1, 1, 17, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime january8th = OffsetDateTime.of(2018, 1, 8, 12, 0, 0, 0, ZoneOffset.UTC);

        // create date times with offsets
        OffsetDateTime dtCompare1 = OffsetDateTime.of(2018, 1, 1, 1, 0, 0, 0, ZoneOffset.ofHours(10));
        OffsetDateTime dtCompare2 = OffsetDateTime.of(2018, 1, 1, 20, 0, 0, 0, ZoneOffset.ofHours(1));

        // create dates on weekend
        OffsetDateTime wkndStart = OffsetDateTime.of(2018, 5, 6, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime wkndEnd = OffsetDateTime.of(2018, 5, 26, 12, 0, 0, 0, ZoneOffset.UTC);

        durationInfo.setDateTimes(january1st, january1st);
        long weekdays = durationInfo.getWeekdaysBetweenDateTimes();
        assertEquals(0, weekdays);

        durationInfo.setDateTimes(january1st, feb1st);
        weekdays = durationInfo.getWeekdaysBetweenDateTimes();
        // 31 days in January, and 4 weekends, so expect 23 weekdays (31 - 8) as result
        assertEquals(23, weekdays);

        durationInfo.setDateTimes(january1st5pm, january8th);
        weekdays = durationInfo.getWeekdaysBetweenDateTimes();
        // slightly less than complete week - expect 4 weekdays
        assertEquals(4, weekdays);

        durationInfo.setDateTimes(dtCompare1, dtCompare2);
        weekdays = durationInfo.getWeekdaysBetweenDateTimes();
        // equivalent UTC times are 2017-12-31 15:00 and 2018-01-01 19:00, so should return 1
        assertEquals(1, weekdays);

        durationInfo.setDateTimes(wkndStart, wkndEnd);
        weekdays = durationInfo.getWeekdaysBetweenDateTimes();
        assertEquals(15, weekdays);
    }

    @org.junit.jupiter.api.Test
    void getCompleteWeeksBetweenDateTimes() {
        // create date times for january 1 and february 1 at noon
        OffsetDateTime january1st = OffsetDateTime.of(2018, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime feb1st = OffsetDateTime.of(2018, 2, 1, 12, 0, 0, 0, ZoneOffset.UTC);
        // also create date time for january 1 at 5pm
        OffsetDateTime january1st5pm = OffsetDateTime.of(2018, 1, 1, 17, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime january8th = OffsetDateTime.of(2018, 1, 8, 12, 0, 0, 0, ZoneOffset.UTC);

        // create date times with offsets
        OffsetDateTime dtCompare1 = OffsetDateTime.of(2018, 1, 1, 1, 0, 0, 0, ZoneOffset.ofHours(10));
        OffsetDateTime dtCompare2 = OffsetDateTime.of(2018, 1, 7, 20, 0, 0, 0, ZoneOffset.ofHours(1));

        durationInfo.setDateTimes(january1st, january1st);
        long weeks = durationInfo.getCompleteWeeksBetweenDateTimes();
        assertEquals(0, weeks);

        durationInfo.setDateTimes(january1st, feb1st);
        weeks = durationInfo.getCompleteWeeksBetweenDateTimes();
        // 31 days in January, so expect 31 days = 4 complete weeks as result
        assertEquals(4, weeks);

        durationInfo.setDateTimes(january1st5pm, january8th);
        weeks = durationInfo.getCompleteWeeksBetweenDateTimes();
        // slightly less than complete week
        assertEquals(0, weeks);

        durationInfo.setDateTimes(dtCompare1, dtCompare2);
        weeks = durationInfo.getCompleteWeeksBetweenDateTimes();
        // equivalent UTC times are 2017-12-31 15:00 and 2018-01-07 19:00, so should return 1
        assertEquals(1, weeks);
    }
}