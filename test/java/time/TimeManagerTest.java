package time;

import org.junit.Test;
import org.mockito.Mockito;
import time.exchangeDate.ExchangeDate;
import time.exchangeDate.ExchengeDateRepository;
import time.timeInfoProvider.DatePair;
import time.timeInfoProvider.TimeInfoProvider;
import time.timeManager.TimeManager;
import time.timeOfDay.TimeOfDayProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TimeManagerTest {


    @Test
    public void should_return_2018_12_20() {

        String s1 = "2019-03-20";
        String s2 = "2018-12-18";
        String currentDateString = "2019-03-28";

        Date currentDate = DateUtils.toDefaultDate(currentDateString);

        DatePair pair = new DatePair(DateUtils.toDefaultDate(s1), DateUtils.toDefaultDate(s2));

        TimeInfoProvider mock = Mockito.mock(TimeInfoProvider.class);

        Mockito.when(mock.getLastLoadedDateWithRealDate()).thenReturn(pair);


        mock.getLastLoadedDateWithRealDate();

        List<ExchangeDate> dates = new ArrayList<>();

        Date expectedDate = DateUtils.toDefaultDate("2018-12-20");

        dates.add(new ExchangeDate("2018-12-19"));
        dates.add(new ExchangeDate(expectedDate));
        dates.add(new ExchangeDate("2018-12-31"));
        dates.add(new ExchangeDate("2019-01-01"));

        ExchengeDateRepository exchengeDateRepository = new ExchengeDateRepository(dates);

        TimeManager timeManager = new TimeManager(exchengeDateRepository, mock);

        Date simulatedCurrentDate = timeManager.simulateCurrentDate(currentDate);

        assertThat(simulatedCurrentDate).isEqualTo(expectedDate);
    }


    @Test
    public void should_return_2019_01_01() {

        String s1 = "2019-01-20";
        String s2 = "2018-12-18";

        String currentDateString = "2019-03-28";

        Date currentDate = DateUtils.toDefaultDate(currentDateString);

        DatePair pair = new DatePair(DateUtils.toDefaultDate(s1), DateUtils.toDefaultDate(s2));

        TimeInfoProvider mock = Mockito.mock(TimeInfoProvider.class);

        Mockito.when(mock.getLastLoadedDateWithRealDate()).thenReturn(pair);


        mock.getLastLoadedDateWithRealDate();

        List<ExchangeDate> dates = new ArrayList<>();

        Date expectedDate = DateUtils.toDefaultDate("2019-01-01");

        dates.add(new ExchangeDate("2018-12-19"));
        dates.add(new ExchangeDate("2018-12-20"));
        dates.add(new ExchangeDate("2018-12-31"));
        dates.add(new ExchangeDate(expectedDate));

        ExchengeDateRepository exchengeDateRepository = new ExchengeDateRepository(dates);

        TimeManager timeManager = new TimeManager(exchengeDateRepository, mock);

        Date simulatedCurrentDate = timeManager.simulateCurrentDate(currentDate);

        assertThat(simulatedCurrentDate).isEqualTo(expectedDate);
    }


    @Test
    public void shouldReturn2018_12_19() {

        String s1 = "2019-01-20";
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

    @Test
    public void shouldReturn2018_12_31() {

        String s1 = "2019-03-15";
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
