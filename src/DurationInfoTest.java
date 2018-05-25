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

        durationInfo.setStartDateTime(january1st);
        durationInfo.setEndDateTime(january1st);
        long weeks = durationInfo.getCompleteWeeksBetweenDateTimes();
        assertEquals(0, weeks);

        durationInfo.setStartDateTime(january1st);
        durationInfo.setEndDateTime(feb1st);
        weeks = durationInfo.getCompleteWeeksBetweenDateTimes();
        // 31 days in January, so expect 31 days = 4 complete weeks as result
        assertEquals(4, weeks);

        durationInfo.setStartDateTime(january1st5pm);
        durationInfo.setEndDateTime(january8th);
        weeks = durationInfo.getCompleteWeeksBetweenDateTimes();
        // slightly less than complete week
        assertEquals(0, weeks);

        durationInfo.setStartDateTime(dtCompare1);
        durationInfo.setEndDateTime(dtCompare2);
        weeks = durationInfo.getCompleteWeeksBetweenDateTimes();
        // equivalent UTC times are 2017-12-31 15:00 and 2018-01-07 19:00, so should return 1
        assertEquals(1, weeks);
    }
}