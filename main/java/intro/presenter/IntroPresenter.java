package intro.presenter;

import time.timeOfDay.TimeOfDay;
import user.User;

public interface IntroPresenter {

    void start(User user, TimeOfDay timeOfDay);

    void nextSimulatedDate(User user);
    
    void showExchangePurchaseDetails(TimeOfDay timeOfDay, User user, int companyId);

	void showAllExchangePurchase(TimeOfDay timeOfDay, User user);
}
