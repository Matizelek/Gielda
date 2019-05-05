package time.timeManager;

import time.exchangeDate.ExchengeDateRepository;
import time.timeInfoProvider.DatePair;
import time.timeInfoProvider.TimeInfoProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import DataBase.DbConnection;
import DataBase.TimeManagerSQL;

public class TimeManager {

    private final ExchengeDateRepository repository;
    private final TimeInfoProvider provider;
    private static Date currentDate;


    public TimeManager(ExchengeDateRepository repository, TimeInfoProvider provider) {
        this.repository = repository;
        this.provider = provider;
    }

	public Date simulateCurrentDate(Date realCurrentDate) {

		DatePair datePair = provider.getLastLoadedDateWithRealDate();
		if (datePair.getRealDate() == null || datePair.getSimulatedDate() == null) {
			
			currentDate = repository.getFirstDay().getDate();
		} else {

			int difference = datePair.getDifferenceBetweenRealDateAndNow(realCurrentDate);

			Calendar instance = Calendar.getInstance();
			instance.setTime(datePair.getSimulatedDate());
			instance.add(Calendar.DAY_OF_MONTH, difference);

			Date dateAsTime = instance.getTime();

			if (dateAsTime.after(repository.getLastDay().getDate())
					|| dateAsTime.equals(repository.getLastDay().getDate())) {
				currentDate = repository.getLastDay().getDate();
			} else if (dateAsTime.before(repository.getFirstDay().getDate())) {
				currentDate = repository.getFirstDay().getDate();
			} else {
				currentDate = repository.getEqualsOrBeforeDate(dateAsTime).isPresent()
						? repository.getEqualsOrBeforeDate(dateAsTime).get()
						: repository.getFirstDay().getDate();
			}
		}
		
		Connection conn = null;
		try {
			conn = DbConnection.getConnectionAndStartTransaction();
		TimeManagerSQL.setTimeManager(currentDate, conn);
		DbConnection.commitTransactionAndCloseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DbConnection.closeConnectionAndRollBackTransaction(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return currentDate;

	}

	public static Date getCurrentDate() {
		return currentDate;
	}


}
