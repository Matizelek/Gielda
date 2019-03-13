package exhcengeDate;

import time.exchangeDate.ExchangeDate;
import time.exchangeDate.ExchangeDatePair;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ExchangeDateTest {

    @Test
    public void should_date_return_19_12_2018() {
        String sDate1 = "2018-12-19";

        assertThat(new ExchangeDate(sDate1).toString()).isEqualTo("19-12-2018");
    }


    @Test
    public void should_date_return_20_12_2018() {
        String sDate1 = "2018-12-19";
        String sDate2 = "2018-12-20";
        ExchangeDate date1 = new ExchangeDate(sDate1);
        ExchangeDate date2 = new ExchangeDate(sDate2);
        ExchangeDatePair datePair = new ExchangeDatePair(date1, date2);

        assertThat(datePair.getNextDate().get().getDate()).isEqualTo(date2.getDate());
    }


    @Test
    public void should_date_return_22() {
        String sDate1 = "2018-12-10";
        String sDate2 = "2019-01-01";
        ExchangeDate date1 = new ExchangeDate(sDate1);
        ExchangeDate date2 = new ExchangeDate(sDate2);
        ExchangeDatePair datePair = new ExchangeDatePair(date1, date2);

        assertThat(datePair.getDayDifferenceBetweenDates()).isEqualTo(22);
    }

    @Test
    public void should_throw_illegal_state_when_incorrect_string(){

        Throwable thrown = catchThrowable(() -> {
            ExchangeDate exchangeDate = new ExchangeDate("12_313_4129_akcje");
        });

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);


    }


//	 @Test
//	  public void should_return_details() {
//		 String sDate1="2018-12-19";
//		 InputDirectory inputDirectory = new InputDirectory();
//		 ExchengeDateRepository repository = inputDirectory.loadDirectory();
//		 System.err.println(repository.getDetails());
//	        assertThat( new ExchangeDate(sDate1)).isEqualTo("2018-12-19");
//	    }
//	

}
