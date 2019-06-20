package buySale.view;

import java.util.List;

import user.User;

public interface BuySaleView {

	void onBuySuccess(ExchangeBuySaleViewModel exchangeBuySaleViewModel);
	
	void onBuyFail(String error);
	
	void onSaleSuccess(ExchangeBuySaleViewModel exchangeBuySaleViewModel);
	
	void onSaleFail(String error);
	
	void onReturn(User user);

	void showExchangeBuySaleModel(List<ExchangeBuySaleViewModel> exchangeBuySale);
}
