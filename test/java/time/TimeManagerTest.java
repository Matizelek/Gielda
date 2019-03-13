package time;

import org.junit.Test;
import org.mockito.Mockito;
import time.exchangeDate.ExchangeDate;
import time.exchangeDate.ExchengeDateRepository;
import time.timeInfoProvider.DatePair;
import time.timeInfoProvider.TimeInfoProvider;
import time.timeManager.TimeManager;

import java.util.ArrayList;
import java.util.List;

public class TimeManagerTest {


    @Test
    public void dsadas() {

        String s1 = "2019-03-10";
        String s2 = "2018-12-18";


        DatePair pair = new DatePair(DateUtils.toDefaultDate(s1), DateUtils.toDefaultDate(s2));

        TimeInfoProvider mock = Mockito.mock(TimeInfoProvider.class);

        Mockito.when(mock.getLastLoadedDateWithRealDate()).thenReturn(pair);


        mock.getLastLoadedDateWithRealDate();

        List<ExchangeDate> led = new ArrayList<>();

        led.add(new ExchangeDate("2018-12-19"));
        led.add(new ExchangeDate("2018-12-20"));
        led.add(new ExchangeDate("2018-12-31"));
        led.add(new ExchangeDate("2019-01-01"));

        ExchengeDateRepository exchengeDateRepository = new ExchengeDateRepository(led);

        TimeManager timeManager = new TimeManager(exchengeDateRepository, mock);


    }

}
