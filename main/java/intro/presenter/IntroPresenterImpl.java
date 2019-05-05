package intro.presenter;

import exchange.Exchange;
import exchange.ExchangePurchase;
import exchange.repository.ExchangeRepository;
import intro.view.ExchangePurchaseViewModel;
import intro.view.IntroView;
import money.Money;
import time.timeOfDay.TimeOfDay;
import user.User;
import user.account.UserAccount;
import user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IntroPresenterImpl implements IntroPresenter {

    private final IntroView view;
    private final UserRepository userRepository;
    private final ExchangeRepository exchangeRepository;

    public IntroPresenterImpl(IntroView view, UserRepository userRepository, ExchangeRepository exchangeRepository) {
        this.view = view;
        this.userRepository = userRepository;
        this.exchangeRepository = exchangeRepository;
    }


    @Override
    public void start(User user, TimeOfDay timeOfDay) {

        UserAccount account = userRepository.getAccountForUser(user);

        view.setAccountState(getUserBalance(account));
        
        view.setLastTransactionBalance(getLastTransactionSaldo(account));

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

        for (int i = 0; i < exchangePurchaseList.size(); i++) {
            Exchange exchange = exchanges.get(i);
            ExchangePurchase purchase = exchangePurchaseList.get(i);

            ExchangePurchaseViewModel viewModel = mapToViewModel(timeOfDay, exchange, purchase);

            viewModels.add(viewModel);
        }
        return viewModels;
    }

    private ExchangePurchaseViewModel mapToViewModel(TimeOfDay timeOfDay, Exchange exchange, ExchangePurchase purchase) {
        String currentPrice = exchange.getCurrentPrice(timeOfDay).toString("z³‚");
        String purchasePrice = purchase.getPurchasePrice().toString("z³‚");

        return new ExchangePurchaseViewModel(
                exchange.getCompanyName(),
                purchase.getAmount(),
                currentPrice,
                purchasePrice);
    }

    private String getUserBalance(UserAccount account) {
        Money balance = account.accountBalance;
        return balance.toString("z³");
    }
    
    private String getLastTransactionSaldo(UserAccount account) {
        Money balance = account.accountBalance;
        return balance.toString("z³‚");
    }
}
