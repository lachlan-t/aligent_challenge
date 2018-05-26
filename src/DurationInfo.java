import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.WEEKS;

class DurationInfo {

    private OffsetDateTime startDateTime;
    private OffsetDateTime endDateTime;

    void setStartDateTime(OffsetDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    void setEndDateTime(OffsetDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    void setDateTimes(OffsetDateTime startDateTime, OffsetDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    // method used to return difference between date times in given units
    long getUnitsBetweenDateTimes(ChronoUnit units) {
        return units.between(startDateTime, endDateTime);
    }

    long getDaysBetweenDateTimes() {
        return getUnitsBetweenDateTimes(DAYS);
    }

    /**
     * Finds the number of weekdays (MON-FRI) between two date times.
     * If the start day is a weekend, it will be considered to start at midnight on the following Monday.
     * For example, if the two date times are:
     *  - Saturday, 19th May, 2018, 10:00
     *  - Wednesday, 23rd May, 2018, 11:00
     *  Then the number of weekdays will be 2 (number of whole days between Monday, 12am and Wednesday, 11am).
     *
     * If the end day is a weekend, it will be considered to end at midnight on the previous (or same) Saturday.
     * For example, if the two date times are:
     *  - Tuesday, 22nd May, 2018, 10:00
     *  - Sunday, 27rd May, 2018, 11:00
     * Then the number of weekdays will be 3 (number of whole days between Tuesday, 10am and Saturday, 12am).
     * @return The number of weekdays between start and end date times.
     */
    long getWeekdaysBetweenDateTimes() {
        DayOfWeek startDay = startDateTime.getDayOfWeek();
        DayOfWeek endDay = endDateTime.getDayOfWeek();

        OffsetDateTime originalStartDateTime = startDateTime;
        OffsetDateTime originalEndDateTime = endDateTime;

        // adjust start date time if it falls on the weekend
        if (DayOfWeek.SATURDAY.equals(startDay) || DayOfWeek.SUNDAY.equals(startDay)) {
            startDateTime = startDateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).truncatedTo(DAYS);
            startDay = DayOfWeek.MONDAY;
        }

        // adjust end date time if it falls on the weekend
        if (DayOfWeek.SATURDAY.equals(endDay) || DayOfWeek.SUNDAY.equals(endDay)) {
            endDateTime = endDateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY)).truncatedTo(DAYS);
            endDay = DayOfWeek.SATURDAY;
        }

        // find the number of days between the dates
        long days = getDaysBetweenDateTimes();
        long weeks = getCompleteWeeksBetweenDateTimes();

        // remove weekends by subtracting 2 for each week
        long weekdays = days - (2 * weeks);

        // if the start date time is after the end date time (in terms of relative position in the week), then another weekend needs to be accounted for
        if (startDay.getValue() > endDay.getValue() || (startDay.getValue() == endDay.getValue() && startDateTime.toLocalTime().isAfter(endDateTime.toLocalTime()))) {
            weekdays = weekdays - 2;
        }

        // reset start and end date times to original values
        startDateTime = originalStartDateTime;
        endDateTime = originalEndDateTime;

        return weekdays;
    }

    long getCompleteWeeksBetweenDateTimes() {
        return getUnitsBetweenDateTimes(WEEKS);
    }
}
