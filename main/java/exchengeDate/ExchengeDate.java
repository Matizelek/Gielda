package exchengeDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ExchengeDate {

	private Date exchengeData;
	private ExchengeDate nextExchengeDate;
	private int differenceBetweenDates = 0;
	private int time = 0;
	final private String dataFormat = "yyyy-MM-dd";
	final private String dataFormatOutput = "dd-MM-yyyy";
	SimpleDateFormat formatter = new SimpleDateFormat(dataFormat);
	SimpleDateFormat formatterOutput = new SimpleDateFormat(dataFormatOutput);
	
	public ExchengeDate(String date) {
		
		try {
			this.exchengeData=formatter.parse(date);
			nextExchengeDate = this;
			time = (int) formatter.parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Date getDate() {
		return exchengeData;
	}
	
	public String toString() {
		return formatterOutput.format(exchengeData);
	}
	
	public void setNextExchengeDate(ExchengeDate nextDate) {
		this.nextExchengeDate = nextDate;
		setDifferenceBetweenDates();
	}
	
	public ExchengeDate getNextExchengeDate() {
		return nextExchengeDate ;
	}
	
	public String getNextExchengeDateString() {
		return formatterOutput.format(nextExchengeDate);
	}
	
	private void setDifferenceBetweenDates() {
		this.differenceBetweenDates = (int) TimeUnit.DAYS.convert(nextExchengeDate.getDate().getTime() 
				- exchengeData.getTime(), TimeUnit.MILLISECONDS);
	}
	
	public int getDifferenceBetweenDates() {
		return differenceBetweenDates ;
	}
	
	public boolean equals(final Object ed) {
	   if (ed == null) {
	         return false;
	      }
	      final ExchengeDate book = (ExchengeDate) ed;
	         return (this.exchengeData.equals(book.exchengeData));
	    }
	
	
	public int hashCode() {
		int result = 0;
		result = (time/11);
		return result;
	}
	
}
