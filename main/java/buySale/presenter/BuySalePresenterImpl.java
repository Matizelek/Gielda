package buySale.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import buySale.model.BuySaleModel;
import buySale.resources.StringResourcesBuySale;
import buySale.view.BuySaleView;
import buySale.view.ExchangeBuySaleViewModel;
import exchange.Exchange;
import exchange.ExchangePurchase;
import exchange.repository.ExchangeRepository;
import time.timeOfDay.TimeOfDay;
import user.User;
import user.account.UserAccount;

public class BuySalePresenterImpl implements BuySalePresenter {
	final private BuySaleView buySaleView;
	final private BuySaleModel model;
	final private User user;
	final private UserAccount userAccount;
	final private ExchangeRepository exchangeRepository;
	
	public BuySalePresenterImpl(BuySaleView buySaleView, User user, UserAccount userAccount, ExchangeRepository exchangeRepository ) {
		this.buySaleView = buySaleView;
		this.user = user;
		this.userAccount = userAccount;
		this.exchangeRepository = exchangeRepository;
		this.model = new BuySaleModel();
	}
	
	 @Override
	    public void start(TimeOfDay timeOfDay) {

	        List<ExchangeBuySaleViewModel> viewModels = getExchangeBuySaleModels(timeOfDay, userAccount);

	        buySaleView.showExchangeBuySaleModel(viewModels);

	    }

	    private List<ExchangeBuySaleViewModel> getExchangeBuySaleModels(TimeOfDay timeOfDay, UserAccount account) {
	        List<ExchangePurchase> exchangePurchaseList = account.getExchangePurchaseList();

	        List<Exchange> exchanges = exchangeRepository.getAllExchanges();

	        List<ExchangeBuySaleViewModel> viewModels = new ArrayList<>();

	        for (Exchange exchan: exchanges){
	            Exchange exchange = exchan;
	            ExchangePurchase purchase = new ExchangePurchase(0L, 0, 0d, 0);
	            List<ExchangePurchase> purchaseExchantge = exchangePurchaseList.stream().filter(ep -> Objects.equals(ep.getCompanyId(), exchange.getCompanyId())).collect(Collectors.toList());
	            if(purchaseExchantge.size()==1) {
	            	purchase = exchangePurchaseList.get(0);
	            }

	            ExchangeBuySaleViewModel viewModel = mapToViewModel(timeOfDay, exchange, purchase);

	            viewModels.add(viewModel);
	        }
	        return viewModels;
	    }

	    private ExchangeBuySaleViewModel mapToViewModel(TimeOfDay timeOfDay, Exchange exchange, ExchangePurchase purchase) {
	        String currentPrice = exchange.getCurrentPrice(timeOfDay).toString("z³");
	        String purchasePrice = purchase.getPurchasePrice().toString("z³");

	        return new ExchangeBuySaleViewModel(
	        		exchange.getCompanyId(),
	                exchange.getCompanyName(),
	                exchange.getIsin(),
	                exchange.getAmount(),
	                currentPrice,
	                purchase.getAmount(),
	                purchasePrice);

	    }
	@Override
	public void onBuy(ExchangeBuySaleViewModel exchangeBuySaleViewModel,int exchangeAmount) {
		if((exchangeBuySaleViewModel.getCurrentValueDouble()*exchangeAmount)<=userAccount.getAccountBalanceDouble()) {
			if(exchangeBuySaleViewModel.getUserAmount()>0 
					&& Objects.equals(exchangeBuySaleViewModel.getCurrentValue(), exchangeBuySaleViewModel.getUserValue())) {
				model.buyUpdate(exchangeBuySaleViewModel, user, exchangeAmount, userAccount,exchangeBuySaleViewModel.getUserAmount() );
			}else {
				model.buy(exchangeBuySaleViewModel, user, exchangeAmount, userAccount);
			}
			buySaleView.onBuySuccess();
		}else {
			buySaleView.onBuyFail(StringResourcesBuySale.accountBalanceNotEnough);
		}

	}

	@Override
	public void onSale(ExchangeBuySaleViewModel exchangeBuySaleViewModel,int exchangeAmount) {
		if(exchangeBuySaleViewModel.getUserAmount()>0) {
			model.sale(exchangeBuySaleViewModel, user, exchangeAmount, userAccount);
			buySaleView.onSaleSuccess();
		}else {
			buySaleView.onSaleFail(StringResourcesBuySale.cantSale);
		}
	}

	@Override
	public void viewExchanges() {
		// TODO Auto-generated method stub

	}
}
