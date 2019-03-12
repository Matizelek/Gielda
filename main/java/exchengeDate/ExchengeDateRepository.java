package exchengeDate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ExchengeDateRepository {

	List<ExchengeDate> exchengeDateRepository = new ArrayList<>();
	private int dateRange = 0;
	private int size = 0;
	private ExchengeDate firstDay;
	private ExchengeDate lastDay;
	private int gapsCount;
	
	
	
	public ExchengeDateRepository(List<ExchengeDate> exchengeDates) {
		this.exchengeDateRepository =  sortAndSetNextDate(exchengeDates);;
		setVariables();
	}
	
	private void setVariables() {
		size = exchengeDateRepository.size();
		if (size < 1) {
			throw new NullPointerException();
		}
		firstDay = exchengeDateRepository.get(0);
		lastDay = exchengeDateRepository.get(size - 1);
		dateRange = (int) TimeUnit.DAYS.convert(lastDay.getDate().getTime() - firstDay.getDate().getTime(),
				TimeUnit.MILLISECONDS);
		for (ExchengeDate ed : exchengeDateRepository) {
			if (ed.getDifferenceBetweenDates() > 1) {
				gapsCount += ed.getDifferenceBetweenDates();
			}

		}
	}

	public int getDateRepositoryRange() {
		return dateRange;
	}
	
	public int getDatesCount() {
		return size;
	}
	
	public String getDetails() {
		String datails = "Zawartosc repository:\n"
				+ "Data Poczatkowa: "+exchengeDateRepository.get(0)+"\n"
				+ "Data Koncowa: "+exchengeDateRepository.get(size-1)+"\n"
				+ "Zakres dat: "+getDateRepositoryRange()+" dni\n"
				+ "Ilosc dat w repository: "+getDatesCount()+"\n";
		if(getGapsCount() >0) {
			datails += "Przerwy w datach: \n"
					+ getGapsString()
					+ "Laczna ilosc przerw: "+getGapsCount()+" dni";
		}else {
			datails +="Brak przerw";
		}
		
		return datails;
	}
	
	public int getGapsCount() {
		return gapsCount;
	}
	
	
	public String getGapsString() {
		return exchengeDateRepository.stream().map(e-> e.getDifferenceBetweenDates()>1?
				"\tMiedzy "+e.toString()+" a "+e.getNextExchengeDateString()+" przerwa "+e.getDifferenceBetweenDates()+" dni\n"
				:"").collect(Collectors.joining(""));
	}
	
	public String getList() {
		return exchengeDateRepository.stream().map(e-> e.toString()).collect(Collectors.joining("\n"));
	}
	
	public String getList2() {
		return exchengeDateRepository.stream().map(e-> e.getNextExchengeDate().toString()).collect(Collectors.joining("\n"));
	}
	
	private List<ExchengeDate> sortAndSetNextDate(List<ExchengeDate> exchengeDates) {
		List<ExchengeDate> exchengeDates2 =  exchengeDates.stream().distinct().sorted(Comparator.comparing(o -> o.getDate())).collect(Collectors.toList());
		if (exchengeDates2.size() >= 2) {
			for(int i = 0; i< exchengeDates2.size() - 1; i++) {
				exchengeDates2.get(i).setNextExchengeDate(exchengeDates2.get(i+1));
			}
		}
		return exchengeDates2;
	}
	
	public ExchengeDate getFirstDay() {
		return firstDay;
	}

	public ExchengeDate getLastDay() {
		return lastDay;
	}
	public List<ExchengeDate> getExchengeDateRepository() {
		return exchengeDateRepository;
	}
}
