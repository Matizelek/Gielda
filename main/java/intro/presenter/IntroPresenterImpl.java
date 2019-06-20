package intro.presenter;

import exchange.Exchange;
import exchange.ExchangePurchase;
import exchange.repository.ExchangeRepository;
import intro.view.ExchangePurchaseViewModel;
import intro.view.IntroView;
import introModel.IntroModel;
import money.Money;
import time.timeOfDay.TimeOfDay;
import user.User;
import user.account.UserAccount;
import user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class IntroPresenterImpl implements IntroPresenter {

    private final IntroView view;
    private final UserRepository userRepository;
    private final ExchangeRepository exchangeRepository;
    private final IntroModel model;

    public IntroPresenterImpl(IntroView view, UserRepository userRepository, ExchangeRepository exchangeRepository) {
        this.view = view;
        this.userRepository = userRepository;
        this.exchangeRepository = exchangeRepository;
        this.model = new IntroModel();
    }


    @Override
    public void start(User user, TimeOfDay timeOfDay) {

        UserAccount account = userRepository.getAccountForUser(user);

        view.setAccountState(getUserBalance(account));

        List<ExchangePurchaseViewModel> viewModels = getExchangePurchaseViewModels(timeOfDay, account);

        view.showExchangePurchases(viewModels);

    }

    private List<ExchangePurchaseViewModel> getExchangePurchaseViewModels(TimeOfDay timeOfDay, UserAccount account) {
        List<ExchangePurchase> exchangePurchaseList = account.getExchangePurchaseList();

        List<Integer> ids = exchangePurchaseList
                .stream()
                .map(ExchangePurchase::getCompanyId)
                .collect(Collectors.toList());

        List<Exchange> exchanges = exchangeRepository.getExchanges(ids);

        List<ExchangePurchaseViewModel> viewModels = new ArrayList<>();
        
        exchangePurchaseList = exchangePurchaseList.stream().sorted(Comparator.comparing(o -> o.getCompanyId())).collect(Collectors.toList());
        exchanges = exchanges.stream().sorted(Comparator.comparing(o -> o.getCompanyId())).collect(Collectors.toList());

        for (int i = 0; i < exchangePurchaseList.size(); i++) {
        	if(exchanges.size()>i) {
        		Exchange exchange = exchanges.get(i);
                ExchangePurchase purchase = exchangePurchaseList.get(i);

                ExchangePurchaseViewModel viewModel = mapToViewModel(timeOfDay, exchange, purchase);

                viewModels.add(viewModel);
        	}
            
        }
        return viewModels;
    }
    
    private List<ExchangePurchaseViewModel> getExchangePurchaseViewModelsDetails(TimeOfDay timeOfDay, UserAccount account, int companyId) {
        List<ExchangePurchase> exchangePurchaseList = account.getExchangePurchaseListDetail(companyId);

        List<Integer> id = new ArrayList<>();
        id.add(companyId);
        List<Exchange> exchanges = exchangeRepository.getExchanges(id);

        List<ExchangePurchaseViewModel> viewModels = new ArrayList<>();
        
        exchangePurchaseList = exchangePurchaseList.stream().sorted(Comparator.comparing(o -> o.getCompanyId())).collect(Collectors.toList());

        for (int i = 0; i < exchangePurchaseList.size(); i++) {
        		Exchange exchange = exchanges.get(0);
                ExchangePurchase purchase = exchangePurchaseList.get(i);

                ExchangePurchaseViewModel viewModel = mapToViewModel(timeOfDay, exchange, purchase);

                viewModels.add(viewModel);
        	
            
        }
        return viewModels;
    }

    private ExchangePurchaseViewModel mapToViewModel(TimeOfDay timeOfDay, Exchange exchange, ExchangePurchase purchase) {
    	Money currentPriceMoney = exchange.getCurrentPrice(timeOfDay);
    	
        String currentPrice = currentPriceMoney.toString("z³‚");
        String currentPriceMultiplayAmount = currentPriceMoney.multiply(purchase.getAmount()).toString("z³");
        String purchasePrice = purchase.getPurchasePrice().toString("z³‚");
        String purchasePriceMultiplayAmount  =purchase.getPurchasePrice().multiply(purchase.getAmount()).toString("z³");

        return new ExchangePurchaseViewModel(
        		purchase.getCompanyId(),
                exchange.getCompanyName(),
                purchase.getAmount(),
                currentPrice,
                purchasePrice,
                currentPriceMultiplayAmount,
                purchasePriceMultiplayAmount);
    }

    private String getUserBalance(UserAccount account) {
        Money balance = account.accountBalance;
        return balance.toString("z³");
    }


	@Override
	public void nextSimulatedDate(User user) {
		model.nextSimulatedDate(user);
	}
	
	@Override
	public void showAllExchangePurchase(TimeOfDay timeOfDay, User user) {
		 UserAccount account = userRepository.getAccountForUser(user);
		
		List<ExchangePurchaseViewModel> viewModels = getExchangePurchaseViewModels(timeOfDay, account);

        view.showExchangePurchases(viewModels);
	}
	
	@Override
	public void showExchangePurchaseDetails(TimeOfDay timeOfDay, User user, int companyId) {
		 UserAccount account = userRepository.getAccountForUser(user);
		
		List<ExchangePurchaseViewModel> viewModels = getExchangePurchaseViewModelsDetails(timeOfDay, account, companyId);

        view.showExchangePurchasesDetails(viewModels);
	}
    
}
