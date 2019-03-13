package time.exchangeDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExchangeDate {

    final private Date exchengeData;
    final private String dataFormat = "yyyy-MM-dd";
    final private String dataFormatOutput = "dd-MM-yyyy";
    SimpleDateFormat formatter = new SimpleDateFormat(dataFormat);
    SimpleDateFormat formatterOutput = new SimpleDateFormat(dataFormatOutput);

    public ExchangeDate(String date) {
        try {
            this.exchengeData = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public Date getDate() {
        return exchengeData;
    }

    public String toString() {
        return formatterOutput.format(exchengeData);
    }

    public boolean equals(final Object ed) {

        if (ed instanceof ExchangeDate) {
            final ExchangeDate date = (ExchangeDate) ed;
            return (this.exchengeData.equals(date.exchengeData));
        }

        return false;
    }


    public int hashCode() {
        int result = 0;
        result = (int) (exchengeData.getTime() / 11);
        return result;
    }

}
