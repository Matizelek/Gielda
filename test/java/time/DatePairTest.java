package time;

import org.junit.Test;
import time.timeInfoProvider.DatePair;

import java.util.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class DatePairTest {

    @Test
    public void difference_bettwen_real_date_and_now_should_be_3() {
        String s1 = "2019-03-10";
        String s2 = "2018-12-10";


        DatePair pair = new DatePair(DateUtils.toDefaultDate(s1), DateUtils.toDefaultDate(s2));

        Date testCurrentDate = DateUtils.toDefaultDate("2019-03-13");

        assertThat(pair.getDifferenceBetweenRealDateAndNow(testCurrentDate)).isEqualTo(3);

    }

}
