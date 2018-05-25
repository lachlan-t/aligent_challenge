import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.WEEKS;

public class DurationInfo {

    private OffsetDateTime startDateTime;
    private OffsetDateTime endDateTime;

    public void setStartDateTime(OffsetDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(OffsetDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    // method used to return difference between date times in given units
    public long getUnitsBetweenDateTimes(ChronoUnit units) {
        return units.between(startDateTime, endDateTime);
    }

    public long getDaysBetweenDateTimes() {
        return getUnitsBetweenDateTimes(DAYS);
    }

    public long getWeekdaysBetweenDateTimes() {
        return 0;
    }

    public long getCompleteWeeksBetweenDateTimes() {
        return getUnitsBetweenDateTimes(WEEKS);
    }
}
