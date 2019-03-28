package time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    static public int getDifferenceInDays(Date firstDate, Date secondDate) {
        long differenceInMilis = Math.abs(firstDate.getTime() - secondDate.getTime());
        return (int) TimeUnit.DAYS.convert(differenceInMilis, TimeUnit.MILLISECONDS);
    }

    static public Date toDefaultDate(String stringValue) {
        String dataFormat = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(dataFormat);
        try {
            return formatter.parse(stringValue);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    static public String toDefaultString(Date date) {
        String dataFormat = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(dataFormat);
        return formatter.format(date);
    }

    static public Date createHourDate(String hourString){
        String dataFormat = "hh:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(dataFormat);
        try {
            return formatter.parse(hourString);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

}
