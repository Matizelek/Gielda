package time.timeManager;

import time.exchangeDate.ExchengeDateRepository;
import time.timeInfoProvider.DatePair;
import time.timeInfoProvider.TimeInfoProvider;

import java.util.Calendar;
import java.util.Date;

public class TimeManager {

    private final ExchengeDateRepository repository;
    private final TimeInfoProvider provider;


    public TimeManager(ExchengeDateRepository repository, TimeInfoProvider provider) {
        this.repository = repository;
        this.provider = provider;
    }

    public Date simulateCurrentDate(Date realCurrentDate) {

        DatePair datePair = provider.getLastLoadedDateWithRealDate();

        int difference = datePair.getDifferenceBetweenRealDateAndNow(realCurrentDate);

        Calendar instance = Calendar.getInstance();
        instance.setTime(datePair.getSimulatedDate());
        instance.add(Calendar.DAY_OF_MONTH, difference);

        Date dateAsTime = instance.getTime();

        Date currentDate;
        if (dateAsTime.after(repository.getLastDay().getDate()) || dateAsTime.equals(repository.getLastDay().getDate())) {
            currentDate = repository.getLastDay().getDate();
        } else if (dateAsTime.before(repository.getFirstDay().getDate())) {
            currentDate = repository.getFirstDay().getDate();
        } else {
            currentDate = repository.getEqualsOrBeforeDate(dateAsTime).isPresent() ?
                    repository.getEqualsOrBeforeDate(dateAsTime).get() : repository.getFirstDay().getDate();
        }

        return currentDate;

    }




}
