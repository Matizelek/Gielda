package exhcengeDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import exchengeDate.ExchengeDate;
import exchengeDate.ExchengeDateRepository;
import money.Money;

public class ExchengeDateRepositoryTest {

	@Test
	 public void should_date_return_19_12_2018() {
		List<ExchengeDate> led = new ArrayList<>();
		led.add(new ExchengeDate("2018-12-19"));
		led.add(new ExchengeDate("2018-12-20"));
		led.add(new ExchengeDate("2018-12-31"));
	     
		assertThat( new ExchengeDateRepository(led).getFirstDay().toString()).isEqualTo("19-12-2018");
	    }
	
	
	@Test
	 public void should_date_return_31_12_2018() {
		List<ExchengeDate> led = new ArrayList<>();
		led.add(new ExchengeDate("2018-12-19"));
		led.add(new ExchengeDate("2018-12-20"));
		led.add(new ExchengeDate("2018-12-31"));
	     
		assertThat( new ExchengeDateRepository(led).getLastDay().toString()).isEqualTo("31-12-2018");
	    }
	
	@Test
	 public void should_date_return_list_of_three_dates() {
		List<ExchengeDate> led = new ArrayList<>();
		led.add(new ExchengeDate("2018-12-20"));
		led.add(new ExchengeDate("2018-12-31"));
		led.add(new ExchengeDate("2018-12-19"));
		
	     
		assertThat( new ExchengeDateRepository(led).getList()).isEqualTo("19-12-2018\n"+"20-12-2018\n"+"31-12-2018");
	    }
	
	@Test
	 public void should_date_return_11() {
		List<ExchengeDate> led = new ArrayList<>();
		led.add(new ExchengeDate("2018-12-19"));
		led.add(new ExchengeDate("2018-12-20"));
		led.add(new ExchengeDate("2018-12-31"));
	     
		assertThat( new ExchengeDateRepository(led).getGapsCount()).isEqualTo(11);
	    }
	
	@Test
	 public void should_date_return_12() {
		List<ExchengeDate> led = new ArrayList<>();
		led.add(new ExchengeDate("2018-12-19"));
		led.add(new ExchengeDate("2018-12-20"));
		led.add(new ExchengeDate("2018-12-31"));
	     
		assertThat( new ExchengeDateRepository(led).getDateRepositoryRange()).isEqualTo(12);
	    }
	
	@Test
	 public void should_date_return_4() {
		List<ExchengeDate> led = new ArrayList<>();
		led.add(new ExchengeDate("2018-12-19"));
		led.add(new ExchengeDate("2018-12-20"));
		led.add(new ExchengeDate("2018-12-31"));
		led.add(new ExchengeDate("2019-01-01"));
	     
		assertThat( new ExchengeDateRepository(led).getDatesCount()).isEqualTo(4);
	    }
	
	@Test
	 public void should_date_return_1() {
		List<ExchengeDate> led = new ArrayList<>();
		led.add(new ExchengeDate("2018-12-19"));
		led.add(new ExchengeDate("2018-12-19"));
		led.add(new ExchengeDate("2018-12-19"));
		led.add(new ExchengeDate("2018-12-19"));
	     
		assertThat( new ExchengeDateRepository(led).getDatesCount()).isEqualTo(1);
	    }
	
	
	@Test
	 public void error_test() {
		List<ExchengeDate> led = new ArrayList<>();
		Throwable thrown = catchThrowable(() -> {
			ExchengeDateRepository ed = new ExchengeDateRepository(led);
        });

        assertThat(thrown).isInstanceOf(NullPointerException.class);
	    }
	
}
