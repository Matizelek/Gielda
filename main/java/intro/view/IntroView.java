package intro.view;

import java.util.List;

public interface IntroView {

    void setAccountState(String money);

    void setLastTransactionBalance(String money);

    void showExchangePurchases(List<ExchangePurchaseViewModel> exchangePurchases);

}
