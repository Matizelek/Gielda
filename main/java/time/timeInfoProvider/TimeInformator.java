package time.timeInfoProvider;

import DataBase.TimeManagerSQL;

public class TimeInformator  implements TimeInfoProvider {

	
	public TimeInformator() {
		
	}
	
	@Override
	public DatePair getLastLoadedDateWithRealDate() {
		return new DatePair(TimeManagerSQL.getLastRealDate(),TimeManagerSQL.getLastSimulatedDate());
	}
	
	

}
