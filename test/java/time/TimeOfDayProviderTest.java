package time;

import org.junit.Test;
import time.timeOfDay.TimeOfDay;
import time.timeOfDay.TimeOfDayProvider;

import java.util.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TimeOfDayProviderTest {


    @Test
    public void shouldReturnSecondQ() {

        TimeOfDay timeOfDay = TimeOfDayProvider.getTimeOfDay(DateUtils.createHourDate("10:21:12"));

        assertThat(timeOfDay).isEqualTo(TimeOfDay.SECOND_Q);

    }

    @Test
    public void shouldReturnThirdQ() {

        TimeOfDay timeOfDay = TimeOfDayProvider.getTimeOfDay(DateUtils.createHourDate("13:21:12"));

        assertThat(timeOfDay).isEqualTo(TimeOfDay.THIRD_Q);

    }

    @Test
    public void shouldReturnThirdQAgain() {

        TimeOfDay timeOfDay = TimeOfDayProvider.getTimeOfDay(DateUtils.createHourDate("14:21:12"));

        assertThat(timeOfDay).isEqualTo(TimeOfDay.THIRD_Q);

    }

    @Test
    public void shouldReturnForthQ() {

        TimeOfDay timeOfDay = TimeOfDayProvider.getTimeOfDay(DateUtils.createHourDate("19:21:12"));

        assertThat(timeOfDay).isEqualTo(TimeOfDay.FORTH_Q);

    }

    @Test
    public void shouldReturnForthQAgain() {

        TimeOfDay timeOfDay = TimeOfDayProvider.getTimeOfDay(DateUtils.createHourDate("23:50:12"));

        assertThat(timeOfDay).isEqualTo(TimeOfDay.FORTH_Q);

    }


    @Test
    public void shouldReturnFirstQAgain() {

        TimeOfDay timeOfDay = TimeOfDayProvider.getTimeOfDay(DateUtils.createHourDate("00:50:12"));

        assertThat(timeOfDay).isEqualTo(TimeOfDay.FIRST_Q);

    }
}
