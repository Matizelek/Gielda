package time.exchangeDate;

import time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExchangeDate {

    final private Date exchangeDate;
    final private String dataFormatOutput = "yyyy-MM-dd";
    private SimpleDateFormat formatterOutput = new SimpleDateFormat(dataFormatOutput);

    public ExchangeDate(String date) {
        this.exchangeDate = DateUtils.toDefaultDate(date);
    }

    public ExchangeDate(Date date) {
        this.exchangeDate = date;
    }

    public Date getDate() {
        return exchangeDate;
    }

    public String toString() {
        return formatterOutput.format(exchangeDate);
    }

    public boolean equals(final Object ed) {

        if (ed instanceof ExchangeDate) {
            final ExchangeDate date = (ExchangeDate) ed;
            return (this.exchangeDate.equals(date.exchangeDate));
        }

        return false;
    }


    public int hashCode() {
        int result = 0;
        result = (int) (exchangeDate.getTime() / 11);
        return result;
    }

}
