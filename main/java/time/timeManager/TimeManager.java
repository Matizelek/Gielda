package time.timeManager;

import time.exchangeDate.ExchengeDateRepository;
import time.timeInfoProvider.DatePair;
import time.timeInfoProvider.TimeInfoProvider;

import java.util.Calendar;
import java.util.Date;

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
			TimeManagerSQL.setDateFirstTime(currentDate);
		} else {
			updateDate(datePair,realCurrentDate);
		}
		return currentDate;
	}
	
	private void updateDate(DatePair datePair,Date realCurrentDate) {
		int difference = datePair.getDifferenceBetweenRealDateAndNow(realCurrentDate);

		Calendar instance = Calendar.getInstance();
		instance.setTime(datePair.getSimulatedDate());
		instance.add(Calendar.DAY_OF_MONTH, difference);
		Date dateAsTime = instance.getTime();
		chooseSimulatedDate(dateAsTime);
		TimeManagerSQL.updateDate(dateAsTime);
	}
	
	private void chooseSimulatedDate(Date dateAsTime) {
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
	
	public Date returnEqualsOrAfterDate(int days) {
		 Date resultDate;
		
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.DAY_OF_MONTH, days);

		Date dateAsTime = instance.getTime();
		
		if (dateAsTime.after(repository.getLastDay().getDate())
				|| dateAsTime.equals(repository.getLastDay().getDate())) {
			resultDate = repository.getLastDay().getDate();
		} else if (dateAsTime.before(repository.getFirstDay().getDate())) {
			resultDate = repository.getFirstDay().getDate();
		} else {
			resultDate = repository.getEqualsOrAfterDate(dateAsTime).isPresent()
					? repository.getEqualsOrAfterDate(dateAsTime).get()
					: repository.getFirstDay().getDate();
		}
		
		return resultDate;
	}

	public static Date getCurrentDate() {
		return currentDate;
	}

	public Date simulateNextDate() {
		DatePair datePair = provider.getLastLoadedDateWithRealDate();
		Calendar instance = Calendar.getInstance();
		instance.setTime(datePair.getSimulatedDate());
		instance.add(Calendar.DAY_OF_MONTH, 1);
		Date dateAsTime = instance.getTime();

		if (dateAsTime.before(repository.getLastDay().getDate())) {

			currentDate = repository.getEqualsOrAfterDate(dateAsTime).isPresent()
					? repository.getEqualsOrAfterDate(dateAsTime).get()
					: repository.getLastDay().getDate();
			TimeManagerSQL.updateDate(currentDate);

		} else {
			currentDate = repository.getLastDay().getDate();
			TimeManagerSQL.updateDate(currentDate);
		}
		return currentDate;
	}

}
