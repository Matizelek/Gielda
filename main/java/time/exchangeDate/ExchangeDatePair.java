package time.exchangeDate;

import time.DateUtils;

import java.util.Optional;

public class ExchangeDatePair {

    private final ExchangeDate date;
    private final ExchangeDate nextDate;
    private final int dayDifferenceBetweenDates;

    public ExchangeDatePair(ExchangeDate date, ExchangeDate nextDate) {
        if (date == null) throw new IllegalArgumentException("date cannot be null");

        this.date = date;
        this.nextDate = nextDate;

        if (nextDate == null) {
            dayDifferenceBetweenDates = 0;
        } else {
            dayDifferenceBetweenDates = DateUtils.getDifferenceInDays(date.getDate(), nextDate.getDate());
        }

    }


    public ExchangeDate getDate() {
        return date;
    }

    public Optional<ExchangeDate> getNextDate() {
    	if(nextDate!=null) {
            return Optional.of(nextDate);
    	}else {
    		return Optional.of(date);
    	}
    }

    public int getDayDifferenceBetweenDates() {
        return dayDifferenceBetweenDates;
    }
}
