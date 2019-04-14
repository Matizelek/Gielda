package intro;

import exchange.Exchange;
import exchange.ExchangePurchase;
import exchange.repository.ExchangeRepository;
import intro.presenter.IntroPresenter;
import intro.presenter.IntroPresenterImpl;
import intro.view.ExchangePurchaseViewModel;
import intro.view.IntroView;
import org.junit.Test;
import org.mockito.Mockito;
import time.timeOfDay.TimeOfDay;
import user.User;
import user.account.UserAccount;
import user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntroPresenterTest {


    @Test
    public void whenStartInvokedShouldShowUserMoney() {
        IntroView view = Mockito.mock(IntroView.class);

        User user = new User("dsad", "abc", 5);
        UserAccount account = new UserAccount(5, 14.3);


        UserRepository repository = Mockito.mock(UserRepository.class);
        Mockito.when(repository.getAccountForUser(user)).thenReturn(account);

        ExchangeRepository exchangeRepository = Mockito.mock(ExchangeRepository.class);
        Mockito.when(exchangeRepository.getExchanges(Mockito.anyList())).thenReturn(Collections.emptyList());

        IntroPresenter presenter = new IntroPresenterImpl(view, repository, exchangeRepository);

        presenter.start(user, TimeOfDay.FIRST_Q);

        Mockito.verify(view).setAccountState("14.30 zł");
    }

    @Test
    public void whenStartInvokedShouldShowPurchaseList() {
        IntroView view = Mockito.mock(IntroView.class);

        User user = new User("dsad", "abc", 5);
        UserAccount account = new UserAccount(5, 14.3);
        account.addExchangePurchase(new ExchangePurchase(5, 3, 15.0, 1));

        List<ExchangePurchaseViewModel> list = new ArrayList<>();
        ExchangePurchaseViewModel model = new ExchangePurchaseViewModel("Test", 1, "15.00 zł", "15.00 zł");
        list.add(model);

        UserRepository repository = Mockito.mock(UserRepository.class);
        Mockito.when(repository.getAccountForUser(user)).thenReturn(account);

        ExchangeRepository exchangeRepository = Mockito.mock(ExchangeRepository.class);

        ArrayList<Exchange> exchanges = new ArrayList<>();
        Exchange exchange = new Exchange(3, "Test", 15.0, 23.0, 14.1, 123.0);
        exchanges.add(exchange);

        Mockito.when(exchangeRepository.getExchanges(Mockito.anyList())).thenReturn(exchanges);

        IntroPresenter presenter = new IntroPresenterImpl(view, repository, exchangeRepository);

        presenter.start(user, TimeOfDay.FIRST_Q);


        Mockito.verify(view).showExchangePurchases(list);
    }

}
