import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.*;

public class AligentChallenge {

    private static final String SECONDS_PARAM = "S";
    private static final String MINUTES_PARAM = "M";
    private static final String HOURS_PARAM   = "H";
    private static final String YEARS_PARAM   = "Y";

    public static void main(String[] args) {
        // check the number of input parameters - must be at least 2
        if (args.length < 2 || args.length > 3) {
            printUsage();
            return;
        }

        // first two parameters are datetimes
        OffsetDateTime firstDateTime = DateTimeHelper.parseDateTime(args[0]);
        OffsetDateTime secondDateTime = DateTimeHelper.parseDateTime(args[1]);

        // if either date is null, just return, as cannot perform checks
        if (firstDateTime == null || secondDateTime == null) {
            return;
        }

        // ensure date times are ordered
        OffsetDateTime[] dateTimes = DateTimeHelper.orderDateTimes(firstDateTime, secondDateTime);

        // check for additional unit parameter
        ChronoUnit units = null;

        if (args.length > 2) {
            units = parseUnitsParameter(args[2]);
            if (units == null) {
                printUsage();
                return;
            }
        }

        // perform and display results of calculations
        runCalculations(dateTimes[0], dateTimes[1], units);
    }

    private static void printUsage() {
        System.out.println("Usage: java AligentChallenge DATETIME DATETIME [CONVERT_TO_UNITS]");
        System.out.println(" - DATETIME : Date time in following format: YYYY-MM-DDTHH:mm[:SS][OFFSET]");
        System.out.println(" - OFFSET : Timezone offset in format: +HH:mm:SS");
        System.out.println(" - CONVERT_TO_UNITS : Additional time unit to calculate, S/M/H/Y -> Seconds/Minutes/Hours/Years");
        System.out.println();
        System.out.println("e.g. java AligentChallenge 2018-01-01T12:00:00 2018-01-01T19:00:00+04:00:00 H");
    }

    private static void runCalculations(OffsetDateTime startDateTime, OffsetDateTime endDateTime, ChronoUnit units) {
        DurationInfo durationInfo = new DurationInfo();
        durationInfo.setDateTimes(startDateTime, endDateTime);

        System.out.println("Between " + DateTimeHelper.getFormattedDateTime(startDateTime) +
                " and " + DateTimeHelper.getFormattedDateTime(endDateTime) + ", there are:");

        // get number of days between dates
        long days = durationInfo.getDaysBetweenDateTimes();
        System.out.print(days + " Complete Days");
        if (units != null) {
            long value = DateTimeHelper.getConvertedValue(days, DAYS, units);
            System.out.print("(" + value + " " + units + ")");
        }
        System.out.println();

        // weekdays
        long weekdays = durationInfo.getWeekdaysBetweenDateTimes();
        System.out.print(weekdays + " Complete Weekdays");
        if (units != null) {
            long value = DateTimeHelper.getConvertedValue(weekdays, DAYS, units);
            System.out.print("(" + value + " " + units + ")");
        }
        System.out.println();

        // complete weeks
        long weeks = durationInfo.getCompleteWeeksBetweenDateTimes();
        System.out.print(weeks + " Complete weeks");
        if (units != null) {
            long value = DateTimeHelper.getConvertedValue(weeks, WEEKS, units);
            System.out.print("(" + value + " " + units + ")");
        }
        System.out.println();

        // additional units if specified
        if (units != null) {
            long unitsValue = durationInfo.getUnitsBetweenDateTimes(units);
            System.out.println(unitsValue + " " + units);
        }
    }

    // determine correct ChronoUnit object to use for conversions
    private static ChronoUnit parseUnitsParameter(String parameter) {
        switch (parameter) {
            case SECONDS_PARAM:
                return SECONDS;
            case MINUTES_PARAM:
                return MINUTES;
            case HOURS_PARAM:
                return HOURS;
            case YEARS_PARAM:
                return YEARS;
            default:
                System.err.println("Error parsing units parameter: " + parameter);
                return null;
        }
    }
}
