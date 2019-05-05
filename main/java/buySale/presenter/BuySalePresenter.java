package buySale.presenter;

import buySale.view.ExchangeBuySaleViewModel;
import time.timeOfDay.TimeOfDay;

public interface BuySalePresenter {
	
	void start(TimeOfDay timeOfDay);
	
	void onBuy(ExchangeBuySaleViewModel exchangeBuySaleViewModel,int exchangeAmount);
	
	void onSale(ExchangeBuySaleViewModel exchangeBuySaleViewModel,int exchangeAmount);
	
	void viewExchanges();
}
