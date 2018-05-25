import java.time.OffsetDateTime;

public class DurationInfo {

    private OffsetDateTime startDateTime;
    private OffsetDateTime endDateTime;

    public void setStartDateTime(OffsetDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(OffsetDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public long getDaysBetweenDateTimes() {
        return 0;
    }

    public long getWeekdaysBetweenDateTimes() {
        return 0;
    }

    public long getCompleteWeeksBetweenDateTimes() {
        return 0;
    }
}
