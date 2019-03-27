package time.timeManager;

import time.DateUtils;
import time.exchangeDate.ExchengeDateRepository;
import time.timeInfoProvider.DatePair;
import time.timeInfoProvider.TimeInfoProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeManager {

    private final ExchengeDateRepository repository;
    private final TimeInfoProvider provider;
    private Date currentDate;

    public TimeManager(ExchengeDateRepository repository, TimeInfoProvider provider) {
        this.repository = repository;
        this.provider = provider;
        simulateCurrentDate();
    }

    private void simulateCurrentDate() {

        DatePair datePair = provider.getLastLoadedDateWithRealDate();

        int difference = datePair.getDifferenceBetweenRealDateAndNow(new Date());

        Calendar instance = Calendar.getInstance();
        instance.setTime(datePair.getSimulatedDate());
        instance.add(Calendar.DAY_OF_MONTH, difference);

        String dateAsTime = DateUtils.toDefaultString(instance.getTime());
        System.out.println(dateAsTime);

        if(DateUtils.toDefaultDate(dateAsTime).after(repository.getLastDay().getDate()) 
        		|| DateUtils.toDefaultDate(dateAsTime).before(repository.getFirstDay().getDate())) {
        	setCurrentDate(repository.getFirstDay().getDate());
        }else {
            setCurrentDate(repository.getEqualsOrBeforeDate(DateUtils.toDefaultDate(dateAsTime)).isPresent()?
            		repository.getEqualsOrBeforeDate(DateUtils.toDefaultDate(dateAsTime)).get():repository.getFirstDay().getDate());
        }
        
        System.out.println(new SimpleDateFormat("dd-MM-yyyy").format(currentDate));

        // pobrać z bazy (obecnie z plików) ostatnią wczytaną datę, datę rzeczywistą w której była wczytana'
        // obliczyć ile dni minęło, tyle pominąć dni

        // sprawdzić czy pasuje do zakresu, jeśli nie to restart do początku
        // sprawdzić czy nie trafia w przerwę, jeśli trafi w przerwę, to bierze poprzedni dzień


    }
    
    private void setCurrentDate(Date currentSimulatedDay) {
    	this.currentDate = currentSimulatedDay;
    }


}
