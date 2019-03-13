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
        return Optional.of(nextDate);
    }

    public int getDayDifferenceBetweenDates() {
        return dayDifferenceBetweenDates;
    }
}
