package time.timeOfDay;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeOfDayProvider {

    private static int openingHour = 0;
    private static int closingHour = 24;

    private static int difference = closingHour - openingHour;
    private static int quarterTime = (int) (((float) difference) / 4f);

    public static TimeOfDay getTimeOfDay(Date date) {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);


        int hour = calendar.get(Calendar.HOUR_OF_DAY) + 1;

        if (hour == 24) hour = 0;

        if (hour < 0 || hour > 24) throw new IllegalArgumentException();


        if (hour <= quarterTime) {
            return TimeOfDay.FIRST_Q;
        }

        if (hour <= quarterTime*2) {
            return TimeOfDay.SECOND_Q;
        }

        if (hour <= quarterTime*3) {
            return TimeOfDay.THIRD_Q;
        }

        return TimeOfDay.FORTH_Q;

    }


}
