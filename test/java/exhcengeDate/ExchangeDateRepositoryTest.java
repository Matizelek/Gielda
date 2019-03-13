package exhcengeDate;

import time.exchangeDate.ExchangeDate;
import time.exchangeDate.ExchengeDateRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ExchangeDateRepositoryTest {

    @Test
    public void should_date_return_19_12_2018() {
        List<ExchangeDate> led = new ArrayList<>();
        led.add(new ExchangeDate("2018-12-19"));
        led.add(new ExchangeDate("2018-12-20"));
        led.add(new ExchangeDate("2018-12-31"));

        assertThat(new ExchengeDateRepository(led).getFirstDay().toString()).isEqualTo("19-12-2018");
    }


    @Test
    public void should_date_return_31_12_2018() {
        List<ExchangeDate> led = new ArrayList<>();
        led.add(new ExchangeDate("2018-12-19"));
        led.add(new ExchangeDate("2018-12-20"));
        led.add(new ExchangeDate("2018-12-31"));

        assertThat(new ExchengeDateRepository(led).getLastDay().toString()).isEqualTo("31-12-2018");
    }

    @Test
    public void should_date_return_list_of_three_dates() {
        List<ExchangeDate> exchangeDates = new ArrayList<>();
        exchangeDates.add(new ExchangeDate("2018-12-20"));
        exchangeDates.add(new ExchangeDate("2018-12-31"));
        exchangeDates.add(new ExchangeDate("2018-12-19"));

        ExchengeDateRepository repository = new ExchengeDateRepository(exchangeDates);

        exchangeDates.sort(Comparator.comparing(ExchangeDate::getDate));

        for (int i = 0; i < exchangeDates.size(); i++) {
           assertThat(repository.getExchangeDatePairs().get(i).getDate()).isEqualTo(exchangeDates.get(i));
        }
    }

        @Test
        public void should_date_return_11 () {
            List<ExchangeDate> led = new ArrayList<>();
            led.add(new ExchangeDate("2018-12-19"));
            led.add(new ExchangeDate("2018-12-20"));
            led.add(new ExchangeDate("2018-12-31"));

            assertThat(new ExchengeDateRepository(led).getGapsCount()).isEqualTo(10);
        }

        @Test
        public void should_date_return_12 () {
            List<ExchangeDate> led = new ArrayList<>();
            led.add(new ExchangeDate("2018-12-19"));
            led.add(new ExchangeDate("2018-12-20"));
            led.add(new ExchangeDate("2018-12-31"));

            assertThat(new ExchengeDateRepository(led).getDateRepositoryRange()).isEqualTo(12);
        }

        @Test
        public void should_date_return_4 () {
            List<ExchangeDate> led = new ArrayList<>();
            led.add(new ExchangeDate("2018-12-19"));
            led.add(new ExchangeDate("2018-12-20"));
            led.add(new ExchangeDate("2018-12-31"));
            led.add(new ExchangeDate("2019-01-01"));

            assertThat(new ExchengeDateRepository(led).getDatesCount()).isEqualTo(4);
        }

        @Test
        public void should_date_return_1 () {
            List<ExchangeDate> led = new ArrayList<>();
            led.add(new ExchangeDate("2018-12-19"));
            led.add(new ExchangeDate("2018-12-19"));
            led.add(new ExchangeDate("2018-12-19"));
            led.add(new ExchangeDate("2018-12-19"));

            assertThat(new ExchengeDateRepository(led).getDatesCount()).isEqualTo(1);
        }


        @Test
        public void error_test () {
            List<ExchangeDate> led = new ArrayList<>();
            Throwable thrown = catchThrowable(() -> {
                ExchengeDateRepository ed = new ExchengeDateRepository(led);
            });

            assertThat(thrown).isInstanceOf(IllegalStateException.class);
        }

        @Test
        public void dsadas(){
            List<ExchangeDate> led = new ArrayList<>();
            led.add(new ExchangeDate("2018-12-19"));
            led.add(new ExchangeDate("2018-12-20"));
            led.add(new ExchangeDate("2018-12-31"));
            led.add(new ExchangeDate("2019-01-06"));

            ExchengeDateRepository repository = new ExchengeDateRepository(led);
            int dateRepositoryRange = repository.getDateRepositoryRange();
            System.out.println(dateRepositoryRange);
            System.out.println(repository.getFirstDay());
            System.out.println(repository.getLastDay());


        }

    }
