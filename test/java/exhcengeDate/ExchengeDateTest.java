package exhcengeDate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import exchengeDate.ExchengeDate;

public class ExchengeDateTest {

	 @Test
	 public void should_date_return_19_12_2018() {
		 String sDate1="2018-12-19";
	     
		 assertThat( new ExchengeDate(sDate1).toString()).isEqualTo("19-12-2018");
	    }
	 

	 @Test
	 public void should_date_return_20_12_2018() {
		 String sDate1="2018-12-19";
		 String sDate2="2018-12-20";
		 ExchengeDate ed =  new ExchengeDate(sDate1);
		 ed.setNextExchengeDate(new ExchengeDate(sDate2));
	     
		 assertThat( ed.getNextExchengeDate().toString()).isEqualTo("20-12-2018");
	    }
	 
	 
	 @Test
	 public void should_date_return_22() {
		 String sDate1="2018-12-10";
		 String sDate2="2019-01-01";
		 ExchengeDate ed =  new ExchengeDate(sDate1);
		 ed.setNextExchengeDate(new ExchengeDate(sDate2));
	       
		 assertThat(ed.getDifferenceBetweenDates()).isEqualTo(22);
	    }
	 
	 
//	 @Test
//	  public void should_return_details() {
//		 String sDate1="2018-12-19";
//		 InputDirectory inputDirectory = new InputDirectory();
//		 ExchengeDateRepository repository = inputDirectory.loadDirectory();
//		 System.err.println(repository.getDetails());
//	        assertThat( new ExchengeDate(sDate1)).isEqualTo("2018-12-19");
//	    }
//	
	
}
