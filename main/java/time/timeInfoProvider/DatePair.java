package time.timeInfoProvider;

import time.DateUtils;

import java.util.Date;

public class DatePair {

    private final Date realDate;
    private final Date simulatedDate;

    public DatePair(Date realDate, Date simulatedDate) {
        this.realDate = realDate;
        this.simulatedDate = simulatedDate;
    }

    public Date getRealDate() {
        return realDate;
    }

    public Date getSimulatedDate() {
        return simulatedDate;
    }

    public int getDifferenceBetweenRealDateAndNow(Date currentDate) {
        return DateUtils.getDifferenceInDays(getRealDate(), currentDate);
    }


}
