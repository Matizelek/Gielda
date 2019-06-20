package buySale.presenter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import DataBase.DbConnection;
import DataBase.ExchangePurchaseSQL;
import buySale.model.BuySaleModel;
import buySale.resources.StringResourcesBuySale;
import buySale.view.BuySaleView;
import buySale.view.ExchangeBuySaleViewModel;
import exchange.Exchange;
import exchange.ExchangePurchase;
import exchange.repository.ExchangeRepository;
import money.Money;
import statistic.StatisticManager;
import time.timeManager.TimeManager;
import time.timeOfDay.TimeOfDay;
import user.User;
import user.account.UserAccount;

public class BuySalePresenterImpl implements BuySalePresenter {
	final private BuySaleView buySaleView;
	final private BuySaleModel model;
	final private User user;
	private UserAccount userAccount;
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
	            ExchangePurchase purchase = new ExchangePurchase(0l,0L, 0, 0d, 0);
	            List<ExchangePurchase> purchaseExchantge = exchangePurchaseList.stream().filter(ep -> Objects.equals(ep.getCompanyId(), exchange.getCompanyId())).collect(Collectors.toList());
	            if(purchaseExchantge.size()==1) {
	            	purchase = purchaseExchantge.get(0);
	            }

	            ExchangeBuySaleViewModel viewModel = mapToViewModel(timeOfDay, exchange, purchase);

	            viewModels.add(viewModel);
	        }
	        return viewModels;
	    }

	    private ExchangeBuySaleViewModel mapToViewModel(TimeOfDay timeOfDay, Exchange exchange, ExchangePurchase purchase) {
	    	Money currentPriceMoney = exchange.getCurrentPrice(timeOfDay);
	    	
	    	String currentPrice = currentPriceMoney.toString("z³");

	        return new ExchangeBuySaleViewModel(
	        		exchange.getCompanyId(),
	                exchange.getCompanyName(),
	                exchange.getIsin(),
	                exchange.getAmount(),
	                currentPrice,
	                purchase.getAmount());

	    }
	@Override
	public void onBuy(ExchangeBuySaleViewModel exchangeBuySaleViewModel,int exchangeAmount) {
		if( exchangeBuySaleViewModel.getAmount()>=exchangeAmount && (exchangeBuySaleViewModel.getCurrentValueDouble()*exchangeAmount)<=userAccount.getAccountBalanceDouble()) {
			if(exchangeBuySaleViewModel.getUserAmount()>0 
					&& isSamePurchasePrice(exchangeBuySaleViewModel)) {
				
				Money moneyBalance = new Money(userAccount.getAccountBalanceDouble()).minus(new Money(exchangeBuySaleViewModel.getCurrentValueDouble()).multiply(exchangeAmount));
				
				buySaleView.onBuySuccess(model.buyUpdate(exchangeBuySaleViewModel, user, exchangeAmount, userAccount,exchangeBuySaleViewModel.getUserAmount() ));
				
				this.userAccount = new UserAccount(user.getId(),Double.valueOf(moneyBalance.toString()));
				
				setStatistic(user,moneyBalance);
			}else {
				
				Money moneyBalance = new Money(userAccount.getAccountBalanceDouble()).minus(new Money(exchangeBuySaleViewModel.getCurrentValueDouble()).multiply(exchangeAmount));
				
				buySaleView.onBuySuccess(model.buy(exchangeBuySaleViewModel, user, exchangeAmount, userAccount));
				this.userAccount = new UserAccount(user.getId(),Double.valueOf(moneyBalance.toString()));
				setStatistic(user,moneyBalance);
			}
		}else {
			buySaleView.onBuyFail(StringResourcesBuySale.accountBalanceNotEnough);
		}
	}

	@Override
	public void onSale(ExchangeBuySaleViewModel exchangeBuySaleViewModel,int exchangeAmount) {
		if(exchangeBuySaleViewModel.getUserAmount()>0 && exchangeBuySaleViewModel.getUserAmount()>= exchangeAmount) {
			
			Money moneyBalance = new Money(userAccount.getAccountBalanceDouble()).add(new Money(exchangeBuySaleViewModel.getCurrentValueDouble()).multiply(exchangeAmount));
			
			buySaleView.onSaleSuccess(model.sale(exchangeBuySaleViewModel, user, exchangeAmount, userAccount));
			this.userAccount = new UserAccount(user.getId(),Double.valueOf(moneyBalance.toString()));
			setStatistic(user,moneyBalance);
		}else {
			buySaleView.onSaleFail(StringResourcesBuySale.cantSale);
		}
	}
	
	private boolean isSamePurchasePrice(ExchangeBuySaleViewModel exchangeBuySaleViewModel) {
		List<Double> purchasePrices = ExchangePurchaseSQL.getPurchasePrice(user, exchangeBuySaleViewModel.getId());
		for(Double price : purchasePrices ) {
			if(exchangeBuySaleViewModel.getCurrentValueDouble().equals(price)) {
				return true;
			}
		}
		return false;
	}
	
	private void setStatistic(User user, Money money) {
		Connection conn = null;
		try {
			conn = DbConnection.getConnectionAndStartTransaction();
			StatisticManager.addStatistic(user, Double.valueOf(money.toString()), TimeManager.getCurrentDate(), conn);
			DbConnection.commitTransactionAndCloseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DbConnection.closeConnectionAndRollBackTransaction(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void viewExchanges() {
		// TODO Auto-generated method stub

	}
}
