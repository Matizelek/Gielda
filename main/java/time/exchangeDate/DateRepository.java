package time.exchangeDate;

import java.util.List;

public interface DateRepository {

	int getDateRepositoryRange();
	
	int getDatesCount();
	
	String getDetails();
	
	int getGapsCount();
	
	String getList();
	
	ExchangeDate getFirstDay();
	
	ExchangeDate getLastDay();
	
	List<ExchangeDatePair> getExchangeDatePairs();
	

	
}
